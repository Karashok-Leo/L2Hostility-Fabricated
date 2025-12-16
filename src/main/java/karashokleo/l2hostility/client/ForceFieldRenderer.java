package karashokleo.l2hostility.client;

import karashokleo.l2hostility.content.item.ComplementItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class ForceFieldRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer
{
    public static final ForceFieldRenderer INSTANCE = new ForceFieldRenderer();

    private static final Identifier WITHER_LOCATION = new Identifier("textures/entity/wither/wither.png");

    private WitherEntityModel<WitherEntity> wither_model;
    private WitherArmorFeatureRenderer wither_armor;
    private WitherEntity wither;

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay)
    {
        if (!stack.isOf(ComplementItems.FORCE_FIELD))
        {
            return;
        }
        setUpWither();
        matrices.push();
        translateWither(mode, matrices);
        wither_armor.render(matrices, vertexConsumers, light, wither, 0, 0, MinecraftClient.getInstance().getTickDelta(), wither.age, 0, 0);
        matrices.pop();
    }

    private void setUpWither()
    {
        MinecraftClient mc = MinecraftClient.getInstance();
        EntityModelLoader modelLoader = mc.getEntityModelLoader();
        if (wither_model == null)
        {
            wither_model = new WitherEntityModel<>(modelLoader.getModelPart(EntityModelLayers.WITHER_ARMOR));
        }
        if (wither_armor == null)
        {
            wither_armor = new WitherArmorFeatureRenderer(new FeatureRendererContext<>()
            {
                @Override
                public WitherEntityModel<WitherEntity> getModel()
                {
                    return ForceFieldRenderer.this.wither_model;
                }

                @Override
                public Identifier getTexture(WitherEntity boss)
                {
                    return WITHER_LOCATION;
                }
            }, modelLoader);
        }

        World world = mc.world;
        if (world == null)
        {
            wither = null;
            return;
        }
        if (wither != null)
        {
            if (wither.getWorld() != world)
            {
                wither = null;
            }
        }
        if (wither == null)
        {
            wither = EntityType.WITHER.create(world);
        }
        if (wither == null)
        {
            return;
        }
        assert mc.player != null;
        wither.age = mc.player.age;
        wither.setHealth(1);
    }

    private void translateWither(ModelTransformationMode mode, MatrixStack matrices)
    {
        switch (mode)
        {
            case GUI:
            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:
                break;
            case THIRD_PERSON_LEFT_HAND:
            case THIRD_PERSON_RIGHT_HAND:
            {
                matrices.translate(0.25, 0.4, 0.5);
                float size = 0.625f;
                matrices.scale(size, size, size);
                break;
            }
            case GROUND:
            {
                matrices.translate(0.25, 0, 0.5);
                float size = 0.625f;
                matrices.scale(size, size, size);
                break;
            }
            case NONE:
            case HEAD:
            case FIXED:
            {
                matrices.translate(0.5, 0.5, 0.5);
                float size = 0.6f;
                matrices.scale(size, -size, size);
                matrices.translate(0, -0.45, 0);
                return;
            }
        }
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(135));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-155));
        float size = 0.6f;
        matrices.scale(size, size, size);
        matrices.translate(0, -1.6, 0);
    }
}
