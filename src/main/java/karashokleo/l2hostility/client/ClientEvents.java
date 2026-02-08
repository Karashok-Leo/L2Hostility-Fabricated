package karashokleo.l2hostility.client;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import karashokleo.l2hostility.util.raytrace.EntityTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class ClientEvents
{
    private static final int CHUNK_RENDER_INTERNAL = 4;
    private static boolean renderChunk = false;

    public static void register()
    {
        ItemTooltipCallback.EVENT.register((stack, context, lines) ->
        {
            if (L2HostilityClient.getClientWorld() == null)
            {
                return;
            }
            EnchantmentDisabler.modifyTooltip(stack, lines, L2HostilityClient.getClientWorld());
        });

        ClientTickEvents.START_CLIENT_TICK.register(client ->
            client.execute(() ->
            {
                for (EntityTarget target : EntityTarget.LIST)
                {
                    target.tickRender();
                }
            }));

        ClientTickEvents.END_CLIENT_TICK.register(client ->
        {
            ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
            if (player != null &&
                player.age % CHUNK_RENDER_INTERNAL == 0)
            {
                renderChunk = TrinketCompat.hasItemEquippedOrInTrinket(player, MiscItems.DETECTOR_GLASSES) &&
                    TrinketCompat.hasItemEquippedOrInTrinket(player, MiscItems.DETECTOR);
            }
        });

        WorldRenderEvents.BEFORE_DEBUG_RENDER.register(context ->
        {
//            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS) return;
            ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
            if (player == null)
            {
                return;
            }
            var opt = ChunkDifficulty.at(player.getWorld(), player.getBlockPos());
            if (opt.isEmpty())
            {
                return;
            }
            if (!renderChunk)
            {
                return;
            }
            ChunkClearRenderer.render(context.matrixStack(), player, opt.get());
        });
    }
}
