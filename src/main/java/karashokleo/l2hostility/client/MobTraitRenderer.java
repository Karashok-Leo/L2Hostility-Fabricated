package karashokleo.l2hostility.client;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

public class MobTraitRenderer
{
    public static void renderNamePlate(Entity entity, int light, MatrixStack matrices, TextRenderer textRenderer, VertexConsumerProvider vertexConsumers)
    {
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        assert player != null;
        boolean needHover = entity.isInvisible() || LHConfig.client().showOnlyWhenHovered;
        if (needHover && RayTraceUtil.rayTraceEntity(player, ReachEntityAttributes.getAttackRange(player, 3.0), e -> e == entity) == null)
            return;
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
}
