package karashokleo.l2hostility.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.l2hostility.content.event.MiscEvents;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BackgroundRenderer.class)
public abstract class BackgroundRendererMixin
{
    @WrapOperation(
            method = "applyFog",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;isSpectator()Z"
            )
    )
    private static boolean inject_applyFog(Entity instance, Operation<Boolean> original)
    {
        return MiscEvents.canSee(instance, original);
    }
}
