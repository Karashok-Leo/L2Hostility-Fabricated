package karashokleo.l2hostility.content.entity.fireball;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHEntities;
import karashokleo.l2hostility.util.EffectHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;

public class BlackFireballEntity extends BaseFireballEntity
{
    public BlackFireballEntity(EntityType<? extends BaseFireballEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public BlackFireballEntity(double x, double y, double z, double vx, double vy, double vz, World world)
    {
        super(LHEntities.BLACK_FIREBALL, x, y, z, vx, vy, vz, world);
    }

    public BlackFireballEntity(LivingEntity owner, double vx, double vy, double vz, World world)
    {
        super(LHEntities.BLACK_FIREBALL, owner, vx, vy, vz, world);
    }

    @Override
    protected void onEntityHitAction(Entity target)
    {
        if (target instanceof LivingEntity le)
        {
            int duration = LHConfig.common().complements.fireCharge.blackFireChargeDuration;
            EffectHelper.forceAddEffectWithEvent(le, new StatusEffectInstance(LHEffects.STONE_CAGE, duration), this.getOwner());
        }
        target.damage(this.getWorld().getDamageSources().fireball(this, this.getOwner()), 6.0F);
    }
}
