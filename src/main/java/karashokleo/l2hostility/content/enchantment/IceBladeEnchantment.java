package karashokleo.l2hostility.content.enchantment;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;

public class IceBladeEnchantment extends AbstractBladeEnchantment
{
    public IceBladeEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    protected StatusEffectInstance getEffect(int level)
    {
        return new StatusEffectInstance(LHEffects.ICE, LHConfig.common().complements.properties.iceEnchantDuration << (level - 1));
    }
}
