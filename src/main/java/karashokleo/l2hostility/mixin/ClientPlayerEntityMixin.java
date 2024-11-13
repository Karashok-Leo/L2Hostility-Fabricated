package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.content.feature.EntityFeature;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin
{
    @Inject(
            method = "updateHealth",
            at = @At("TAIL")
    )
    private void inject_updateHealth_stableBody(float health, CallbackInfo ci)
    {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (EntityFeature.STABLE_BODY.test(player))
            player.hurtTime = 0;
    }

    @Inject(
            method = "handleStatus",
            at = @At("TAIL")
    )
    private void inject_handleStatus_stableBody(byte status, CallbackInfo ci)
    {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if (EntityFeature.STABLE_BODY.test(player))
            player.hurtTime = 0;
    }
}
