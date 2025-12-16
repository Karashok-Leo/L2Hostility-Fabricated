package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin
{
    @Shadow
    @NotNull
    public abstract Iterable<ItemStack> getArmorItems();

    @Inject(
        method = "getArmorVisibility",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_getArmorVisibility(CallbackInfoReturnable<Float> cir)
    {
        Iterable<ItemStack> iterable = this.getArmorItems();
        int total = 0;
        int visible = 0;
        for (ItemStack stack : iterable)
        {
            ++total;
            if (stack.isEmpty() ||
                EnchantmentHelper.getLevel(LHEnchantments.SHULKER_ARMOR, stack) > 0)
            {
                continue;
            }
            ++visible;
        }
        cir.setReturnValue(total > 0 ? (float) visible / (float) total : 0.0F);
    }
}