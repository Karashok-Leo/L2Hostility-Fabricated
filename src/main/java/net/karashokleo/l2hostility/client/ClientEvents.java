package net.karashokleo.l2hostility.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.karashokleo.l2hostility.util.raytrace.EntityTarget;
import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import net.karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import net.karashokleo.l2hostility.init.LHItems;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientEvents
{
    public static void register()
    {
        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (L2HostilityClient.getClientWorld() == null) return;
            EnchantmentDisabler.modifyTooltip(stack, lines, L2HostilityClient.getClientWorld());
        });

        ClientTickEvents.START_CLIENT_TICK.register(client ->
                client.execute(() ->
                {
                    for (EntityTarget target : EntityTarget.LIST)
                        target.tickRender();
                }));

        WorldRenderEvents.BEFORE_DEBUG_RENDER.register(context ->
        {
//            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) return;
            ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
            if (player == null) return;
            var opt = ChunkDifficulty.at(player.getWorld(), player.getBlockPos());
            if (opt.isEmpty()) return;
            if (!TrinketCompat.hasItemEquippedOrInTrinket(player, LHItems.DETECTOR_GLASSES)) return;
            if (!TrinketCompat.hasItemEquippedOrInTrinket(player, LHItems.DETECTOR)) return;
            ChunkClearRenderer.render(context.matrixStack(), player, opt.get());
        });
    }
}
