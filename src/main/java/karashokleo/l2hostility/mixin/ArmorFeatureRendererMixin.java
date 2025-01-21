package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// set priority to 0 to inject before other mods in sure that the rendering is canceled
@Mixin(value = ArmorFeatureRenderer.class, priority = 0)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, A extends BipedEntityModel<T>>
{
    @Inject(
            method = "renderArmor",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci)
    {
        if (EnchantmentHelper.getLevel(LHEnchantments.SHULKER_ARMOR, entity.getEquippedStack(armorSlot)) > 0)
            ci.cancel();
    }
}
