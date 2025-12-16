package karashokleo.l2hostility.init;

import karashokleo.l2hostility.content.command.MobCommands;
import karashokleo.l2hostility.content.command.PlayerCommands;
import karashokleo.l2hostility.content.command.RegionCommands;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;

public class LHCommands
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(
                CommandManager.literal("hostility")
                    .then(MobCommands.build())
                    .then(RegionCommands.build())
                    .then(PlayerCommands.build())
            )
        );
    }
}
