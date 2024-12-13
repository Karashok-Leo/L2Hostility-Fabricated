package karashokleo.l2hostility.util;

import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public class EffectHelper
{
    public static void forceAddEffectWithEvent(LivingEntity entity, StatusEffectInstance newEffect, Entity source)
    {
        StatusEffectInstance oldEffect = entity.getActiveStatusEffects().get(newEffect.getEffectType());
        if (EffectUtil.forceAddEffect(entity, newEffect, source))
            new MobEffectEvent.Added(entity, oldEffect, newEffect, source).sendEvent();
    }
}
