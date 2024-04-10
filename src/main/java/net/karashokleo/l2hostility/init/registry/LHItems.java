package net.karashokleo.l2hostility.init.registry;

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
import net.karashokleo.l2hostility.data.provider.EN_US_LangProvider;
import net.karashokleo.l2hostility.data.provider.TagItemProvider;
import net.karashokleo.l2hostility.data.provider.ModelProvider;
import net.karashokleo.l2hostility.data.provider.ZH_CN_LangProvider;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.karashokleo.l2hostility.util.StringUtil;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.NetherStarItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Rarity;

public class LHItems
{
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
        HOSTILITY_ORB = ItemEntry.of(
                        "hostility_orb",
                        new HostilityOrb(
                                new FabricItemSettings()
                                        .maxCount(64)
                                        .rarity(Rarity.EPIC)
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        BOTTLE_CURSE = ItemEntry.of(
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
                .addZH("")
                .register();

        BOTTLE_SANITY = ItemEntry.of(
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
                .addZH("")
                .register();

        WITCH_DROPLET = ItemEntry.of(
                        "witch_droplet",
                        new Item(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        BOOSTER_POTION = ItemEntry.of(
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
                .addZH("")
                .register();

//            WITCH_CHARGE = LeosHostility.REGISTRATE.item("witch_charge",
//                    p - new HostilityChargeItem(p, ChargeType.BOOST, () -
//                            LangData.TOOLTIP_WITCH_CHARGE.get(
//                                    LHConfig.COMMON.witchChargeMinDuration.get() / 20,
//                                    Math.round(100 * LHConfig.COMMON.drainDuration.get()),
//                                    LHConfig.COMMON.drainDurationMax.get() / 20
//                            ).withStyle(ChatFormatting.GRAY))
//            ).register();
//
//            ETERNAL_WITCH_CHARGE = LeosHostility.REGISTRATE.item("eternal_witch_charge",
//                    p - new HostilityChargeItem(p, ChargeType.ETERNAL, () -
//                            LangData.TOOLTIP_WITCH_ETERNAL.get(
//                                    LHConfig.COMMON.witchChargeMinDuration.get() / 20
//                            ).withStyle(ChatFormatting.GRAY))
//            ).register();

        BOOK_COPY = ItemEntry.of(
                        "book_of_reprint",
                        new BookCopy(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        BOOK_OMNISCIENCE = ItemEntry.of(
                        "book_of_omniscience",
                        new BookEverything(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        DETECTOR = ItemEntry.of(
                        "hostility_detector",
                        new Detector(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        DETECTOR_GLASSES = ItemEntry.of(
                        "detector_glasses",
                        new DetectorGlasses(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.FACE_SLOT)
                .register();

        WITCH_WAND = ItemEntry.of(
                        "detector_glasses",
                        new WitchWand(
                                new FabricItemSettings()
                                        .maxDamage(300)
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS)
                .register();

        CHAOS_INGOT = ItemEntry.of(
                        "chaos_ingot",
                        new Item(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS)
                .register();

        HOSTILITY_ESSENCE = ItemEntry.of(
                        "hostility_essence",
                        new NetherStarItem(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        MIRACLE_POWDER = ItemEntry.of(
                        "miracle_powder",
                        new NetherStarItem(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        MIRACLE_INGOT = ItemEntry.of(
                        "miracle_ingot",
                        new NetherStarItem(
                                new FabricItemSettings()
                                        .rarity(Rarity.EPIC)
                                        .fireproof()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .register();

        LOOT_1 = ItemEntry.of(
                        "charm_of_looting_1",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Unpolished Looting Charm")
                .addZH("")
                .addTag(LHTags.NO_SEAL)
                .register();

        LOOT_2 = ItemEntry.of(
                        "charm_of_looting_2",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Magical Looting Charm")
                .addZH("")
                .addTag(LHTags.NO_SEAL)
                .register();

        LOOT_3 = ItemEntry.of(
                        "charm_of_looting_3",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Chaotic Looting Charm")
                .addZH("")
                .addTag(LHTags.NO_SEAL)
                .register();

        LOOT_4 = ItemEntry.of(
                        "charm_of_looting_4",
                        new LootingCharm(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN("Miraculous Looting Charm")
                .addZH("")
                .addTag(LHTags.NO_SEAL)
                .register();

        CURSE_ENVY = ItemEntry.of(
                        "curse_of_envy",
                        new CurseOfEnvy(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_GLUTTONY = ItemEntry.of(
                        "curse_of_gluttony",
                        new CurseOfGluttony(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_GREED = ItemEntry.of(
                        "curse_of_greed",
                        new CurseOfGreed(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_LUST = ItemEntry.of(
                        "curse_of_lust",
                        new CurseOfLust(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_PRIDE = ItemEntry.of(
                        "curse_of_pride",
                        new CurseOfPride(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT)
                .register();

        CURSE_SLOTH = ItemEntry.of(
                        "curse_of_sloth",
                        new CurseOfSloth(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_WRATH = ItemEntry.of(
                        "curse_of_wrath",
                        new CurseOfWrath(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT)
                .register();

        RING_OCEAN = ItemEntry.of(
                        "ring_of_ocean",
                        new RingOfOcean(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_LIFE = ItemEntry.of(
                        "ring_of_life",
                        new RingOfLife(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_DIVINITY = ItemEntry.of(
                        "ring_of_divinity",
                        new RingOfDivinity(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_REFLECTION = ItemEntry.of(
                        "ring_of_reflection",
                        new RingOfReflection(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_INCARCERATION = ItemEntry.of(
                        "ring_of_incarceration",
                        new RingOfIncarceration(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_CORROSION = ItemEntry.of(
                        "ring_of_corrosion",
                        new RingOfCorrosion(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        RING_HEALING = ItemEntry.of(
                        "ring_of_healing",
                        new RingOfHealing(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT)
                .register();

        FLAMING_THORN = ItemEntry.of(
                        "flaming_thorn",
                        new FlamingThorn(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT)
                .register();

        IMAGINE_BREAKER = ItemEntry.of(
                        "imagine_breaker",
                        new ImagineBreaker(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.NO_SEAL)
                .register();

        PLATINUM_STAR = ItemEntry.of(
                        "platinum_star",
                        new PlatinumStar(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.CHARM_SLOT)
                .register();

        INFINITY_GLOVE = ItemEntry.of(
                        "infinity_glove",
                        new InfinityGlove(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT)
                .register();

        ABYSSAL_THORN = ItemEntry.of(
                        "abyssal_thorn",
                        new AbyssalThorn(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT)
                .register();

        DIVINITY_CROSS = ItemEntry.of(
                        "divinity_cross",
                        new DivinityCross(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.CHARM_SLOT)
                .register();

        DIVINITY_LIGHT = ItemEntry.of(
                        "divinity_light",
                        new DivinityLight(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.CHARM_SLOT)
                .register();

        ODDEYES_GLASSES = ItemEntry.of(
                        "oddeyes_glasses",
                        new OddeyesGlasses(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.FACE_SLOT)
                .register();

        TRIPLE_STRIP_CAPE = ItemEntry.of(
                        "triple_strip_cape",
                        new TripleStripCape(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.BACK_SLOT)
                .register();

        ABRAHADABRA = ItemEntry.of(
                        "abrahadabra",
                        new Abrahadabra(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        RESTORATION = ItemEntry.of(
                        "pocket_of_restoration",
                        new PocketOfRestoration(
                                new FabricItemSettings(),
                                128
                        )
                )
                .addModel()
                .addEN()
                .addZH("")
                .addTag(LHTags.CHARM_SLOT, LHTags.NO_SEAL)
                .register();

        TRAIT_ADDER_WAND = ItemEntry.of(
                        "trait_adder_wand",
                        new TraitAdderWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("")
                .register();

        TARGET_SELECT_WAND = ItemEntry.of(
                        "target_select_wand",
                        new TargetSelectWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("")
                .register();

        EQUIPMENT_WAND = ItemEntry.of(
                        "equipment_wand",
                        new EquipmentWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("")
                .register();

        AI_CONFIG_WAND = ItemEntry.of(
                        "ai_config_wand",
                        new AiConfigWand(
                                new FabricItemSettings()
                                        .maxCount(1)
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("")
                .register();

        SEAL = ItemEntry.of(
                        "sealed_item",
                        new SealedItem(
                                new FabricItemSettings()
                                        .maxCount(1)
                                        .fireproof()
                        )
                )
                .addModel(Models.HANDHELD)
                .addEN()
                .addZH("")
                .addTag(LHTags.NO_SEAL)
                .register();
    }

    static class ItemEntry<T extends Item>
    {
        String name;
        T item;

        private ItemEntry(String name, T item)
        {
            this.name = name;
            this.item = item;
        }

        public static <T extends Item> ItemEntry<T> of(String name, T item)
        {
            return new ItemEntry<>(name, item);
        }

        public T register()
        {
            return Registry.register(Registries.ITEM, L2Hostility.id(name), item);
        }

        public ItemEntry<T> addModel()
        {
            ModelProvider.addItem(item);
            return this;
        }

        public ItemEntry<T> addModel(Model model)
        {
            ModelProvider.addItem(item, model);
            return this;
        }

        public ItemEntry<T> addEN()
        {
            return addEN(StringUtil.getNameById(name));
        }

        public ItemEntry<T> addEN(String en)
        {
            EN_US_LangProvider.addItem(item, en);
            return this;
        }

        public ItemEntry<T> addZH(String zh)
        {
            ZH_CN_LangProvider.addItem(item, zh);
            return this;
        }

        public ItemEntry<T> addTag(TagKey<Item> key)
        {
            TagItemProvider.add(key, item);
            return this;
        }

        @SafeVarargs
        public final ItemEntry<T> addTag(TagKey<Item>... keys)
        {
            for (TagKey<Item> key : keys)
                TagItemProvider.add(key, item);
            return this;
        }
    }
}
