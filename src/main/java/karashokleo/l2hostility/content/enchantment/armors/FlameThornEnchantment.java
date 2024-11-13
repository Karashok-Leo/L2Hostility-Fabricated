package karashokleo.l2hostility.content.enchantment.armors;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;

public class FlameThornEnchantment extends AbstractThornEnchantment
{
    public FlameThornEnchantment()
    {
        super(
                Rarity.VERY_RARE,
                EnchantmentTarget.ARMOR,
                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}
        );
    }

    @Override
    protected StatusEffectInstance getEffect(int level)
    {
        return new StatusEffectInstance(LHEffects.FLAME, LHConfig.common().complements.properties.iceEnchantDuration, level - 1);
    }
}
