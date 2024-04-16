package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.item.consumable.*;
import net.karashokleo.l2hostility.content.item.tool.Detector;
import net.karashokleo.l2hostility.content.item.tool.DetectorGlasses;
import net.karashokleo.l2hostility.content.item.tool.WitchWand;
import net.karashokleo.l2hostility.content.item.traits.SealedItem;
import net.karashokleo.l2hostility.content.item.trinket.curse.*;
import net.karashokleo.l2hostility.content.item.trinket.misc.*;
import net.karashokleo.l2hostility.content.item.trinket.ring.*;
import net.karashokleo.l2hostility.content.item.wand.AiConfigWand;
import net.karashokleo.l2hostility.content.item.wand.EquipmentWand;
import net.karashokleo.l2hostility.content.item.wand.TargetSelectWand;
import net.karashokleo.l2hostility.content.item.wand.TraitAdderWand;
import net.karashokleo.l2hostility.data.generate.EN_US_LangProvider;
import net.karashokleo.l2hostility.data.generate.TagItemProvider;
import net.karashokleo.l2hostility.data.generate.ModelProvider;
import net.karashokleo.l2hostility.data.generate.ZH_CN_LangProvider;
import net.karashokleo.l2hostility.util.StringUtil;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.NetherStarItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

public class LHItems
{
    public static final List<ItemStack> TAB_ITEMS = new ArrayList<>();

    public static HostilityOrb HOSTILITY_ORB;
    public static BottleOfCurse BOTTLE_CURSE;
    public static BottleOfSanity BOTTLE_SANITY;
    public static Item WITCH_DROPLET;
    public static EffectBoosterBottle BOOSTER_POTION;
    //    public static HostilityChargeItem WITCH_CHARGE, ETERNAL_WITCH_CHARGE;
    public static BookCopy BOOK_COPY;
    public static BookEverything BOOK_OMNISCIENCE;
    public static Detector DETECTOR;
    public static DetectorGlasses DETECTOR_GLASSES;
    public static WitchWand WITCH_WAND;
    public static Item CHAOS_INGOT;
    public static NetherStarItem HOSTILITY_ESSENCE;
    public static NetherStarItem MIRACLE_POWDER;
    public static NetherStarItem MIRACLE_INGOT;
    public static LootingCharm LOOT_1, LOOT_2, LOOT_3, LOOT_4;
    public static CurseOfEnvy CURSE_ENVY;
    public static CurseOfGluttony CURSE_GLUTTONY;
    public static CurseOfGreed CURSE_GREED;
    public static CurseOfLust CURSE_LUST;
    public static CurseOfPride CURSE_PRIDE;
    public static CurseOfSloth CURSE_SLOTH;
    public static CurseOfWrath CURSE_WRATH;
    public static RingOfOcean RING_OCEAN;
    public static RingOfLife RING_LIFE;
    public static RingOfDivinity RING_DIVINITY;
    public static RingOfReflection RING_REFLECTION;
    public static RingOfIncarceration RING_INCARCERATION;
    public static RingOfCorrosion RING_CORROSION;
    public static RingOfHealing RING_HEALING;
    public static FlamingThorn FLAMING_THORN;
    public static ImagineBreaker IMAGINE_BREAKER;
    public static PlatinumStar PLATINUM_STAR;
    public static InfinityGlove INFINITY_GLOVE;
    public static OddeyesGlasses ODDEYES_GLASSES;
    public static TripleStripCape TRIPLE_STRIP_CAPE;
    public static Abrahadabra ABRAHADABRA;
    public static GreedOfNidhoggur NIDHOGGUR;
    public static PocketOfRestoration RESTORATION;
    public static AbyssalThorn ABYSSAL_THORN;
    public static DivinityCross DIVINITY_CROSS;
    public static DivinityLight DIVINITY_LIGHT;
    public static TraitAdderWand TRAIT_ADDER_WAND;
    public static TargetSelectWand TARGET_SELECT_WAND;
    public static AiConfigWand AI_CONFIG_WAND;
    public static EquipmentWand EQUIPMENT_WAND;
    public static SealedItem SEAL;

