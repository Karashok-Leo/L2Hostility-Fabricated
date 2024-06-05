package net.karashokleo.l2hostility.mixin;

import net.karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin
{
    @Inject(
            method = "renderItem", cancellable = true,
            at = @At("HEAD")
    )
    private void inject_renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode transformationMode, Arm arm, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci)
    {
        if (EnchantmentHelper.getLevel(LHEnchantments.SHULKER_ARMOR, stack) > 0)
            ci.cancel();
    }
}