package net.karashokleo.l2hostility.content.trait.goals;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.component.mob.CapStorageData;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
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
    public void tick(LivingEntity le, int level)
    {
        if (le.getWorld().isClient()) return;
        var diff = MobDifficulty.get(le);
        if (diff.isEmpty()) return;
        var data = diff.get().getOrCreateData(getId(), Data::new);
        if (data.cooldown > 0)
        {
            data.cooldown--;
            return;
        }
        if (!le.isOnGround()) return;
        var target = diff.get().owner.getTarget();
        if (target == null || !target.isAlive()) return;
        if (data.strikeId == null || !data.strikeId.equals(target.getUuid())) return;
        Vec3d vec3d = target.getPos().subtract(le.getPos());
        vec3d = vec3d.normalize().multiply(3);
        if (vec3d.y <= 0.2)
            vec3d = vec3d.add(0, 0.2, 0);
        le.setVelocity(vec3d);
        le.velocityDirty = true;
        data.duration = LHConfig.common().traits.counterStrikeDuration;
        data.strikeId = null;
    }

    @Override
    public void onHurt(int level, LivingEntity le, LivingHurtEvent event)
    {
        if (le.getWorld().isClient()) return;
        var target = event.getSource().getAttacker();
        var diff = MobDifficulty.get(le);
        if (diff.isEmpty()) return;
        var data = diff.get().getOrCreateData(getId(), Data::new);
        if (target instanceof LivingEntity && le instanceof MobEntity mob && mob.getTarget() == target)
            data.strikeId = target.getUuid();
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
