package net.karashokleo.l2hostility.content.effect;

import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.util.MathHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.UUID;

public class StoneCageEffect extends StatusEffect
{
    private static final UUID ID_SLOW = MathHelper.getUUIDFromString(L2Hostility.MOD_ID + ":stone_cage_slow");
    private static final UUID ID_FLY = MathHelper.getUUIDFromString(L2Hostility.MOD_ID + ":stone_cage_fly");
    private static final UUID ID_KB = MathHelper.getUUIDFromString(L2Hostility.MOD_ID + ":stone_cage_kb");
    private static final UUID ID_SWIM = MathHelper.getUUIDFromString(L2Hostility.MOD_ID + ":stone_cage_swim");

    public StoneCageEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x000000);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, ID_SLOW.toString(), -1F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(EntityAttributes.GENERIC_FLYING_SPEED, ID_FLY.toString(), -1F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, ID_KB.toString(), 1F, EntityAttributeModifier.Operation.ADDITION);
        addAttributeModifier(PortingLibAttributes.SWIM_SPEED, ID_SWIM.toString(), -1F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        entity.setVelocity(0, 0, 0);
    }
}
