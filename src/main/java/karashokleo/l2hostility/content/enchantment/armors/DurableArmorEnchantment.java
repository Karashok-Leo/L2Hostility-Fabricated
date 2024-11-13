package karashokleo.l2hostility.content.enchantment.armors;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class DurableArmorEnchantment extends UnobtainableEnchantment
{
    public DurableArmorEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR, EquipmentSlot.values());
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    protected boolean canAccept(Enchantment other)
    {
        return other != Enchantments.UNBREAKING && super.canAccept(other);
    }
}
