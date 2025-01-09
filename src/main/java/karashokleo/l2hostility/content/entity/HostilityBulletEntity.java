package karashokleo.l2hostility.content.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class HostilityBulletEntity extends ShulkerBulletEntity
{
    public static final String DAMAGE_KEY = "Damage";
    private float damage;

    public static HostilityBulletEntity typeFactory(EntityType<? extends ShulkerBulletEntity> entityType, World world)
    {
        return new HostilityBulletEntity(entityType, world);
    }

    private HostilityBulletEntity(EntityType<? extends ShulkerBulletEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public HostilityBulletEntity(World world, LivingEntity owner, Entity target, Direction.Axis axis, float damage)
    {
        super(world, owner, target, axis);
        this.damage = damage;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)
    {
        Entity target = entityHitResult.getEntity();
        LivingEntity attacker = this.getOwner() instanceof LivingEntity living ? living : null;
        boolean damaged = target.damage(this.getDamageSources().mobProjectile(this, attacker), this.damage);
        if (damaged)
        {
            this.applyDamageEffects(attacker, target);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt)
    {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat(DAMAGE_KEY, this.damage);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt)
    {
        super.readCustomDataFromNbt(nbt);
        this.damage = nbt.getFloat(DAMAGE_KEY);
    }
}
