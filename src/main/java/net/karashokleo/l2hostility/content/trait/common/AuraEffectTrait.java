package net.karashokleo.l2hostility.content.trait.common;

import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.content.network.S2CAuraEffect;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHItems;
import net.karashokleo.l2hostility.init.LHNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

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
        return !TrinketCompat.hasItemInTrinket(e, LHItems.RING_REFLECTION) &&
                !TrinketCompat.hasItemInTrinket(e, LHItems.ABRAHADABRA);
    }

    @Override
    public void serverTick(LivingEntity mob, int level)
    {
        int range = LHConfig.common().range.get(getId().getPath());
        LHNetworking.toTracking(mob, new S2CAuraEffect(mob, range, effect.get().getColor()));
        for (var e : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(range), EntityPredicates.VALID_ENTITY))
        {
            if (e instanceof PlayerEntity pl && pl.getAbilities().creativeMode) continue;
            if (e.distanceTo(mob) > range) continue;
            if (!canApply(e)) continue;
            e.addStatusEffect(new StatusEffectInstance(effect.get(), 40, level - 1, true, true), mob);
        }
    }
}
