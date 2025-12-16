package karashokleo.l2hostility.content.enchantment.weapon;

import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Formatting;

public class VoidTouchEnchantment extends UnobtainableEnchantment
{
    public VoidTouchEnchantment()
    {
        super(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.WEAPON,
            new EquipmentSlot[]{EquipmentSlot.MAINHAND}
        );
    }

    private double getChance(DamageSource source, int level)
    {
        double chance = LHConfig.common().complements.properties.voidTouchChance * level;
        if (source != null)
        {
            if (source.isIn(DamageTypeTags.BYPASSES_ARMOR))
            {
                chance += LHConfig.common().complements.properties.voidTouchChanceBonus;
            }
            if (source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
                source.isIn(DamageTypeTags.BYPASSES_ENCHANTMENTS))
            {
                chance += LHConfig.common().complements.properties.voidTouchChanceBonus;
            }
        }
        return chance;
    }

    public boolean allow(LivingEntity living, DamageSource source)
    {
        int level = EnchantmentHelper.getEquipmentLevel(this, living);
        if (level <= 0)
        {
            return false;
        }
        double chance = getChance(source, level);
        return living.getRandom().nextDouble() < chance;
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
    public Formatting getColor()
    {
        return Formatting.GOLD;
    }
}
