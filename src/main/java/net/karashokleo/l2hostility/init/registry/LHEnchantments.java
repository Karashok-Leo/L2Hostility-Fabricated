package net.karashokleo.l2hostility.init.registry;

import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.enchantment.HostilityEnchantment;
import net.karashokleo.l2hostility.content.enchantment.RemoveTraitEnchantment;
import net.karashokleo.l2hostility.content.enchantment.SingleLevelEnchantment;
import net.karashokleo.l2hostility.content.enchantment.VanishEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHEnchantments
{
    public static final HostilityEnchantment INSULATOR = new HostilityEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.ARMOR,
            new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET},
            3
    );
    public static final RemoveTraitEnchantment SPLIT_SUPPRESS = new RemoveTraitEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.WEAPON,
            new EquipmentSlot[]{EquipmentSlot.OFFHAND},
            () -> LHTraits.SPLIT
    );
    public static final VanishEnchantment VANISH = new VanishEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.VANISHABLE,
            EquipmentSlot.values()
    );
    public static final SingleLevelEnchantment SHULKER_ARMOR = new SingleLevelEnchantment(
            Enchantment.Rarity.VERY_RARE,
            EnchantmentTarget.VANISHABLE,
            EquipmentSlot.values()
    );

    public static void register()
    {
        Registry.register(Registries.ENCHANTMENT, L2Hostility.id("insulator"), INSULATOR);
        Registry.register(Registries.ENCHANTMENT, L2Hostility.id("split_suppress"), SPLIT_SUPPRESS);
        Registry.register(Registries.ENCHANTMENT, L2Hostility.id("vanish"), VANISH);
        Registry.register(Registries.ENCHANTMENT, L2Hostility.id("shulker_armor"), SHULKER_ARMOR);
    }
}
