package net.karashokleo.l2hostility.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

public class EffectUtil
{
    public static void refreshEffect(LivingEntity entity, StatusEffectInstance instance)
    {
        refreshEffect(entity, instance, null);
    }

    public static void refreshEffect(LivingEntity entity, StatusEffectInstance instance, @Nullable Entity source)
    {
        StatusEffectInstance old = entity.getStatusEffect(instance.getEffectType());
        if (old == null ||
                old.getAmplifier() < instance.getAmplifier() ||
                old.getDuration() < instance.getDuration())
            entity.addStatusEffect(instance, source);
    }
}
