package net.karashokleo.l2hostility.mixin;

import dev.xkmc.l2serial.util.Wrappers;
import net.karashokleo.l2hostility.event.ClientGlowingHandler;
import net.karashokleo.l2hostility.util.raytrace.EntityTarget;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Inject(at = @At("HEAD"), method = "isGlowing", cancellable = true)
    private void injectedIsGlowing(CallbackInfoReturnable<Boolean> cir)
    {
        if (ClientGlowingHandler.isGlowing(Wrappers.cast(this)))
            cir.setReturnValue(true);
        for (EntityTarget target : EntityTarget.LIST)
            if (target.target == (Object) this)
                cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "getTeamColorValue", cancellable = true)
    private void injectedGetTeamColorValue(CallbackInfoReturnable<Integer> cir)
    {
        Integer col = ClientGlowingHandler.getColor(Wrappers.cast(this));
        if (col != null) cir.setReturnValue(col);
    }
}
