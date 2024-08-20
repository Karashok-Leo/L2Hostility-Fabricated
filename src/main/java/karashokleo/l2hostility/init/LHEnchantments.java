package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.enchantment.*;
import karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class LHEnchantments
{
    public static HostilityEnchantment INSULATOR;
    public static RemoveTraitEnchantment SPLIT_SUPPRESS;
    public static VanishEnchantment VANISH;
    public static SingleLevelEnchantment SHULKER_ARMOR;
    public static CurseBladeEnchantment CURSE_BLADE;
    public static SoulFlameBladeEnchantment FLAME_BLADE;
    public static IceBladeEnchantment ICE_BLADE;

    public static void register()
    {
        INSULATOR = Entry.of(
                        "insulator",
                        new HostilityEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.ARMOR,
                                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET},
                                3
                        )
                )
                .addEN()
                .addENDesc("Reduce trait effects that pulls or pushes you.")
                .addZH("绝缘")
                .addZHDesc("减少词条效果对你施加的推挤或者拉扯")
                .register();
        SPLIT_SUPPRESS = Entry.of(
                        "split_suppress",
                        new RemoveTraitEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.WEAPON,
                                new EquipmentSlot[]{EquipmentSlot.OFFHAND},
                                () -> LHTraits.SPLIT
                        )
                )
                .addEN()
                .addENDesc("Disable Split trait on enemies on hit.")
                .addZH("分裂抑制")
                .addZHDesc("击中目标时删除分裂词条")
                .register();
        VANISH = Entry.of(
                        "vanish",
                        new VanishEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.VANISHABLE,
                                EquipmentSlot.values()
                        )
                )
                .addEN()
                .addENDesc("This item vanishes when on ground or in hand of survival / adventure player.")
                .addZH("毁灭")
                .addZHDesc("掉落或者被生存玩家持有时消失")
                .register();
        SHULKER_ARMOR = Entry.of(
                        "shulker_armor",
                        new SingleLevelEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.VANISHABLE,
                                EquipmentSlot.values()
                        )
                )
                .addEN("Transparent")
                .addENDesc("Armor invisible to mobs and players when wearer has invisibility effect.")
                .addZH("遁隐")
                .addZHDesc("玩家隐身时，物品也隐身")
                .register();
        CURSE_BLADE = Entry.of(
                        "cursed_blade",
                        new CurseBladeEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.WEAPON,
                                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
                        )
                )
                .addEN()
                .addENDesc("Apply cursed effect to target. Higher levels have longer duration.")
                .addZH("诅咒之刃")
                .addZHDesc("对敌人施加诅咒效果。更高级的附魔延长效果时间")
                .register();
        FLAME_BLADE = Entry.of(
                        "soul_flame_blade",
                        new SoulFlameBladeEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.WEAPON,
                                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
                        )
                )
                .addEN()
                .addENDesc("Apply flame effect to target. Higher levels have higher damage.")
                .addZH("炎刃")
                .addZHDesc("对敌人施加魂火效果。更高级的附魔造成的伤害更高")
                .register();
        ICE_BLADE = Entry.of(
                        "ice_blade",
                        new IceBladeEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.WEAPON,
                                new EquipmentSlot[]{EquipmentSlot.MAINHAND}
                        )
                )
                .addEN()
                .addENDesc("Apply freezing effect to target. Higher levels have longer duration.")
                .addZH("冰刃")
                .addZHDesc("对敌人施加寒流效果。更高级的附魔延长效果时间")
                .register();
    }

    static class Entry<T extends Enchantment> extends EnchantmentBuilder<T>
    {
        public static <T extends Enchantment> Entry<T> of(String name, T enchantment)
        {
            return new Entry<>(name, enchantment);
        }

        private Entry(String name, T enchantment)
        {
            super(name, enchantment);
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
