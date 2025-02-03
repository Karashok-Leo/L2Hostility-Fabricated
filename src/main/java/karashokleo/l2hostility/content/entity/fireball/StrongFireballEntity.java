package karashokleo.l2hostility.content.entity.fireball;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StrongFireballEntity extends BaseFireballEntity
{
    public StrongFireballEntity(EntityType<? extends BaseFireballEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public StrongFireballEntity(double x, double y, double z, double vx, double vy, double vz, World world)
    {
        super(LHEntities.STRONG_FIREBALL, x, y, z, vx, vy, vz, world);
    }

    public StrongFireballEntity(LivingEntity owner, double vx, double vy, double vz, World world)
    {
        super(LHEntities.STRONG_FIREBALL, owner, vx, vy, vz, world);
    }

    @Override
    protected void onCollisionAction(Vec3d pos)
    {
        super.onCollisionAction(pos);
        int power = LHConfig.common().complements.fireCharge.strongFireChargePower;
        boolean breaking = LHConfig.common().complements.fireCharge.strongFireChargeBreakBlock;
        this.getWorld().createExplosion(this, pos.x, pos.y, pos.z, power, breaking ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.NONE);
    }
}
