package net.karashokleo.l2hostility;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import net.karashokleo.l2hostility.util.raytrace.ClientUpdatePacket;
import net.karashokleo.l2hostility.util.raytrace.EntityTarget;
import net.karashokleo.l2hostility.util.raytrace.RayTraceUtilClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;

public class L2HostilityClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (getClientWorld() == null) return;
            EnchantmentDisabler.modifyTooltip(stack, lines, getClientWorld());
        });
        ClientTickEvents.START_CLIENT_TICK.register(client ->
                client.execute(() ->
                {
                    for (EntityTarget target : EntityTarget.LIST)
                        target.tickRender();
                }));
        ClientPlayNetworking.registerGlobalReceiver(L2Hostility.HANDLER.getPacketType(ClientUpdatePacket.class), (packet, player, responseSender) ->
                RayTraceUtilClient.clientUpdateTarget(player, packet.packet().range));
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