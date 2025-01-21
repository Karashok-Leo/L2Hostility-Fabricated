package karashokleo.l2hostility.content.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import karashokleo.l2hostility.content.component.chunk.SectionDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.function.Predicate;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class RegionCommands
{
    public static LiteralArgumentBuilder<ServerCommandSource> build()
    {
        return literal("region")
                .then(section())
                .then(area());
    }

    private static LiteralArgumentBuilder<ServerCommandSource> section()
    {
        return literal("section")
                .then(argument("pos", BlockPosArgumentType.blockPos())
                        .then(literal("set_base")
                                .requires(e -> e.hasPermissionLevel(2))
                                .then(argument("level", IntegerArgumentType.integer(0))
                                        .executes(sectionWorld((section, level, pos, lv) ->
                                                section.getLevelEditor(level, pos).setBase(lv)))))
                        .then(literal("add_base")
                                .requires(e -> e.hasPermissionLevel(2))
                                .then(argument("level", IntegerArgumentType.integer())
                                        .executes(sectionWorld((section, level, pos, lv) ->
                                                section.getLevelEditor(level, pos).addBase(lv)))))
                        .then(literal("set_clear")
                                .requires(e -> e.hasPermissionLevel(2))
                                .executes(sectionRun((section, level, pos) ->
                                        section.setClear(ChunkDifficulty.at(level, pos).orElseThrow(), pos))))
                        .then(literal("set_unclear")
                                .requires(e -> e.hasPermissionLevel(2))
                                .executes(sectionRun((section, level, pos) ->
                                        section.setUnclear(ChunkDifficulty.at(level, pos).orElseThrow(), pos))))
                        .then(literal("get_base").executes(sectionGet((section, level, pos) ->
                                LHTexts.COMMAND_REGION_GET_BASE.get(section.getLevelEditor(level, pos).getBase()))))
                        .then(literal("get_total").executes(sectionGet((section, level, pos) ->
                                LHTexts.COMMAND_REGION_GET_TOTAL.get(section.getLevelEditor(level, pos).getTotal()))))
                        .then(literal("get_scale").executes(sectionGet((section, level, pos) ->
                                LHTexts.COMMAND_REGION_GET_SCALE.get(section.getScale(level, pos)))))
                        .then(literal("is_clear").executes(sectionGet((section, level, pos) ->
                                section.isCleared() ? LHTexts.COMMAND_REGION_CLEAR.get() :
                                        LHTexts.COMMAND_REGION_NOT_CLEAR.get()))
                        )
                );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> area()
    {
        return literal("area")
                .requires(e -> e.hasPermissionLevel(2))
                .then(argument("from", BlockPosArgumentType.blockPos())
                        .then(argument("to", BlockPosArgumentType.blockPos())
                                .then(literal("set_base")
                                        .then(argument("level", IntegerArgumentType.integer(0))
                                                .executes(areaWorld((section, level, pos, lv) ->
                                                        section.getLevelEditor(level, pos).setBase(lv)))))
                                .then(literal("add_base")
                                        .then(argument("level", IntegerArgumentType.integer())
                                                .executes(areaWorld((section, level, pos, lv) ->
                                                        section.getLevelEditor(level, pos).addBase(lv)))))
                                .then(literal("set_clear")
                                        .executes(areaRun((section, level, pos) ->
                                                section.setClear(ChunkDifficulty.at(level, pos).orElseThrow(), pos))))
                                .then(literal("set_unclear")
                                        .executes(areaRun((section, level, pos) ->
                                                section.setUnclear(ChunkDifficulty.at(level, pos).orElseThrow(), pos))))
                        )
                );
    }

    private static Command<ServerCommandSource> sectionWorld(SectionWorldCommand cmd)
    {
        return ctx ->
        {
            int level = ctx.getArgument("level", Integer.class);
            BlockPos sel = BlockPosArgumentType.getLoadedBlockPos(ctx, "pos");
            var e = SectionDifficulty.sectionAt(ctx.getSource().getWorld(), sel);
            if (e.isEmpty()) throw BlockPosArgumentType.UNLOADED_EXCEPTION.create();
            if (!LHConfig.common().scaling.allowSectionDifficulty)
            {
                ctx.getSource().sendMessage(LHTexts.COMMAND_REGION_LOCAL_OFF.get().formatted(Formatting.RED));
                return 1;
            }
            cmd.run(e.get(), ctx.getSource().getWorld(), sel, level);
            ctx.getSource().sendMessage(LHTexts.COMMAND_REGION_SUCCEED.get());
            return 0;
        };
    }

    private static Command<ServerCommandSource> sectionRun(SectionCommand cmd)
    {
        return ctx ->
        {
            BlockPos sel = BlockPosArgumentType.getLoadedBlockPos(ctx, "pos");
            var e = SectionDifficulty.sectionAt(ctx.getSource().getWorld(), sel);
            if (e.isEmpty()) throw BlockPosArgumentType.UNLOADED_EXCEPTION.create();
            cmd.run(e.get(), ctx.getSource().getWorld(), sel);
            ctx.getSource().sendMessage(LHTexts.COMMAND_REGION_SUCCEED.get());
            return 0;
        };
    }

    private static Command<ServerCommandSource> sectionGet(SectionGet cmd)
    {
        return ctx ->
        {
            BlockPos sel = BlockPosArgumentType.getLoadedBlockPos(ctx, "pos");
            var e = SectionDifficulty.sectionAt(ctx.getSource().getWorld(), sel);
            if (e.isEmpty()) throw BlockPosArgumentType.UNLOADED_EXCEPTION.create();
            ctx.getSource().sendMessage(cmd.run(e.get(), ctx.getSource().getWorld(), sel));
            return 0;
        };
    }

    private static Command<ServerCommandSource> areaWorld(SectionWorldCommand cmd)
    {
        return ctx ->
        {
            int level = ctx.getArgument("level", Integer.class);
            BlockPos from = BlockPosArgumentType.getLoadedBlockPos(ctx, "from");
            BlockPos to = BlockPosArgumentType.getLoadedBlockPos(ctx, "to");
            if (!LHConfig.common().scaling.allowSectionDifficulty)
            {
                ctx.getSource().sendMessage(LHTexts.COMMAND_REGION_LOCAL_OFF.get().formatted(Formatting.RED));
                return 1;
            }
            int count = iterate(from, to, sel ->
            {
                var e = SectionDifficulty.sectionAt(ctx.getSource().getWorld(), sel);
                return e.filter(sectionDifficulty -> cmd.run(sectionDifficulty, ctx.getSource().getWorld(), sel, level)).isPresent();
            });
            ctx.getSource().sendMessage(LHTexts.COMMAND_REGION_COUNT.get(count));
            return 0;
        };
    }

    private static Command<ServerCommandSource> areaRun(SectionCommand cmd)
    {
        return ctx ->
        {
            BlockPos from = BlockPosArgumentType.getLoadedBlockPos(ctx, "from");
            BlockPos to = BlockPosArgumentType.getLoadedBlockPos(ctx, "to");
            int count = iterate(from, to, sel ->
            {
                var e = SectionDifficulty.sectionAt(ctx.getSource().getWorld(), sel);
                return e.filter(sectionDifficulty -> cmd.run(sectionDifficulty, ctx.getSource().getWorld(), sel)).isPresent();
            });
            ctx.getSource().sendMessage(LHTexts.COMMAND_REGION_COUNT.get(count));
            return 0;
        };
    }

    private static int iterate(BlockPos from, BlockPos to, Predicate<BlockPos> pred)
    {
        Box aabb = new Box(from, to);
        int x0 = ((int) aabb.minX) & -15;
        int y0 = ((int) aabb.minY) & -15;
        int z0 = ((int) aabb.minZ) & -15;
        int x1 = ((int) aabb.maxX) & -15;
        int y1 = ((int) aabb.maxY) & -15;
        int z1 = ((int) aabb.maxZ) & -15;
        int count = 0;
        for (int x = x0; x <= x1; x += 16)
        {
            for (int y = y0; y <= y1; y += 16)
            {
                for (int z = z0; z <= z1; z += 16)
                {
                    if (pred.test(new BlockPos(x, y, z)))
                    {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private interface SectionCommand
    {
        boolean run(SectionDifficulty section, World level, BlockPos pos);
    }

    private interface SectionGet
    {
        Text run(SectionDifficulty section, World level, BlockPos pos);
    }

    private interface SectionWorldCommand
    {
        boolean run(SectionDifficulty section, World level, BlockPos pos, int lv);
    }
}
