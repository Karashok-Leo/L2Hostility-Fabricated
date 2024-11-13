package karashokleo.l2hostility.content.enchantment;

import karashokleo.l2hostility.content.enchantment.core.SingleLevelEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class VanishEnchantment extends SingleLevelEnchantment
{
    public VanishEnchantment()
    {
        super(
                Enchantment.Rarity.VERY_RARE,
                EnchantmentTarget.VANISHABLE,
                EquipmentSlot.values()
        );
    }

    public Formatting getColor()
    {
        return Formatting.RED;
    }
}
