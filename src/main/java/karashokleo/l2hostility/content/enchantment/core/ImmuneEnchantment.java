package karashokleo.l2hostility.content.enchantment.core;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHCplTexts;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ImmuneEnchantment extends UnobtainableEnchantment
{
    public ImmuneEnchantment()
    {
        super(
                Rarity.VERY_RARE,
                EnchantmentTarget.ARMOR,
                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}
        );
    }

    @Override
    protected boolean canAccept(Enchantment other)
    {
        return !(other instanceof ImmuneEnchantment);
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.GOLD;
    }

    @Override
    public Text getName(int level)
    {
        Text name = super.getName(level);
        if (LHConfig.common().complements.enableImmunityEnchantments)
            return name;
        return LHCplTexts.BANNED_ENCH.get()
                .formatted(Formatting.DARK_RED)
                .append(" ")
                .append(name.copy().formatted(Formatting.DARK_GRAY, Formatting.STRIKETHROUGH));
    }
}
