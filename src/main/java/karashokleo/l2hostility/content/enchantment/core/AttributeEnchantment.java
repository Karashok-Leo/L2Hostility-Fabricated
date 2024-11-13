package karashokleo.l2hostility.content.enchantment.core;

import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

public interface AttributeEnchantment
{
    void addAttributes(int lv, ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers);
}
