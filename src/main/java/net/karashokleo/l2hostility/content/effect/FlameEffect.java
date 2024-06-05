package net.karashokleo.l2hostility.content.effect;

import karashokleo.effect_overlay.api.IconEffectRenderer;
import karashokleo.effect_overlay.api.IconOverlayEffect;
import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FlameEffect extends StatusEffect implements IconOverlayEffect
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

    @Override
    public IconEffectRenderer getIcon(LivingEntity entity, int lv)
    {
        return IconEffectRenderer.icon(entity, L2Hostility.id("textures/effect_overlay/flame.png"));
    }
}
