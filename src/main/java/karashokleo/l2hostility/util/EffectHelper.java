package karashokleo.l2hostility.util;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class EffectHelper
{
    public static void forceAddEffectWithEvent(LivingEntity entity, StatusEffectInstance newEffect, Entity source)
    {
        MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(entity, newEffect);
        event.sendEvent();
        if (event.getResult() == BaseEvent.Result.DENY)
        {
            return;
        }

        StatusEffectInstance oldEffect = entity.getActiveStatusEffects().get(newEffect.getEffectType());
        if (EffectUtil.forceAddEffect(entity, newEffect, source))
        {
            new MobEffectEvent.Added(entity, oldEffect, newEffect, source).sendEvent();
        }
    }

    public static MutableText getEffectInstanceText(StatusEffectInstance instance)
    {
        StatusEffect effect = instance.getEffectType();
        MutableText text = Text.translatable(effect.getTranslationKey());
        if (instance.getAmplifier() > 0)
        {
            text = Text.translatable("potion.withAmplifier", text, Text.translatable("potion.potency." + instance.getAmplifier()));
        }
        if (instance.getDuration() > 20)
        {
            text = Text.translatable("potion.withDuration", text, StatusEffectUtil.getDurationText(instance, 1));
        }
        return text.formatted(effect.getCategory().getFormatting());
    }
}
