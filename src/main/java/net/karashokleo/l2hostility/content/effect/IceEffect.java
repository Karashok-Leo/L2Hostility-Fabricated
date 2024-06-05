package net.karashokleo.l2hostility.content.effect;

import karashokleo.effect_overlay.api.IconEffectRenderer;
import karashokleo.effect_overlay.api.IconOverlayEffect;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class IceEffect extends StatusEffect implements IconOverlayEffect
{
    private static final UUID ID = MathHelper.getUUIDFromIdentifier("ice");

    public IceEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x7f7fff);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, ID.toString(), -0.6F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        entity.setInPowderSnow(true);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public IconEffectRenderer getIcon(LivingEntity entity, int lv)
    {
        return IconEffectRenderer.icon(entity, L2Hostility.id("textures/effect_overlay/ice.png"));
    }
}
