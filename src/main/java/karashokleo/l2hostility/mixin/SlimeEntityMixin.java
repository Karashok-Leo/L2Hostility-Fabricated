package karashokleo.l2hostility.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2serial.util.Wrappers;
import karashokleo.l2hostility.content.enchantment.weapon.SplitSuppressEnchantment;
import karashokleo.l2hostility.content.trait.highlevel.SplitTrait;
import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin
{
    @WrapOperation(
            method = "remove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/SlimeEntity;isDead()Z"
            )
    )
    public boolean l2hostility$remove$suppress(SlimeEntity instance, Operation<Boolean> original)
    {
        if (instance.getCommandTags().contains(SplitSuppressEnchantment.FLAG))
        {
            return false;
        }
        return original.call(instance);
    }

    @WrapOperation(
            method = "remove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/SlimeEntity;setAiDisabled(Z)V"
            )
    )
    public void l2hostility$remove$inheritCap(SlimeEntity sub, boolean noAI, Operation<Void> op)
    {
        SlimeEntity self = Wrappers.cast(this);
        SplitTrait.copyComponents(self, sub);
        op.call(sub, noAI);
    }
}
