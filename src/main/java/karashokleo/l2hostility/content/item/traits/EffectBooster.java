package karashokleo.l2hostility.content.item.traits;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.leobrary.effect.api.util.StatusEffectInstanceBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
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

    public static void boostTrait(LivingEntity target, int level)
    {
        double factor = 1 + LHConfig.common().traits.drainDuration * level;
        int maxTime = level * LHConfig.common().traits.drainDurationMax;
        boost(target, e -> e.getCategory() == StatusEffectCategory.HARMFUL && drainAllow(e), 0, factor, maxTime);
    }

    public static boolean drainAllow(StatusEffect effect)
    {
        List<String> blacklist = LHConfig.common().traits.drainBlacklist;
        Identifier id = Registries.STATUS_EFFECT.getId(effect);
        return id == null || !blacklist.contains(id.toString());
    }

    private static void boost(LivingEntity target, Predicate<StatusEffect> pred, int min, double factor, int maxTime)
    {
        var list = new ArrayList<>(target.getStatusEffects());
        for (var e : list)
        {
            if (pred.test(e.getEffectType()))
            {
                int current = e.getDuration();
                if (current < min)
                {
                    continue;
                }
                int max = Math.min(maxTime, (int) (current * factor));
                if (max > current)
                {
                    StatusEffectInstance newEffect = new StatusEffectInstanceBuilder(e).setDuration(max).build();
                    target.setStatusEffect(newEffect, null);
                }
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
                if (current < min)
                {
                    continue;
                }
                StatusEffectInstance newEffect = new StatusEffectInstanceBuilder(e).setDuration(StatusEffectInstance.INFINITE).build();
                target.setStatusEffect(newEffect, null);
            }
        }
    }
}
