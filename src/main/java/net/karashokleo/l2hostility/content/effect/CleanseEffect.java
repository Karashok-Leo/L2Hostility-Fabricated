package net.karashokleo.l2hostility.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class CleanseEffect extends StatusEffect
{
    private static int recursive = 0;

    public static void clearOnEntity(LivingEntity entity)
    {
        recursive++;
        List<StatusEffectInstance> list = new ArrayList<>(entity.getStatusEffects());
        for (StatusEffectInstance ins : list)
        {
            StatusEffect type = ins.getEffectType();
            if (recursive <= 1)
                entity.removeStatusEffect(type);
            if (entity.hasStatusEffect(type))
                entity.getActiveStatusEffects().remove(type);
        }
        recursive--;
    }

    public CleanseEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0xffff7f);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 10 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        clearOnEntity(entity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier)
    {
        clearOnEntity(entity);
    }
}
