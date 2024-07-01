package karashokleo.l2hostility.content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import karashokleo.l2hostility.content.item.trinket.curse.*;
import karashokleo.l2hostility.content.item.trinket.misc.*;
import karashokleo.l2hostility.content.item.trinket.ring.*;
import karashokleo.l2hostility.init.LHItems;
import karashokleo.l2hostility.init.LHTags;

public class TrinketItems
{
    public static LootingCharm LOOT_1;
    public static LootingCharm LOOT_2;
    public static LootingCharm LOOT_3;
    public static LootingCharm LOOT_4;
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
    public static AbyssalThorn ABYSSAL_THORN;
    public static DivinityCross DIVINITY_CROSS;
    public static DivinityLight DIVINITY_LIGHT;
    public static OddeyesGlasses ODDEYES_GLASSES;
    public static TripleStripCape TRIPLE_STRIP_CAPE;
    public static Abrahadabra ABRAHADABRA;
    public static GreedOfNidhoggur NIDHOGGUR;
    public static PocketOfRestoration RESTORATION;

    public static void register()
    {

        LOOT_1 = LHItems.Entry.of(
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

        LOOT_2 = LHItems.Entry.of(
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

        LOOT_3 = LHItems.Entry.of(
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

        LOOT_4 = LHItems.Entry.of(
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

        CURSE_ENVY = LHItems.Entry.of(
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

        CURSE_GLUTTONY = LHItems.Entry.of(
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

        CURSE_GREED = LHItems.Entry.of(
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

        CURSE_LUST = LHItems.Entry.of(
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

        CURSE_PRIDE = LHItems.Entry.of(
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

        CURSE_SLOTH = LHItems.Entry.of(
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

        CURSE_WRATH = LHItems.Entry.of(
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

        RING_OCEAN = LHItems.Entry.of(
                        "ring_of_ocean",
                        new RingOfOcean(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("深海加护之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_LIFE = LHItems.Entry.of(
                        "ring_of_life",
                        new RingOfLife(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("屹立不倒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_DIVINITY = LHItems.Entry.of(
                        "ring_of_divinity",
                        new RingOfDivinity(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("圣光守护之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_REFLECTION = LHItems.Entry.of(
                        "ring_of_reflection",
                        new RingOfReflection(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("诅咒扭曲之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_INCARCERATION = LHItems.Entry.of(
                        "ring_of_incarceration",
                        new RingOfIncarceration(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("禁锢诅咒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_CORROSION = LHItems.Entry.of(
                        "ring_of_corrosion",
                        new RingOfCorrosion(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("丑恶诅咒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_HEALING = LHItems.Entry.of(
                        "ring_of_healing",
                        new RingOfHealing(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("再生之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        FLAMING_THORN = LHItems.Entry.of(
                        "flaming_thorn",
                        new FlamingThorn(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("魔焰荆棘")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT)
                .register();

        IMAGINE_BREAKER = LHItems.Entry.of(
                        "imagine_breaker",
                        new ImagineBreaker(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("破咒遗骨")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT, LHTags.NO_SEAL)
                .register();

        PLATINUM_STAR = LHItems.Entry.of(
                        "platinum_star",
                        new PlatinumStar(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("破风拳套")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT, LHTags.CHARM_SLOT)
                .register();

        INFINITY_GLOVE = LHItems.Entry.of(
                        "infinity_glove",
                        new InfinityGlove(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("宝石手套")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT)
                .register();

        ABYSSAL_THORN = LHItems.Entry.of(
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

        DIVINITY_CROSS = LHItems.Entry.of(
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

        DIVINITY_LIGHT = LHItems.Entry.of(
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

        ODDEYES_GLASSES = LHItems.Entry.of(
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

        TRIPLE_STRIP_CAPE = LHItems.Entry.of(
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

        ABRAHADABRA = LHItems.Entry.of(
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

        NIDHOGGUR = LHItems.Entry.of(
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

        RESTORATION = LHItems.Entry.of(
                        "pocket_of_restoration",
                        new PocketOfRestoration(
                                new FabricItemSettings(),
                                128
                        )
                )
                .addEN()
                .addZH("解咒口袋")
                .addTag(LHTags.CHARM_SLOT, LHTags.NO_SEAL)
                .register();
    }
}
