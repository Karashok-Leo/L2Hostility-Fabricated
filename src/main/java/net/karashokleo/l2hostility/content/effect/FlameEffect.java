package net.karashokleo.l2hostility.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FlameEffect extends StatusEffect
{
    public FlameEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0xFF0000);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        if (entity.isFireImmune() && entity.hurtByWater()) return;
        entity.damage(
                new DamageSource(
                        entity.getDamageSources().registry.entryOf(DamageTypes.ON_FIRE),
                        null,
                        entity.getLastAttacker()
                ),
                2 << amplifier
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 20 == 0;
    }
}
