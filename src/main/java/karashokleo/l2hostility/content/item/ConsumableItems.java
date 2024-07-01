package karashokleo.l2hostility.content.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import karashokleo.l2hostility.content.item.consumable.*;
import karashokleo.l2hostility.init.LHItems;
import net.minecraft.item.Items;
import net.minecraft.util.Rarity;

public class ConsumableItems
{
    public static BookCopy BOOK_COPY;
    public static BookEverything BOOK_OMNISCIENCE;
    public static BottleOfCurse BOTTLE_CURSE;
    public static BottleOfSanity BOTTLE_SANITY;
    public static EffectBoosterBottle BOOSTER_POTION;
    public static HostilityOrb HOSTILITY_ORB;
    //    public static HostilityChargeItem WITCH_CHARGE, ETERNAL_WITCH_CHARGE;

    public static void register()
    {
        BOOK_COPY = LHItems.Entry.of(
                        "book_of_reprint",
                        new BookCopy(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("附魔影本")
                .register();

        BOOK_OMNISCIENCE = LHItems.Entry.of(
                        "book_of_omniscience",
                        new BookEverything(
                                new FabricItemSettings()
                        )
                )
                .addModel()
                .addEN()
                .addZH("全知之书")
                .register();

        BOTTLE_CURSE = LHItems.Entry.of(
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

        BOTTLE_SANITY = LHItems.Entry.of(
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

        BOOSTER_POTION = LHItems.Entry.of(
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
//            )//                .register();
//
        //永恒嗜魔弹
//            ETERNAL_WITCH_CHARGE = LeosHostility.REGISTRATE.item("eternal_witch_charge",
//                    p - new HostilityChargeItem(p, ChargeType.ETERNAL, () -
//                            LangData.TOOLTIP_WITCH_ETERNAL.get(
//                                    LHConfig.COMMON.witchChargeMinDuration.get() / 20
//                            ).withStyle(ChatFormatting.GRAY))
//            )//                .register();

        HOSTILITY_ORB = LHItems.Entry.of(
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
    }
}
