package net.karashokleo.l2hostility.content.mixin;

import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.init.registry.LHItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Inject(at = @At("HEAD"), method = "isBeingRainedOn", cancellable = true)
    private void injectedIsBeingRainedOn(CallbackInfoReturnable<Boolean> cir)
    {
        if (((Entity) (Object) this) instanceof LivingEntity le)
            if (TrinketCompat.hasItemInTrinket(le, LHItems.RING_OCEAN))
                cir.setReturnValue(true);
    }
}
