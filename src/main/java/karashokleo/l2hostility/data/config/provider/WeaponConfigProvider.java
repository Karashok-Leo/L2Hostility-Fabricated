package karashokleo.l2hostility.data.config.provider;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.data.Constants;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.leobrary.data.AbstractDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;

public class WeaponConfigProvider extends AbstractDataProvider
{
    public WeaponConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.PARENT_CONFIG_PATH + "/" + Constants.WEAPON_CONFIG_PATH);
    }

    @Override
    public String getName()
    {
        return "LH Weapon Config";
    }

    @Override
    public void addAll()
    {
        add(L2Hostility.id("armors"), new WeaponConfig()
                .putArmor(0, 200, Items.AIR)
                .putArmor(20, 100, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS)
                .putArmor(35, 100, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS)
                .putArmor(45, 100, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS)
                .putArmor(60, 200, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS)
                .putArmor(80, 300, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS)
                .putArmor(100, 100, Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS)
        );

        add(L2Hostility.id("vanilla"), new WeaponConfig()
                .putMeleeWeapon(0, 200, Items.AIR)
                .putMeleeWeapon(30, 100, Items.IRON_AXE, Items.IRON_SWORD)
                .putMeleeWeapon(50, 100, Items.DIAMOND_AXE, Items.DIAMOND_SWORD)
                .putMeleeWeapon(70, 100, Items.NETHERITE_AXE, Items.NETHERITE_SWORD)
                .putRangedWeapon(0, 100,
                        Items.AIR
                )
                .putWeaponEnchantment(30, 0.5f,
                        Enchantments.SHARPNESS,
                        Enchantments.POWER
                )
                .putWeaponEnchantment(40, 0.2f,
                        Enchantments.KNOCKBACK,
                        Enchantments.PUNCH
                )
                .putWeaponEnchantment(50, 0.1f,
                        Enchantments.FIRE_ASPECT,
                        Enchantments.FLAME
                )
                .putArmorEnchantment(30, 0.5f, Enchantments.PROTECTION)
                .putArmorEnchantment(30, 0.2f,
                        Enchantments.PROJECTILE_PROTECTION,
                        Enchantments.BLAST_PROTECTION,
                        Enchantments.FIRE_PROTECTION,
                        Enchantments.FEATHER_FALLING
                )
                .putArmorEnchantment(70, 0.3f, Enchantments.BINDING_CURSE)
        );

        add(L2Hostility.id("l2complements"), new WeaponConfig()
                        .putWeaponEnchantment(100, 0.02f,
                                LHEnchantments.CURSE_BLADE,
                                LHEnchantments.SHARP_BLADE,
                                LHEnchantments.FLAME_BLADE,
                                LHEnchantments.ICE_BLADE
                        )
                        .putWeaponEnchantment(200, 0.01f,
                                LHEnchantments.VOID_TOUCH
                        )
                        .putArmorEnchantment(70, 0.2f,
                                LHEnchantments.STABLE_BODY
//                        LHEnchantments.SNOW_WALKER
                        )
                        .putArmorEnchantment(100, 0.02f,
                                LHEnchantments.ICE_THORN,
                                LHEnchantments.FLAME_THORN,
                                LHEnchantments.SAFEGUARD
                        )
        );
    }
}
