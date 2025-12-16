package karashokleo.l2hostility.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import karashokleo.l2hostility.content.event.MaterialEvents;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHMiscs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Inject(
        method = "applyDamage",
        at = @At("HEAD")
    )
    private void inject_head_applyDamage(DamageSource source, float amount, CallbackInfo ci, @Share("preDamage") LocalFloatRef preDamageRef)
    {
        preDamageRef.set(amount);
    }

    @Inject(
        method = "applyDamage",
        at = @At("RETURN")
    )
    private void inject_return_applyDamage(DamageSource source, float amount, CallbackInfo ci, @Share("preDamage") LocalFloatRef preDamageRef)
    {
        MaterialEvents.dropExplosionShard((PlayerEntity) (Object) this, source, preDamageRef.get(), amount);
    }

    @ModifyArg(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;getNonSpectatingEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;)Ljava/util/List;"
        ),
        index = 1
    )
    private Box inject_attack_sweepBox(Box box)
    {
        int lv = EnchantmentHelper.getEquipmentLevel(LHEnchantments.WIND_SWEEP, (PlayerEntity) (Object) this);
        if (lv > 0)
        {
            double amount = LHConfig.common().complements.properties.windSweepIncrement;
            box = box.expand(amount * lv, amount * lv, amount * lv);
        }
        return box;
    }
}
