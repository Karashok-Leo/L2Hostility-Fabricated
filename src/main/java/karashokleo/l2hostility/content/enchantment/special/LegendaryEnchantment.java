package karashokleo.l2hostility.content.enchantment.special;

import karashokleo.l2hostility.content.enchantment.core.SingleLevelEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class LegendaryEnchantment extends SingleLevelEnchantment
{
    public LegendaryEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.GOLD;
    }
}
