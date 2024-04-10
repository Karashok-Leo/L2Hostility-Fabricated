package net.karashokleo.l2hostility.content.effect;

import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class IceEffect extends StatusEffect
{
    private static final String UUID = MathHelper.getUUIDFromString(L2Hostility.MOD_ID + ":ice").toString();

    public IceEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x7f7fff);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, UUID, -0.6F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
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
}
