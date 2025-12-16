package karashokleo.l2hostility.content.trait.common;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public class GravityTrait extends AuraEffectTrait
{
    public GravityTrait()
    {
        super(() -> LHEffects.GRAVITY);
    }

    @Override
    public void onDamaged(MobDifficulty difficulty, LivingEntity entity, int level, LivingDamageEvent event)
    {
        Entity attacker = event.getSource().getAttacker();
        if (!(attacker instanceof LivingEntity living))
        {
            return;
        }
        if (living instanceof EnderDragonEntity)
        {
            return;
        }
        if (living.isOnGround())
        {
            return;
        }
        if (living.isSpectator())
        {
            return;
        }
        if (living instanceof PlayerEntity player &&
            player.isCreative())
        {
            return;
        }
        if (ReflectTrinket.canReflect(living, this))
        {
            return;
        }
        living.addVelocity(0, -level, 0);
        if (living instanceof ServerPlayerEntity)
        {
            living.velocityModified = true;
        }
    }
}
