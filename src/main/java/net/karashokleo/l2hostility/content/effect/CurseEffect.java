package net.karashokleo.l2hostility.content.effect;

import karashokleo.effect_overlay.api.IconEffectRenderer;
import karashokleo.effect_overlay.api.IconOverlayEffect;
import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CurseEffect extends StatusEffect implements IconOverlayEffect
{
    public CurseEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x3f3f3f);
    }

    @Override
    public IconEffectRenderer getIcon(LivingEntity entity, int lv)
    {
        return IconEffectRenderer.icon(entity, L2Hostility.id("textures/effect_overlay/curse.png"));
    }
}
