package net.karashokleo.l2hostility.init;

import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.enchantment.HostilityEnchantment;
import net.karashokleo.l2hostility.content.enchantment.RemoveTraitEnchantment;
import net.karashokleo.l2hostility.content.enchantment.SingleLevelEnchantment;
import net.karashokleo.l2hostility.content.enchantment.VanishEnchantment;
import net.karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

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
        public @Nullable LanguageGenerator getEnglishGenerator()
        {
            return LHData.EN_TEXTS;
        }

        @Override
        public @Nullable LanguageGenerator getChineseGenerator()
        {
            return LHData.ZH_TEXTS;
        }

        @Override
        public @Nullable TagGenerator<Enchantment> getTagGenerator()
        {
            return LHData.ENCHANTMENT_TAGS;
        }

        @Override
        protected String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