    public static void register()
    {
        HOSTILITY_ORB = Entry.of(
                        "hostility_orb",
                        new HostilityOrb(
                                new FabricItemSettings()
                                        .maxCount(64)
                                        .rarity(Rarity.EPIC)
                        )
                )
                .addModel()
                .addEN()
                .addZH("恶意吸收宝珠")
                .register();

        BOTTLE_CURSE = Entry.of(
                        "bottle_of_curse",
                        new BottleOfCurse(
                                new FabricItemSettings()
                                        .maxCount(64)
                                        .rarity(Rarity.RARE)
                                        .recipeRemainder(Items.GLASS_BOTTLE)
                        )
                )
                .addModel()
                .addEN()
                .addZH("瓶装恶意")
                .register();

        BOTTLE_SANITY = Entry.of(
                        "bottle_of_sanity",
                        new BottleOfSanity(
                                new FabricItemSettings()
                                        .maxCount(64)
                                        .rarity(Rarity.RARE)
                                        .recipeRemainder(Items.GLASS_BOTTLE)
                        )
                )
                .addModel()
                .addEN()
                .addZH("恶意净化药水")
                .register();

        WITCH_DROPLET = Entry.of(
                        "witch_droplet",
                        new Item(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("女巫浓缩凝露")
                .register();

        BOOSTER_POTION = Entry.of(
                        "booster_potion",
                        new EffectBoosterBottle(
                                new FabricItemSettings()
                                        .maxCount(16)
                                        .rarity(Rarity.RARE)
                                        .recipeRemainder(Items.GLASS_BOTTLE)
                        )
                )
                .addModel()
                .addEN()
                .addZH("药水增幅药剂")
                .register();

        //嗜魔弹
//            WITCH_CHARGE = LeosHostility.REGISTRATE.item("witch_charge",
//                    p - new HostilityChargeItem(p, ChargeType.BOOST, () -
//                            LangData.TOOLTIP_WITCH_CHARGE.get(
//                                    LHConfig.COMMON.witchChargeMinDuration.get() / 20,
//                                    Math.round(100 * LHConfig.COMMON.drainDuration.get()),
//                                    LHConfig.COMMON.drainDurationMax.get() / 20
//                            ).withStyle(ChatFormatting.GRAY))
//            ).register();
//
        //永恒嗜魔弹
//            ETERNAL_WITCH_CHARGE = LeosHostility.REGISTRATE.item("eternal_witch_charge",
//                    p - new HostilityChargeItem(p, ChargeType.ETERNAL, () -
//                            LangData.TOOLTIP_WITCH_ETERNAL.get(
//                                    LHConfig.COMMON.witchChargeMinDuration.get() / 20
//                            ).withStyle(ChatFormatting.GRAY))
//            ).register();

        BOOK_COPY = Entry.of(
                        "book_of_reprint",
                        new BookCopy(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("附魔影本")
                .register();

        BOOK_OMNISCIENCE = Entry.of(
                        "book_of_omniscience",
                        new BookEverything(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("全知之书")
                .register();

        DETECTOR = Entry.of(
                        "hostility_detector",
                        new Detector(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel()
                .addEN()
                .addZH("恶意探测器")
                .register();

        DETECTOR_GLASSES = Entry.of(
                        "detector_glasses",
                        new DetectorGlasses(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel()
                .addEN()
                .addZH("侦测眼镜")
                .addTag(LHTags.FACE_SLOT)
                .register();

        WITCH_WAND = Entry.of(
                        "witch_wand",
                        new WitchWand(
                                new FabricItemSettings()
                                        .maxDamage(300)
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("混乱魔药法杖")
                .addTag(LHTags.CHAOS)
                .register();

        CHAOS_INGOT = Entry.of(
                        "chaos_ingot",
                        new Item(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("混沌锭")
                .addTag(LHTags.CHAOS)
                .register();

        HOSTILITY_ESSENCE = Entry.of(
                        "hostility_essence",
                        new NetherStarItem(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("恶意精华")
                .register();

        MIRACLE_POWDER = Entry.of(
                        "miracle_powder",
                        new NetherStarItem(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("奇迹粉末")
                .register();

        MIRACLE_INGOT = Entry.of(
                        "miracle_ingot",
                        new NetherStarItem(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("奇迹锭")
                .register();

        LOOT_1 = Entry.of(
                        "charm_of_looting_1",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Unpolished Looting Charm")
                .addZH("粗糙抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        LOOT_2 = Entry.of(
                        "charm_of_looting_2",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Magical Looting Charm")
                .addZH("魔能抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        LOOT_3 = Entry.of(
                        "charm_of_looting_3",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Chaotic Looting Charm")
                .addZH("混沌抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        LOOT_4 = Entry.of(
                        "charm_of_looting_4",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Miraculous Looting Charm")
                .addZH("奇迹抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_ENVY = Entry.of(
                        "curse_of_envy",
                        new CurseOfEnvy(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("嫉妒诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_GLUTTONY = Entry.of(
                        "curse_of_gluttony",
                        new CurseOfGluttony(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("暴食诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_GREED = Entry.of(
                        "curse_of_greed",
                        new CurseOfGreed(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("贪婪诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_LUST = Entry.of(
                        "curse_of_lust",
                        new CurseOfLust(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("色欲诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_PRIDE = Entry.of(
                        "curse_of_pride",
                        new CurseOfPride(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("傲慢诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT)
                .register();

        CURSE_SLOTH = Entry.of(
                        "curse_of_sloth",
                        new CurseOfSloth(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("怠惰诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_WRATH = Entry.of(
                        "curse_of_wrath",
                        new CurseOfWrath(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("暴怒诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT)
                .register();

        RING_OCEAN = Entry.of(
                        "ring_of_ocean",
                        new RingOfOcean(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("深海加护之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_LIFE = Entry.of(
                        "ring_of_life",
                        new RingOfLife(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("屹立不倒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_DIVINITY = Entry.of(
                        "ring_of_divinity",
                        new RingOfDivinity(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("圣光守护之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_REFLECTION = Entry.of(
                        "ring_of_reflection",
                        new RingOfReflection(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("诅咒扭曲之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_INCARCERATION = Entry.of(
                        "ring_of_incarceration",
                        new RingOfIncarceration(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("禁锢诅咒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_CORROSION = Entry.of(
                        "ring_of_corrosion",
                        new RingOfCorrosion(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("丑恶诅咒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_HEALING = Entry.of(
                        "ring_of_healing",
                        new RingOfHealing(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("再生之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        FLAMING_THORN = Entry.of(
                        "flaming_thorn",
                        new FlamingThorn(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("魔焰荆棘")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT)
                .register();

        IMAGINE_BREAKER = Entry.of(
                        "imagine_breaker",
                        new ImagineBreaker(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("破咒遗骨")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.NO_SEAL)
                .register();

        PLATINUM_STAR = Entry.of(
                        "platinum_star",
                        new PlatinumStar(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("破风拳套")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.CHARM_SLOT)
                .register();

        INFINITY_GLOVE = Entry.of(
                        "infinity_glove",
                        new InfinityGlove(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("宝石手套")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT)
                .register();

        ABYSSAL_THORN = Entry.of(
                        "abyssal_thorn",
                        new AbyssalThorn(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("深渊王座")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT)
                .register();

        DIVINITY_CROSS = Entry.of(
                        "divinity_cross",
                        new DivinityCross(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("神圣十字")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.CHARM_SLOT)
                .register();

        DIVINITY_LIGHT = Entry.of(
                        "divinity_light",
                        new DivinityLight(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("神圣之光")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.CHARM_SLOT)
                .register();

        ODDEYES_GLASSES = Entry.of(
                        "oddeyes_glasses",
                        new OddeyesGlasses(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("异色眼镜")
                .addTag(LHTags.CHAOS, LHTags.FACE_SLOT)
                .register();

        TRIPLE_STRIP_CAPE = Entry.of(
                        "triple_strip_cape",
                        new TripleStripCape(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("三色披风")
                .addTag(LHTags.CHAOS, LHTags.BACK_SLOT)
                .register();

        ABRAHADABRA = Entry.of(
                        "abrahadabra",
                        new Abrahadabra(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("扭曲之魂")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        NIDHOGGUR = Entry.of(
                        "greed_of_nidhoggur",
                        new GreedOfNidhoggur(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("尼德霍格之欲")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        RESTORATION = Entry.of(
                        "pocket_of_restoration",
                        new PocketOfRestoration(
                                new FabricItemSettings(),
                                128
                        )
                )
                .addModel()
                .addEN()
                .addZH("解咒口袋")
                .addTag(LHTags.CHARM_SLOT, LHTags.NO_SEAL)
                .register();

        TRAIT_ADDER_WAND = Entry.of(
                        "trait_adder_wand",
                        new TraitAdderWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("词条配置手杖")
                .register();

        TARGET_SELECT_WAND = Entry.of(
                        "target_select_wand",
                        new TargetSelectWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("攻击目标控制手杖")
                .register();

        EQUIPMENT_WAND = Entry.of(
                        "equipment_wand",
                        new EquipmentWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("装备配置手杖")
                .register();

        AI_CONFIG_WAND = Entry.of(
                        "ai_config_wand",
                        new AiConfigWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("AI控制手杖")
                .register();

        SEAL = Entry.of(
                        "sealed_item",
                        new SealedItem(
                                new FabricItemSettings()
                                        .maxCount(1)
                                        .fireproof()
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("被封印的物品")
                .addTag(LHTags.NO_SEAL)
                .removeTab()
                .register();
    }

    static class Entry<T extends Item>
    {
        String name;
        T item;
        boolean addToTab = true;

        private Entry(String name, T item)
        {
            this.name = name;
            this.item = item;
        }

        public static <T extends Item> Entry<T> of(String name, T item)
        {
            return new Entry<>(name, item);
        }

        public T register()
        {
            if (addToTab) TAB_ITEMS.add(item.getDefaultStack());
            return Registry.register(Registries.ITEM, L2Hostility.id(name), item);
        }

        public Entry<T> removeTab()
        {
            addToTab = false;
            return this;
        }

        public Entry<T> addModel()
        {
            ModelProvider.addItem(item);
            return this;
        }

        public Entry<T> addModel(Model model)
        {
            ModelProvider.addItem(item, model);
            return this;
        }

        public Entry<T> addEN()
        {
            return addEN(StringUtil.getNameById(name));
        }

        public Entry<T> addEN(String en)
        {
            EN_US_LangProvider.addItem(item, en);
            return this;
        }

        public Entry<T> addZH(String zh)
        {
            ZH_CN_LangProvider.addItem(item, zh);
            return this;
        }

        public Entry<T> addTag(TagKey<Item> key)
        {
            TagItemProvider.add(key, item);
            return this;
        }

        @SafeVarargs
        public final Entry<T> addTag(TagKey<Item>... keys)
        {
            for (TagKey<Item> key : keys)
                TagItemProvider.add(key, item);
            return this;
        }
    }
}
