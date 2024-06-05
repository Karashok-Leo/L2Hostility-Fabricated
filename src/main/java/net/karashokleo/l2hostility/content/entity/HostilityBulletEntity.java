package net.karashokleo.l2hostility.content.entity;

import com.google.common.base.MoreObjects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HostilityBulletEntity extends ShulkerBulletEntity
{
    private int level;

    public HostilityBulletEntity(EntityType<? extends ShulkerBulletEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public HostilityBulletEntity(World world, LivingEntity owner, Entity target, Direction.Axis axis)
    {
        super(world, owner, target, axis);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)
    {
        Entity target = entityHitResult.getEntity();
        Entity owner = this.getOwner();
        LivingEntity livingEntity = owner instanceof LivingEntity ? (LivingEntity)owner : null;
        boolean bl = target.damage(this.getDamageSources().mobProjectile(this, livingEntity), 4.0F);
        if (bl) {
            this.applyDamageEffects(livingEntity, target);
        }
    }
}
