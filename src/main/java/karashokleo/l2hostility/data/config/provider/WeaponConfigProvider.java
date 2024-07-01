package karashokleo.l2hostility.data.config.provider;

import karashokleo.leobrary.data.AbstractDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.data.Constants;
import karashokleo.l2hostility.data.config.WeaponConfig;
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
    public void add()
    {
        add(L2Hostility.id("vanilla"), new WeaponConfig()
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
    }
}
