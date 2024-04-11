package net.karashokleo.l2hostility.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.karashokleo.l2hostility.client.L2HostilityClient;
import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.registry.LHItems;
import net.karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

public class ClientEvents
{
    public static void renderNamePlate(Entity entity, int light, MatrixStack matrices, TextRenderer textRenderer, VertexConsumerProvider vertexConsumers)
    {
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        assert player != null;
        boolean needHover = entity.isInvisible() || LHConfig.client().showOnlyWhenHovered;
        if (needHover && RayTraceUtil.rayTraceEntity(player, player.getEntityReach(), e -> e == entity) == null) return;
        var list = diff.get().getTitle(
                LHConfig.client().showLevelOverHead,
                LHConfig.client().showTraitOverHead
        );
        int offset = list.size();
        float off = (float) LHConfig.client().overHeadRenderOffset;
        TextRenderer.TextLayerType mode = player.canSee(entity) ?
                TextRenderer.TextLayerType.SEE_THROUGH :
                TextRenderer.TextLayerType.NORMAL;
        for (var e : list)
        {
            renderNameTag(entity, light, (offset + off) * 0.2f, matrices, textRenderer, e, vertexConsumers, mode);
            offset--;
        }
    }

    protected static void renderNameTag(Entity entity, int light, float offset, MatrixStack matrices, TextRenderer textRenderer, Text text, VertexConsumerProvider vertexConsumers, TextRenderer.TextLayerType mode)
    {
        var dispatcher = L2HostilityClient.getClient().getEntityRenderDispatcher();
        double d0 = dispatcher.getSquaredDistanceToCamera(entity);
        int max = LHConfig.client().overHeadRenderDistance;
        int light_ = LHConfig.client().overHeadRenderFullBright ? LightmapTextureManager.MAX_LIGHT_COORDINATE : light;
        if (d0 < max * max)
        {
            float f = entity.getNameLabelHeight() + offset;
            matrices.push();
            matrices.translate(0.0F, f, 0.0F);
            matrices.multiply(dispatcher.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            float f2 = (float) (-textRenderer.getWidth(text) / 2);
            float f1 = L2HostilityClient.getClient().options.getTextBackgroundOpacity(0.25F);
            int j = (int) (f1 * 255.0F) << 24;
            textRenderer.draw(text, f2, 0, -1, false, matrix4f, vertexConsumers, mode, j, light_);
            matrices.pop();
        }
    }

    public static void onLevelRenderLast()
    {
        WorldRenderEvents.LAST.register(context ->
        {
//            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS)
//                return;
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
