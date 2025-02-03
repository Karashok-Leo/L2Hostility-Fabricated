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

public class SoulFireballEntity extends BaseFireballEntity
{
    public SoulFireballEntity(EntityType<? extends BaseFireballEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public SoulFireballEntity(double x, double y, double z, double vx, double vy, double vz, World world)
    {
        super(LHEntities.SOUL_FIREBALL, x, y, z, vx, vy, vz, world);
    }

    public SoulFireballEntity(LivingEntity owner, double vx, double vy, double vz, World world)
    {
        super(LHEntities.SOUL_FIREBALL, owner, vx, vy, vz, world);
    }

    @Override
    protected void onEntityHitAction(Entity target)
    {
        if (target instanceof LivingEntity le)
        {
            int duration = LHConfig.common().complements.fireCharge.soulFireChargeDuration;
            EffectHelper.forceAddEffectWithEvent(le, new StatusEffectInstance(LHEffects.FLAME, duration), this.getOwner());
        }
        target.damage(this.getWorld().getDamageSources().fireball(this, this.getOwner()), 6.0F);
    }
}
