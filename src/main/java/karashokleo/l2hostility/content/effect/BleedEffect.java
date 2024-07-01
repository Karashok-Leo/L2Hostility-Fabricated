package karashokleo.l2hostility.content.effect;

import karashokleo.effect_overlay.api.IconEffectRenderer;
import karashokleo.effect_overlay.api.IconOverlayEffect;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.init.LHDamageTypes;
import karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class BleedEffect extends StatusEffect implements IconOverlayEffect, StackingEffect
{
    private static final UUID ID_SLOW = MathHelper.getUUIDFromIdentifier("bleed_slow");
    private static final UUID ID_ATK = MathHelper.getUUIDFromIdentifier("bleed_atk");

    public BleedEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x7f0000);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, ID_SLOW.toString(), -0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, ID_ATK.toString(), -0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier)
    {
        return Math.pow(1 + modifier.getValue(), amplifier + 1) - 1;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        entity.damage(entity.getDamageSources().create(LHDamageTypes.BLEED), 6 * (amplifier + 1));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 60 == 0;
    }

    @Override
    public IconEffectRenderer getIcon(LivingEntity entity, int lv)
    {
        return IconEffectRenderer.icon(entity, L2Hostility.id("textures/effect_overlay/bleed.png"));
    }
}
