package karashokleo.l2hostility.content.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.Nullable;

public interface StackingEffect
{
    // Used by SharpBladeEnchantment
    default void addTo(LivingEntity target, int dur, int max, @Nullable Entity source)
    {
        var old = target.getStatusEffect(asEffect());
        StatusEffectInstance ins = old == null ? new StatusEffectInstance(asEffect(), dur) : new StatusEffectInstance(asEffect(), Math.max(dur, old.getDuration()), Math.min(max, old.getAmplifier() + 1));
        target.addStatusEffect(ins, source);
    }

    default StatusEffect asEffect()
    {
        return (StatusEffect) this;
    }
}
