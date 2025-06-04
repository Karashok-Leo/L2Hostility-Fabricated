package karashokleo.l2hostility.content.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;

public class DebugCommands
{
    public static LiteralArgumentBuilder<ServerCommandSource> build()
    {
        return literal("debug")
                .executes(DebugCommands::executeDebug);
    }

    private static int executeDebug(CommandContext<ServerCommandSource> context)
    {
        return Command.SINGLE_SUCCESS;
    }
}
