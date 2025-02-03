package karashokleo.l2hostility.content.entity.fireball;

import karashokleo.l2hostility.content.item.traits.EffectBooster;
import karashokleo.l2hostility.init.LHEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class WitchFireballEntity extends BaseFireballEntity
{
    public WitchFireballEntity(EntityType<? extends BaseFireballEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public WitchFireballEntity(double x, double y, double z, double vx, double vy, double vz, World world)
    {
        super(LHEntities.WITCH_FIREBALL, x, y, z, vx, vy, vz, world);
    }

    public WitchFireballEntity(LivingEntity owner, double vx, double vy, double vz, World world)
    {
        super(LHEntities.WITCH_FIREBALL, owner, vx, vy, vz, world);
    }

    @Override
    protected void onEntityHitAction(Entity target)
    {
        if (target instanceof LivingEntity living)
            EffectBooster.boostCharge(living);
    }
}
