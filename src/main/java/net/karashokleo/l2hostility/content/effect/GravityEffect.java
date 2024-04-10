package net.karashokleo.l2hostility.content.effect;

import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import net.karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class GravityEffect extends StatusEffect
{
    private static final double FACTOR = 1.3;

    public GravityEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0x3f3f3f);
        addAttributeModifier(PortingLibAttributes.ENTITY_GRAVITY, MathHelper.getUUIDFromString("gravity").toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier)
    {
        return Math.pow(FACTOR, amplifier + 1) - 1;
    }
}
