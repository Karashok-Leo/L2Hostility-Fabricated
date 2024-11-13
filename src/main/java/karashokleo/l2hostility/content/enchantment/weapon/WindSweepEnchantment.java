package karashokleo.l2hostility.content.enchantment.weapon;

import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import karashokleo.l2hostility.content.enchantment.core.AttributeEnchantment;
import karashokleo.l2hostility.content.enchantment.core.UnobtainableEnchantment;
import karashokleo.l2hostility.util.MathHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class WindSweepEnchantment extends UnobtainableEnchantment implements AttributeEnchantment
{
    private static final String MODIFIER_NAME = "WindSweep modifier";
    private static final UUID MODIFIER_UUID = MathHelper.getUUIDFromString(MODIFIER_NAME);

    public WindSweepEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public void addAttributes(int lv, ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers)
    {
        if (slot != EquipmentSlot.MAINHAND) return;
        attributeModifiers.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(MODIFIER_UUID, MODIFIER_NAME, lv, EntityAttributeModifier.Operation.ADDITION));
    }
}
