package net.karashokleo.l2hostility.content.item.traits;

import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.util.EffectBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.function.Predicate;

public class EffectBooster
{
    public static void boostCharge(LivingEntity target)
    {
        double factor = 1 + LHConfig.common().traits.drainDuration;
        int maxTime = LHConfig.common().traits.drainDurationMax;
        int min = LHConfig.common().items.witchChargeMinDuration;
        boost(target, e -> e.getCategory() == StatusEffectCategory.HARMFUL, min, factor, maxTime);
    }

    public static void boostBottle(LivingEntity target)
    {
        double factor = 1 + LHConfig.common().traits.drainDuration;
        int maxTime = LHConfig.common().traits.drainDurationMax;
        int min = LHConfig.common().items.witchChargeMinDuration;
        boost(target, e -> true, min, factor, maxTime);
    }

    public static void boostTrait(LivingEntity target, double factor, int maxTime)
    {
        boost(target, e -> e.getCategory() == StatusEffectCategory.HARMFUL, 0, factor, maxTime);
    }

    private static void boost(LivingEntity target, Predicate<StatusEffect> pred, int min, double factor, int maxTime)
    {
        var list = new ArrayList<>(target.getStatusEffects());
        for (var e : list)
        {
            if (pred.test(e.getEffectType()))
            {
                int current = e.getDuration();
                if (current < min) continue;
                int max = Math.min(maxTime, (int) (current * factor));
                if (max > current)
                    new EffectBuilder(e).setDuration(max);
                target.setStatusEffect(e, null);
            }
        }
    }

    public static void boostInfinite(LivingEntity target)
    {
        int min = LHConfig.common().items.witchChargeMinDuration;
        var list = new ArrayList<>(target.getStatusEffects());
        for (var e : list)
        {
            if (e.getEffectType().getCategory() == StatusEffectCategory.HARMFUL)
            {
                int current = e.getDuration();
                if (current < min) continue;
                new EffectBuilder(e).setDuration(StatusEffectInstance.INFINITE);
                target.setStatusEffect(e, null);
            }
        }
    }
}
