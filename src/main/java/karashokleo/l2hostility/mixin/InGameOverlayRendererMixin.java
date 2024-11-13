package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.content.feature.EntityFeature;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin
{
    @Inject(
            method = "renderFireOverlay",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void inject_renderFireOverlay(MinecraftClient client, MatrixStack matrices, CallbackInfo ci)
    {
        if (EntityFeature.FIRE_REJECT.test(client.player))
            ci.cancel();
    }
}
