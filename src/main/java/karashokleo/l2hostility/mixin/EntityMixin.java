package karashokleo.l2hostility.mixin;

import dev.xkmc.l2serial.util.Wrappers;
import karashokleo.l2hostility.client.ClientGlowingHandler;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.util.raytrace.EntityTarget;
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
        method = "isGlowing",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_isGlowing(CallbackInfoReturnable<Boolean> cir)
    {
        if (ClientGlowingHandler.isGlowing(Wrappers.cast(this)))
        {
            cir.setReturnValue(true);
        }
        for (EntityTarget target : EntityTarget.LIST)
        {
            if (target.target == (Object) this)
            {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(
        method = "getTeamColorValue",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_getTeamColorValue(CallbackInfoReturnable<Integer> cir)
    {
        Integer col = ClientGlowingHandler.getColor(Wrappers.cast(this));
        if (col != null)
        {
            cir.setReturnValue(col);
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
