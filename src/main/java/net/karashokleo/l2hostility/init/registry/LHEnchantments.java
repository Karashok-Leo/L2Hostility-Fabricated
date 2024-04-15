package net.karashokleo.l2hostility.init.registry;

import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.enchantment.HostilityEnchantment;
import net.karashokleo.l2hostility.content.enchantment.RemoveTraitEnchantment;
import net.karashokleo.l2hostility.content.enchantment.SingleLevelEnchantment;
import net.karashokleo.l2hostility.content.enchantment.VanishEnchantment;
import net.karashokleo.l2hostility.data.provider.EN_US_LangProvider;
import net.karashokleo.l2hostility.data.provider.ZH_CN_LangProvider;
import net.karashokleo.l2hostility.data.provider.TagEnchantmentProvider;
import net.karashokleo.l2hostility.util.StringUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;

public class LHEnchantments
{
    public static HostilityEnchantment INSULATOR;
    public static RemoveTraitEnchantment SPLIT_SUPPRESS;
    public static VanishEnchantment VANISH;
    public static SingleLevelEnchantment SHULKER_ARMOR;

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
    }

    static class Entry<T extends Enchantment>
    {
        String name;
        T enchantment;

        private Entry(String name, T enchantment)
        {
            this.name = name;
            this.enchantment = enchantment;
        }

        public static <T extends Enchantment> Entry<T> of(String name, T enchantment)
        {
            return new Entry<>(name, enchantment);
        }

        public T register()
        {
            return Registry.register(Registries.ENCHANTMENT, L2Hostility.id(name), enchantment);
        }

        public Entry<T> addEN()
        {
            return addEN(StringUtil.getNameById(name));
        }

        public Entry<T> addEN(String en)
        {
            EN_US_LangProvider.addEnchantment(enchantment, en);
            return this;
        }

        public Entry<T> addENDesc(String en)
        {
            EN_US_LangProvider.addEnchantmentDesc(enchantment, en);
            return this;
        }

        public Entry<T> addZH(String zh)
        {
            ZH_CN_LangProvider.addEnchantment(enchantment, zh);
            return this;
        }

        public Entry<T> addZHDesc(String zh)
        {
            ZH_CN_LangProvider.addEnchantmentDesc(enchantment, zh);
            return this;
        }

        public Entry<T> addTag(TagKey<Enchantment> key)
        {
            TagEnchantmentProvider.add(key, enchantment);
            return this;
        }
    }
}
