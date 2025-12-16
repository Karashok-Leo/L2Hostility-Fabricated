package karashokleo.l2hostility.content.trait.goals;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public class CounterStrikeTrait extends MobTrait
{
    // 使怪物更容易攻击到目标。
    // 怪物受到攻击时朝攻击者冲刺。
    public CounterStrikeTrait()
    {
        super(Formatting.WHITE);
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity le, int level)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.cooldown > 0)
        {
            data.cooldown--;
            return;
        }
        if (!le.isOnGround())
        {
            return;
        }
        var target = difficulty.owner.getTarget();
        if (target == null || !target.isAlive())
        {
            return;
        }
        if (target.distanceTo(le) > LHConfig.common().traits.counterStrikeRange)
        {
            return;
        }
        if (data.strikeId == null || !data.strikeId.equals(target.getUuid()))
        {
            return;
        }
        Vec3d vec3d = target.getPos().subtract(le.getPos());
        vec3d = vec3d.normalize().multiply(3);
        if (vec3d.y <= 0.2)
        {
            vec3d = vec3d.add(0, 0.2, 0);
        }
        le.setVelocity(vec3d);
        le.velocityDirty = true;
        data.duration = LHConfig.common().traits.counterStrikeDuration;
        data.strikeId = null;
    }

    @Override
    public void onHurt(MobDifficulty difficulty, LivingEntity le, int level, LivingHurtEvent event)
    {
        if (le.getWorld().isClient())
        {
            return;
        }
        var target = event.getSource().getAttacker();
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (target instanceof LivingEntity &&
            le instanceof MobEntity mob &&
            mob.getTarget() == target)
        {
            data.strikeId = target.getUuid();
        }
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int cooldown, duration;

        @SerialClass.SerialField
        public UUID strikeId;
    }
}
