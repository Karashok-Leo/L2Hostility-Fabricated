package karashokleo.l2hostility.content.effect;

import karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class ArmorReduceEffect extends StatusEffect
{
    private static final UUID ID = MathHelper.getUUIDFromIdentifier("armor_reduce");

    public ArmorReduceEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0xFFFFFF);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, ID.toString(), -0.5f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double adjustModifierAmount(int amplifier, EntityAttributeModifier modifier)
    {
        return Math.pow(1 + modifier.getValue(), amplifier + 1) - 1;
    }
}
