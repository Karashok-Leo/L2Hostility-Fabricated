package karashokleo.l2hostility.content.enchantment.special;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class LifeMendingEnchantment extends UnobtainableEnchantment
{
    public LifeMendingEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public int getMinPower(int level)
    {
        return 1;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.LIGHT_PURPLE;
    }
}
