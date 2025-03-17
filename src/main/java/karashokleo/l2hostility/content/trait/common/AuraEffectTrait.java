package karashokleo.l2hostility.content.trait.common;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.content.network.S2CEffectAura;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.l2hostility.util.EffectHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.function.Supplier;

public class AuraEffectTrait extends MobTrait
{
    protected static final int TICK_AURA_INTERNAL = 10;

    protected final Supplier<StatusEffect> effect;

    public AuraEffectTrait(Supplier<StatusEffect> effect)
    {
        super(() -> effect.get().getColor());
        this.effect = effect;
    }

    protected boolean canApply(LivingEntity e)
    {
        return !ReflectTrinket.canReflect(e, this);
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity mob, int level)
    {
        if (mob.age % TICK_AURA_INTERNAL != 0) return;
        int range = LHConfig.common().traits.auraRange.get(getId().getPath());
        LHNetworking.toTracking(mob, new S2CEffectAura(mob, range, effect.get().getColor()));
        for (var e : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(range), EntityPredicates.VALID_ENTITY))
        {
            if (e instanceof PlayerEntity pl && pl.getAbilities().creativeMode) continue;
            if (e.distanceTo(mob) > range) continue;
            if (!canApply(e)) continue;
            StatusEffectInstance newEffect = new StatusEffectInstance(effect.get(), 40, level - 1, true, true);
            EffectHelper.forceAddEffectWithEvent(e, newEffect, mob);
        }
    }
}
