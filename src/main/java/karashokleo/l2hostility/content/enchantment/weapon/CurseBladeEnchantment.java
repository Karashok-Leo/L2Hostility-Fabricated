package karashokleo.l2hostility.content.enchantment.weapon;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;

public class CurseBladeEnchantment extends AbstractBladeEnchantment
{
    public CurseBladeEnchantment()
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
        return new StatusEffectInstance(LHEffects.CURSE, LHConfig.common().complements.properties.curseEnchantDuration << (level - 1));
    }
}
