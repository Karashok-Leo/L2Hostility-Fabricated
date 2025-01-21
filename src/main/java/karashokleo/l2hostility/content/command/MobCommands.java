package karashokleo.l2hostility.content.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.RegistryKeyArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MobCommands
{
    public static LiteralArgumentBuilder<ServerCommandSource> build()
    {
        return literal("mobs")
                .then(argument("targets", EntityArgumentType.entities())
                        .then(level())
                        .then(trait())
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> trait()
    {
        return literal("trait")
                .then(literal("clear")
                        .requires(e -> e.hasPermissionLevel(2))
                        .executes(mobRun(MobCommands::commandClearTrait)))
                .then(literal("remove")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("trait", RegistryKeyArgumentType.registryKey(LHTraits.TRAIT_KEY))
                                .executes(mobTrait(MobCommands::commandRemoveTrait))))
                .then(literal("set")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("trait", RegistryKeyArgumentType.registryKey(LHTraits.TRAIT_KEY))
                                .then(argument("rank", IntegerArgumentType.integer(0))
                                        .executes(mobTraitRank(MobCommands::commandSetTrait)))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> level()
    {
        return literal("level")
                .then(literal("rerollTrait")
                        .requires(e -> e.hasPermissionLevel(2))
                        .executes(mobRun(cap ->
                                cap.reInit(cap.getLevel(), false))))
                .then(literal("rerollTraitNoSuppression")
                        .requires(e -> e.hasPermissionLevel(2))
                        .executes(mobRun(cap ->
                                cap.reInit(cap.getLevel(), true))))
                .then(literal("setAndRerollTrait")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("level", IntegerArgumentType.integer(0))
                                .executes(mobLevel((cap, level) ->
                                        cap.reInit(level, false)))))
                .then(literal("addAndRerollTrait")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("level", IntegerArgumentType.integer(0))
                                .executes(mobLevel((cap, level) ->
                                        cap.reInit(cap.getLevel() + level, false)))))
                .then(literal("set")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("level", IntegerArgumentType.integer(0))
                                .executes(mobLevel(MobCommands::commandSetLevel))))
                .then(literal("add")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("level", IntegerArgumentType.integer(0))
                                .executes(mobLevel((cap, level) ->
                                        commandSetLevel(cap, cap.getLevel() + level)))));
    }

    private static boolean commandSetLevel(MobDifficulty cap, int level)
    {
        cap.setLevel(level);
        cap.sync();
        return true;
    }

    private static boolean commandClearTrait(MobDifficulty cap)
    {
        if (cap.traits.isEmpty()) return false;
        for (var e : cap.traits.keySet())
            cap.setTrait(e, 0);
        return true;
    }

    private static boolean commandRemoveTrait(MobDifficulty cap, MobTrait trait)
    {
        if (!cap.hasTrait(trait)) return false;
        cap.removeTrait(trait);
        return true;
    }

    private static boolean commandSetTrait(MobDifficulty cap, MobTrait trait, int rank)
    {
        if (!trait.allow(cap.owner)) return false;
        if (trait.getConfig().max_rank < rank) return false;
        cap.setTrait(trait, rank);
        return true;
    }


    private static Command<ServerCommandSource> mobRun(MobCommand cmd)
    {
        return ctx ->
        {
            var list = EntityArgumentType.getEntities(ctx, "targets");
            int count = iterate(list, cmd::run);
            printCompletion(ctx.getSource(), count);
            return 0;
        };
    }

    private static Command<ServerCommandSource> mobLevel(MobLevelCommand cmd)
    {
        return ctx ->
        {
            int level = ctx.getArgument("level", Integer.class);
            var list = EntityArgumentType.getEntities(ctx, "targets");
            int count = iterate(list, cap -> cmd.run(cap, level));
            printCompletion(ctx.getSource(), count);
            return 0;
        };
    }

    private static Command<ServerCommandSource> mobTrait(MobTraitCommand cmd)
    {
        return ctx ->
        {
            var trait = resolveKey(ctx, "trait", LHTraits.TRAIT_KEY, ERR_INVALID_NAME);
            var list = EntityArgumentType.getEntities(ctx, "targets");
            int count = iterate(list, cap -> cmd.run(cap, trait.value()));
            printCompletion(ctx.getSource(), count);
            return 0;
        };
    }

    private static Command<ServerCommandSource> mobTraitRank(MobTraitRankCommand cmd)
    {
        return ctx ->
        {
            int rank = ctx.getArgument("rank", Integer.class);
            var trait = resolveKey(ctx, "trait", LHTraits.TRAIT_KEY, ERR_INVALID_NAME);
            var list = EntityArgumentType.getEntities(ctx, "targets");
            int count = iterate(list, cap -> cmd.run(cap, trait.value(), rank));
            printCompletion(ctx.getSource(), count);
            return 0;
        };
    }

    private static int iterate(Collection<? extends Entity> list, Predicate<MobDifficulty> task)
    {
        int count = 0;
        for (var e : list)
        {
            var cap = MobDifficulty.get(e);
            if (cap.isEmpty()) continue;
            if (task.test(cap.get())) count++;
        }
        return count;
    }

    private static void printCompletion(ServerCommandSource ctx, int count)
    {
        if (count > 0)
        {
            ctx.sendMessage(LHTexts.COMMAND_MOB_SUCCEED.get(count));
        } else
        {
            ctx.sendMessage(LHTexts.COMMAND_PLAYER_FAIL.get().formatted(Formatting.RED));
        }
    }

    private interface MobCommand
    {
        boolean run(MobDifficulty difficulty);
    }

    private interface MobLevelCommand
    {
        boolean run(MobDifficulty difficulty, int level);
    }

    private interface MobTraitCommand
    {
        boolean run(MobDifficulty difficulty, MobTrait trait);
    }

    private interface MobTraitRankCommand
    {
        boolean run(MobDifficulty difficulty, MobTrait trait, int rank);
    }

    private static final DynamicCommandExceptionType ERR_INVALID_NAME = new DynamicCommandExceptionType(LHTexts.COMMAND_INVALID_TRAIT::get);

    private static <T> RegistryKey<T> getRegistryKey(
            CommandContext<ServerCommandSource> ctx, String name,
            RegistryKey<Registry<T>> reg, DynamicCommandExceptionType err
    ) throws CommandSyntaxException
    {
        RegistryKey<?> ans = ctx.getArgument(name, RegistryKey.class);
        Optional<RegistryKey<T>> optional = ans.tryCast(reg);
        return optional.orElseThrow(() -> err.create(ans));
    }

    private static <T> Registry<T> getRegistry(CommandContext<ServerCommandSource> ctx, RegistryKey<? extends Registry<T>> reg)
    {
        return ctx.getSource().getServer().getRegistryManager().get(reg);
    }

    private static <T> RegistryEntry.Reference<T> resolveKey(
            CommandContext<ServerCommandSource> ctx, String name,
            RegistryKey<Registry<T>> reg, DynamicCommandExceptionType err
    ) throws CommandSyntaxException
    {
        RegistryKey<T> ans = getRegistryKey(ctx, name, reg, err);
        return getRegistry(ctx, reg).getEntry(ans).orElseThrow(() -> err.create(ans.getValue()));
    }
}
