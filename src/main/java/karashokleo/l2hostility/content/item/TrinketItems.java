package karashokleo.l2hostility.content.item;

import karashokleo.l2hostility.content.item.trinket.curse.*;
import karashokleo.l2hostility.content.item.trinket.misc.*;
import karashokleo.l2hostility.content.item.trinket.ring.*;
import karashokleo.l2hostility.init.LHItems;
import karashokleo.l2hostility.init.LHTags;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;

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
    public static AbyssalThrone ABYSSAL_THRONE;
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
                        new LootingCharm()
                )
                .addModel()
                .addEN("Unpolished Looting Charm")
                .addZH("粗糙抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        LOOT_2 = LHItems.Entry.of(
                        "charm_of_looting_2",
                        new LootingCharm()
                )
                .addModel()
                .addEN("Magical Looting Charm")
                .addZH("魔能抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        LOOT_3 = LHItems.Entry.of(
                        "charm_of_looting_3",
                        new LootingCharm()
                )
                .addModel()
                .addEN("Chaotic Looting Charm")
                .addZH("混沌抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        LOOT_4 = LHItems.Entry.of(
                        "charm_of_looting_4",
                        new LootingCharm()
                )
                .addModel()
                .addEN("Miraculous Looting Charm")
                .addZH("奇迹抢夺宝珠")
                .addTag(LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_ENVY = LHItems.Entry.of(
                        "curse_of_envy",
                        new CurseOfEnvy()
                )
                .addModel()
                .addEN()
                .addZH("嫉妒诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_GLUTTONY = LHItems.Entry.of(
                        "curse_of_gluttony",
                        new CurseOfGluttony()
                )
                .addModel()
                .addEN()
                .addZH("暴食诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_GREED = LHItems.Entry.of(
                        "curse_of_greed",
                        new CurseOfGreed()
                )
                .addModel()
                .addEN()
                .addZH("贪婪诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_LUST = LHItems.Entry.of(
                        "curse_of_lust",
                        new CurseOfLust()
                )
                .addModel()
                .addEN()
                .addZH("色欲诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_PRIDE = LHItems.Entry.of(
                        "curse_of_pride",
                        new CurseOfPride()
                )
                .addModel()
                .addEN()
                .addZH("傲慢诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT)
                .register();

        CURSE_SLOTH = LHItems.Entry.of(
                        "curse_of_sloth",
                        new CurseOfSloth()
                )
                .addModel()
                .addEN()
                .addZH("怠惰诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        CURSE_WRATH = LHItems.Entry.of(
                        "curse_of_wrath",
                        new CurseOfWrath()
                )
                .addModel()
                .addEN()
                .addZH("暴怒诅咒")
                .addTag(LHTags.CHAOS, LHTags.CHARM_SLOT, LHTags.CURSE_SLOT)
                .register();

        RING_OCEAN = LHItems.Entry.of(
                        "ring_of_ocean",
                        new RingOfOcean()
                )
                .addModel()
                .addEN()
                .addZH("深海加护之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_LIFE = LHItems.Entry.of(
                        "ring_of_life",
                        new RingOfLife()
                )
                .addModel()
                .addEN()
                .addZH("屹立不倒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_DIVINITY = LHItems.Entry.of(
                        "ring_of_divinity",
                        new RingOfDivinity()
                )
                .addModel()
                .addEN()
                .addZH("圣光守护之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_REFLECTION = LHItems.Entry.of(
                        "ring_of_reflection",
                        new RingOfReflection()
                )
                .addModel()
                .addEN()
                .addZH("诅咒扭曲之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_INCARCERATION = LHItems.Entry.of(
                        "ring_of_incarceration",
                        new RingOfIncarceration()
                )
                .addModel()
                .addEN()
                .addZH("禁锢诅咒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_CORROSION = LHItems.Entry.of(
                        "ring_of_corrosion",
                        new RingOfCorrosion()
                )
                .addModel()
                .addEN()
                .addZH("丑恶诅咒之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        RING_HEALING = LHItems.Entry.of(
                        "ring_of_healing",
                        new RingOfHealing()
                )
                .addModel()
                .addEN()
                .addZH("再生之戒")
                .addTag(LHTags.CHAOS, LHTags.RING_SLOT, LHTags.OFF_RING_SLOT)
                .register();

        FLAMING_THORN = LHItems.Entry.of(
                        "flaming_thorn",
                        new FlamingThorn()
                )
                .addModel()
                .addEN()
                .addZH("魔焰荆棘")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT)
                .register();

        IMAGINE_BREAKER = LHItems.Entry.of(
                        "imagine_breaker",
                        new ImagineBreaker()
                )
                .addModel()
                .addEN()
                .addZH("破咒遗骨")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT, LHTags.NO_SEAL)
                .register();

        PLATINUM_STAR = LHItems.Entry.of(
                        "platinum_star",
                        new PlatinumStar()
                )
                .addModel()
                .addEN()
                .addZH("破风拳套")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT, LHTags.CHARM_SLOT)
                .register();

        INFINITY_GLOVE = LHItems.Entry.of(
                        "infinity_glove",
                        new InfinityGlove()
                )
                .addModel()
                .addEN()
                .addZH("宝石手套")
                .addTag(LHTags.CHAOS, LHTags.HAND_SLOT, LHTags.OFF_HAND_SLOT)
                .register();

        ABYSSAL_THRONE = LHItems.Entry.of(
                        "abyssal_throne",
                        new AbyssalThrone()
                )
                .addModel()
                .addEN()
                .addZH("深渊王座")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT)
                .register();

        DIVINITY_CROSS = LHItems.Entry.of(
                        "divinity_cross",
                        new DivinityCross()
                )
                .addModel()
                .addEN()
                .addZH("神圣十字")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.CHARM_SLOT)
                .register();

        DIVINITY_LIGHT = LHItems.Entry.of(
                        "divinity_light",
                        new DivinityLight()
                )
                .addModel()
                .addEN()
                .addZH("神圣之光")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.CHARM_SLOT)
                .register();

        ODDEYES_GLASSES = LHItems.Entry.of(
                        "oddeyes_glasses",
                        new OddeyesGlasses()
                )
                .addModel()
                .addEN()
                .addZH("异色眼镜")
                .addTag(LHTags.CHAOS, LHTags.FACE_SLOT)
                .register();

        TRIPLE_STRIP_CAPE = LHItems.Entry.of(
                        "triple_strip_cape",
                        new TripleStripCape()
                )
                .addModel()
                .addEN()
                .addZH("三色披风")
                .addTag(LHTags.CHAOS, LHTags.BACK_SLOT)
                .register();

        ABRAHADABRA = LHItems.Entry.of(
                        "abrahadabra",
                        new Abrahadabra()
                )
                .addModel()
                .addEN()
                .addZH("扭曲之魂")
                .addTag(LHTags.CHAOS, LHTags.CURSE_SLOT, LHTags.NO_SEAL)
                .register();

        NIDHOGGUR = LHItems.Entry.of(
                        "greed_of_nidhoggur",
                        new GreedOfNidhoggur()
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
