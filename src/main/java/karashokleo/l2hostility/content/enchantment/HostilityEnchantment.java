package karashokleo.l2hostility.content.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class HostilityEnchantment extends UnobtainableEnchantment
{
    private final int maxLv;

    public HostilityEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes, int maxLv)
    {
        super(weight, target, slotTypes);
        this.maxLv = maxLv;
    }

    @Override
    public int getMaxLevel()
    {
        return maxLv;
    }
}
