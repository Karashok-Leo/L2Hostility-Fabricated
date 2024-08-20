package karashokleo.l2hostility.content.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class UnobtainableEnchantment extends Enchantment
{
    public UnobtainableEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level)
    {
        return 1;
    }

    @Override
    public int getMaxPower(int level)
    {
        return 5;
    }

    @Override
    public boolean isTreasure()
    {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer()
    {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection()
    {
        return false;
    }

    public Formatting getColor()
    {
        return Formatting.GREEN;
    }

    @Override
    public Text getName(int level)
    {
        MutableText text = Text.translatable(this.getTranslationKey());
        if (level != 1 || this.getMaxLevel() != 1)
            text.append(" ").append(Text.translatable("enchantment.level." + level));
        text.formatted(getColor());
        return text;
    }
}
