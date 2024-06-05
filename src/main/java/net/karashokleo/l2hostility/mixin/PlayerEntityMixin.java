package net.karashokleo.l2hostility.mixin;

import net.karashokleo.l2hostility.init.LHMiscs;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin
{
    @Inject(
            method = "createPlayerAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;",
            at = @At("RETURN"),
            require = 1,
            allow = 1
    )
    private static void inject_createPlayerAttributes(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info)
    {
        info.getReturnValue().add(LHMiscs.ADD_LEVEL);
    }
}
