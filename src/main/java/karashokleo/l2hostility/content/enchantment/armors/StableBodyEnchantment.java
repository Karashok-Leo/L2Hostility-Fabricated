package karashokleo.l2hostility.content.enchantment.armors;

import com.google.common.collect.Multimap;
import karashokleo.l2hostility.content.enchantment.core.AttributeEnchantment;
import karashokleo.l2hostility.content.enchantment.core.SingleLevelEnchantment;
import karashokleo.l2hostility.util.MathHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class StableBodyEnchantment extends SingleLevelEnchantment implements AttributeEnchantment
{
    public static final UUID ID = MathHelper.getUUIDFromString("stable_body");

    public StableBodyEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[]{EquipmentSlot.CHEST});
    }

    @Override
    public void addAttributes(int lv, ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers)
    {
        if (slot != EquipmentSlot.CHEST) return;
        attributeModifiers.put(
                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                new EntityAttributeModifier(ID, "stable_body", 1, EntityAttributeModifier.Operation.ADDITION)
        );
    }
}
