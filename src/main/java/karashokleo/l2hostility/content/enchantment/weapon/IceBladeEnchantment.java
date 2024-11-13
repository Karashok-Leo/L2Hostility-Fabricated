package karashokleo.l2hostility.content.enchantment.weapon;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;

public class IceBladeEnchantment extends AbstractBladeEnchantment
{
    public IceBladeEnchantment()
    {
        super(
                Enchantment.Rarity.VERY_RARE,
                EnchantmentTarget.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
        );
    }

    @Override
    protected StatusEffectInstance getEffect(int level)
    {
        return new StatusEffectInstance(LHEffects.ICE, LHConfig.common().complements.properties.iceEnchantDuration << (level - 1));
    }
}
