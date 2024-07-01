package karashokleo.l2hostility.content.effect;

import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class GravityEffect extends StatusEffect
{
    private static final UUID ID = MathHelper.getUUIDFromIdentifier("gravity");
    private static final double FACTOR = 1.3;

    public GravityEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0x3f3f3f);
        addAttributeModifier(PortingLibAttributes.ENTITY_GRAVITY, ID.toString(), FACTOR, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier)
    {
        return Math.pow(FACTOR, amplifier + 1) - 1;
    }
}
