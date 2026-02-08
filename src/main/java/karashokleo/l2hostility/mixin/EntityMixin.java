package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Inject(
        method = "isBeingRainedOn",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_isBeingRainedOn(CallbackInfoReturnable<Boolean> cir)
    {
        if (((Entity) (Object) this) instanceof LivingEntity le)
        {
            if (TrinketCompat.hasItemInTrinket(le, TrinketItems.RING_OCEAN))
            {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(
        method = "occludeVibrationSignals",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_dampened_occludeVibrationSignals(CallbackInfoReturnable<Boolean> cir)
    {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof LivingEntity self)
        {
            int count = 0;
            for (EquipmentSlot slot : EquipmentSlot.values())
            {
                ItemStack stack = self.getEquippedStack(slot);
                count += EnchantmentHelper.getLevel(LHEnchantments.DAMPENED, stack);
            }
            if (count > 0)
            {
                cir.setReturnValue(true);
            }
        }
    }
}
