package net.karashokleo.l2hostility.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.karashokleo.l2hostility.client.event.ClientEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class L2HostilityClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ClientEvents.register();
    }

    public static MinecraftClient getClient()
    {
        return MinecraftClient.getInstance();
    }

    @Nullable
    public static ClientPlayerEntity getClientPlayer()
    {
        return getClient().player;
    }

    @Nullable
    public static ClientWorld getClientWorld()
    {
        return getClient().world;
    }
}