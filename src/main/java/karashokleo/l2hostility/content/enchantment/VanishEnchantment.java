package karashokleo.l2hostility.content.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Formatting;

public class VanishEnchantment extends SingleLevelEnchantment
{
    public VanishEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    public Formatting getColor() {
        return Formatting.RED;
    }
}
