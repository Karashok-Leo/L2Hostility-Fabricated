package net.karashokleo.l2hostility.data.provider.cfg;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.WeaponConfig;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;

public class WeaponConfigProvider extends ConfigDataProvider
{
    public WeaponConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.WEAPON_CONFIG_PATH);
    }

    @Override
    public void add(Collector collector)
    {
        collector.add(L2Hostility.id("vanilla"), new WeaponConfig()
                .putMeleeWeapon(0, 200, Items.AIR)
                .putMeleeWeapon(30, 100, Items.IRON_AXE, Items.IRON_SWORD)
                .putMeleeWeapon(50, 100, Items.DIAMOND_AXE, Items.DIAMOND_SWORD)
                .putMeleeWeapon(70, 100, Items.NETHERITE_AXE, Items.NETHERITE_SWORD)
                .putRangedWeapon(0, 100,
                        Items.AIR
                )
                .putWeaponEnch(30, 0.5f,
                        Enchantments.SHARPNESS,
                        Enchantments.POWER
                )
                .putWeaponEnch(40, 0.2f,
                        Enchantments.KNOCKBACK,
                        Enchantments.PUNCH
                )
                .putWeaponEnch(50, 0.1f,
                        Enchantments.FIRE_ASPECT,
                        Enchantments.FLAME
                )
                .putArmorEnch(30, 0.5f, Enchantments.PROTECTION)
                .putArmorEnch(30, 0.2f,
                        Enchantments.PROJECTILE_PROTECTION,
                        Enchantments.BLAST_PROTECTION,
                        Enchantments.FIRE_PROTECTION,
                        Enchantments.FEATHER_FALLING
                )
                .putArmorEnch(70, 0.3f, Enchantments.BINDING_CURSE)
        );
//
//        collector.add(L2Hostility.WEAPON, new Identifier(L2Complements.MODID, "l2complements"), new WeaponConfig()
//                .putWeaponEnch(100, 0.02f,
//                        LCEnchantments.CURSE_BLADE.get(),
//                        LCEnchantments.SHARP_BLADE.get(),
//                        LCEnchantments.FLAME_BLADE.get(),
//                        LCEnchantments.ICE_BLADE.get()
//                )
//                .putWeaponEnch(200, 0.01f,
//                        LCEnchantments.VOID_TOUCH.get()
//                )
//                .putArmorEnch(70, 0.2f,
//                        LCEnchantments.STABLE_BODY.get(),
//                        LCEnchantments.SNOW_WALKER.get()
//                )
//                .putArmorEnch(100, 0.02f,
//                        LCEnchantments.ICE_THORN.get(),
//                        LCEnchantments.FLAME_THORN.get(),
//                        LCEnchantments.SAFEGUARD.get()
//                )
//        );
//
//
//        collector.add(L2Hostility.WEAPON, new Identifier(L2Weaponry.MODID, "weapons"), new WeaponConfig()
//                .putMeleeWeapon(200, 10,
//                        LWItems.STORM_JAVELIN.get(),
//                        LWItems.FLAME_AXE.get(),
//                        LWItems.FROZEN_SPEAR.get()
//                )
//                .putMeleeWeapon(300, 5,
//                        LWItems.ABYSS_MACHETE.get(),
//                        LWItems.HOLY_AXE.get(),
//                        LWItems.BLACK_AXE.get()
//                )
//                .putMeleeWeapon(400, 2,
//                        LWItems.CHEATER_CLAW.get(),
//                        LWItems.CHEATER_MACHETE.get()
//                )
//        );
//
//        collector.add(L2Hostility.WEAPON, new Identifier(L2Archery.MODID, "bows"), new WeaponConfig()
//                .putRangedWeapon(50, 10,
//                        ArcheryItems.STARTER_BOW.get()
//                )
//                .putRangedWeapon(70, 5,
//                        ArcheryItems.IRON_BOW.get(),
//                        ArcheryItems.MASTER_BOW.get()
//                )
//                .putRangedWeapon(100, 2,
//                        ArcheryItems.FLAME_BOW.get(),
//                        ArcheryItems.BLACKSTONE_BOW.get(),
//                        ArcheryItems.TURTLE_BOW.get(),
//                        ArcheryItems.EAGLE_BOW.get(),
//                        ArcheryItems.EXPLOSION_BOW.get(),
//                        ArcheryItems.FROZE_BOW.get()
//                )
//        );
    }
}
