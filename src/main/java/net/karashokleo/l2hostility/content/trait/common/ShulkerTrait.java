package net.karashokleo.l2hostility.content.trait.common;

import net.karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ShulkerTrait extends IntervalTrait
{
    // 使怪物拥有发射潜影弹的能力。
    // 怪物能够以 2.0秒 的频率发射潜影弹，潜影弹会携带怪物所拥有的词条的能力。
    public ShulkerTrait()
    {
        super(Formatting.LIGHT_PURPLE, () -> LHConfig.common().traits.shulkerInterval);
    }

    @Override
    public void action(MobEntity mob, Data data)
    {
        LivingEntity target = mob.getTarget();
        if (target != null && target.isAlive())
        {
            World world = mob.getWorld();
            var bullet = new ShulkerBulletEntity(world, mob, target, Direction.Axis.Y);
            bullet.setPosition(mob.getX(), mob.getBodyY(0.5) + 0.5, mob.getZ());
            world.spawnEntity(bullet);
            mob.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (mob.getRandom().nextFloat() - mob.getRandom().nextFloat()) * 0.2F + 1.0F);
            super.action(mob, data);
        }
    }
}
