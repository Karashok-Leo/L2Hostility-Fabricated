package net.karashokleo.l2hostility.mixin;

import net.karashokleo.l2hostility.init.LHEffects;
import net.karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @Shadow
    @NotNull
    public abstract Iterable<ItemStack> getArmorItems();

    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Inject(at = @At("HEAD"), method = "getArmorVisibility", cancellable = true)
    private void injectedGetArmorVisibility(CallbackInfoReturnable<Float> cir)
    {
        Iterable<ItemStack> iterable = this.getArmorItems();
        int total = 0;
        int visible = 0;
        for (ItemStack stack : iterable)
        {
            ++total;
            if (stack.isEmpty() ||
                    EnchantmentHelper.getLevel(LHEnchantments.SHULKER_ARMOR, stack) > 0)
                continue;
            ++visible;
        }
        cir.setReturnValue(total > 0 ? (float) visible / (float) total : 0.0F);
    }

    @Inject(at = @At("HEAD"), method = "heal", cancellable = true)
    private void injectedHeal(float amount, CallbackInfo ci)
    {
        if (this.hasStatusEffect(LHEffects.CURSE))
            ci.cancel();
    }
}