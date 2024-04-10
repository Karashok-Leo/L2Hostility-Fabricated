package net.karashokleo.l2hostility.content.trait.common;

import net.karashokleo.l2hostility.config.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrenadeTrait extends IntervalTrait
{
    // 使怪物拥有发射会爆炸的潜影弹的能力。
    // 怪物能够发射会爆炸的潜影弹，在上一个子弹消失后的 3.0秒 发射下一个，爆炸不会破坏方块，每级使爆炸威力额外增加。
    // 拥有该词条的怪物免疫爆炸伤害。
    public GrenadeTrait()
    {
        super(Formatting.RED, () -> LHConfig.common().traits.grenadeInterval);
    }

    @Override
    public void action(MobEntity mob, Data data)
    {
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive())
        {
            World world = mob.getWorld();
            Vec3d vec3d = mob.getRotationVec(1.0F);
            double f = target.getX() - (mob.getX() + vec3d.x * 4.0);
            double g = target.getBodyY(0.5) - (0.5 + mob.getBodyY(0.5));
            double h = target.getZ() - (mob.getZ() + vec3d.z * 4.0);
            var fireball = new FireballEntity(world, mob, f, g, h, 1);
            fireball.setPosition(mob.getX() + vec3d.x * 4.0, mob.getBodyY(0.5) + 0.5, mob.getZ() + vec3d.z * 4.0);
            world.spawnEntity(fireball);
            mob.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (mob.getRandom().nextFloat() - mob.getRandom().nextFloat()) * 0.2F + 1.0F);
            super.action(mob, data);
        }
    }
}
