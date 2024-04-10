package net.karashokleo.l2hostility.content.trait.common;

import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class AuraEffectTrait extends MobTrait
{
    private final Supplier<StatusEffect> effect;

    public AuraEffectTrait(Supplier<StatusEffect> effect)
    {
        super(() -> effect.get().getColor());
        this.effect = effect;
    }

    protected boolean canApply(LivingEntity e)
    {
//        if (CurioCompat.hasItemInCurio(e, LHItems.RING_REFLECTION.get())) return false;
//        if (CurioCompat.hasItemInCurio(e, LHItems.ABRAHADABRA.get())) return false;
        return true;
    }

    @Override
    public void tick(LivingEntity mob, int level)
    {
        int range = LHConfig.common().range.get(getId().getPath());
        if (mob.getWorld().isClient())
        {
            Vec3d center = mob.getPos();
            float tpi = (float) (Math.PI * 2);
            Vec3d v0 = new Vec3d(0, range, 0);
            v0 = v0.rotateX(tpi / 4).rotateY(mob.getRandom().nextFloat() * tpi);
            int k = effect.get().getColor();
            mob.getWorld().addImportantParticle(
                    ParticleTypes.EFFECT,
                    center.x + v0.x,
                    center.y + v0.y + 0.5f,
                    center.z + v0.z,
                    (k >> 16 & 255) / 255.0,
                    (k >> 8 & 255) / 255.0,
                    (k & 255) / 255.0
            );
        } else
            for (var e : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(range), EntityPredicates.VALID_ENTITY))
            {
                if (e instanceof PlayerEntity pl && pl.getAbilities().creativeMode) continue;
                if (e.distanceTo(mob) > range) continue;
                if (!canApply(e)) continue;
                e.addStatusEffect(new StatusEffectInstance(effect.get(), 40, level - 1, true, true), mob);
            }
    }
}
