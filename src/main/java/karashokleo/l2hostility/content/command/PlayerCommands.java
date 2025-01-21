package karashokleo.l2hostility.content.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.logic.TraitManager;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.Predicate;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class PlayerCommands
{
    public static LiteralArgumentBuilder<ServerCommandSource> build()
    {
        return literal("player")
                .then(argument("player", EntityArgumentType.players())
                        .then(difficulty())
                        .then(trait())
                        .then(dim())
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> difficulty()
    {
        return literal("difficulty")
                .then(literal("base")
                        .then(literal("set")
                                .requires(e -> e.hasPermissionLevel(2))
                                .then(argument("level", IntegerArgumentType.integer(0))
                                        .executes(playerLevel((player, level) ->
                                                player.getLevelEditor().setBase(level)))))
                        .then(literal("add")
                                .requires(e -> e.hasPermissionLevel(2))
                                .then(argument("level", IntegerArgumentType.integer())
                                        .executes(playerLevel((player, level) ->
                                                player.getLevelEditor().addBase(level)))))
                        .then(literal("get").executes(playerGet(player ->
                                LHTexts.COMMAND_PLAYER_GET_BASE.get(player.owner.getDisplayName(), player.getLevelEditor().getBase())))))
                .then(literal("total")
                        .then(literal("set")
                                .requires(e -> e.hasPermissionLevel(2))
                                .then(argument("level", IntegerArgumentType.integer(0))
                                        .executes(playerLevel((player, level) ->
                                                player.getLevelEditor().setTotal(level)))))
                        .then(literal("add")
                                .requires(e -> e.hasPermissionLevel(2))
                                .then(argument("level", IntegerArgumentType.integer())
                                        .executes(playerLevel((player, level) ->
                                                player.getLevelEditor().addTotal(level)))))
                        .then(literal("get").executes(playerGet(player ->
                                LHTexts.COMMAND_PLAYER_GET_TOTAL.get(player.owner.getDisplayName(), player.getLevelEditor().getTotal())))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> trait()
    {
        return literal("traitCap")
                .then(literal("set")
                        .requires(e -> e.hasPermissionLevel(2))
                        .then(argument("level", IntegerArgumentType.integer(0, TraitManager.getMaxLevel()))
                                .executes(playerLevel((player, level) ->
                                {
                                    player.maxRankKilled = level;
                                    return true;
                                }))))
                .then(literal("get").executes(playerGet(player ->
                        LHTexts.COMMAND_PLAYER_GET_TRAIT_CAP.get(player.owner.getDisplayName(), player.maxRankKilled))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> dim()
    {
        return literal("dimensions")
                .then(literal("clear")
                        .requires(e -> e.hasPermissionLevel(2))
                        .executes(playerRun(player ->
                        {
                            boolean ans = !player.dimensions.isEmpty();
                            player.dimensions.clear();
                            return ans;
                        })))
                .then(literal("get").executes(playerGet(player ->
                        LHTexts.COMMAND_PLAYER_GET_DIM.get(player.owner.getDisplayName(), player.dimensions.size()))));
    }

    private static Command<ServerCommandSource> playerRun(PlayerCommand cmd)
    {
        return ctx ->
        {
            EntitySelector sel = ctx.getArgument("player", EntitySelector.class);
            var list = sel.getPlayers(ctx.getSource());
            int count = iterate(list, cmd::run);
            printCompletion(ctx.getSource(), count);
            return 0;
        };
    }

    private static Command<ServerCommandSource> playerGet(PlayerGet cmd)
    {
        return ctx ->
        {
            EntitySelector sel = ctx.getArgument("player", EntitySelector.class);
            var list = sel.getPlayers(ctx.getSource());
            for (var e : list)
            {
                ctx.getSource().sendMessage(cmd.run(PlayerDifficulty.get(e)));
            }
            return 0;
        };
    }

    private static Command<ServerCommandSource> playerLevel(PlayerLevelCommand cmd)
    {
        return ctx ->
        {
            int level = ctx.getArgument("level", Integer.class);
            EntitySelector sel = ctx.getArgument("player", EntitySelector.class);
            var list = sel.getPlayers(ctx.getSource());
            int count = iterate(list, cap -> cmd.run(cap, level));
            printCompletion(ctx.getSource(), count);
            return 0;
        };
    }

    private static int iterate(List<ServerPlayerEntity> list, Predicate<PlayerDifficulty> task)
    {
        int count = 0;
        for (var e : list)
        {
            PlayerDifficulty cap = PlayerDifficulty.get(e);
            if (task.test(cap))
            {
                PlayerDifficulty.sync(e);
                count++;
            }
        }
        return count;
    }

    private static void printCompletion(ServerCommandSource ctx, int count)
    {
        if (count > 0)
        {
            ctx.sendMessage(LHTexts.COMMAND_PLAYER_SUCCEED.get(count));
        } else
        {
            ctx.sendMessage(LHTexts.COMMAND_PLAYER_FAIL.get().formatted(Formatting.RED));
        }
    }

    private interface PlayerCommand
    {
        boolean run(PlayerDifficulty player);
    }

    private interface PlayerGet
    {
        Text run(PlayerDifficulty player);
    }

    private interface PlayerLevelCommand
    {
        boolean run(PlayerDifficulty player, int level);
    }
}
