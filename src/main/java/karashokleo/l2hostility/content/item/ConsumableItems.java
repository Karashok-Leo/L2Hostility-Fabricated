package karashokleo.l2hostility.content.item;

import karashokleo.l2hostility.content.item.consumable.*;
import karashokleo.l2hostility.content.item.consumable.charge.*;
import karashokleo.l2hostility.init.LHItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
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
    public static SoulFireChargeItem SOUL_FIRE_CHARGE;
    public static StrongFireChargeItem STRONG_FIRE_CHARGE;
    public static BlackFireChargeItem BLACK_FIRE_CHARGE;
    public static WitchChargeItem WITCH_CHARGE;
    public static EternalWitchChargeItem ETERNAL_WITCH_CHARGE;

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

        SOUL_FIRE_CHARGE = LHItems.Entry.of(
                "soul_fire_charge",
                new SoulFireChargeItem(new FabricItemSettings())
            )
            .addModel()
            .addEN()
            .addZH("魂炎弹")
            .register();
        STRONG_FIRE_CHARGE = LHItems.Entry.of(
                "strong_fire_charge",
                new StrongFireChargeItem(new FabricItemSettings())
            )
            .addModel()
            .addEN()
            .addZH("爆炎弹")
            .register();
        BLACK_FIRE_CHARGE = LHItems.Entry.of(
                "black_fire_charge",
                new BlackFireChargeItem(new FabricItemSettings())
            )
            .addModel()
            .addEN()
            .addZH("黑炎弹")
            .register();
        WITCH_CHARGE = LHItems.Entry.of(
                "witch_charge",
                new WitchChargeItem(new FabricItemSettings())
            )
            .addModel()
            .addEN()
            .addZH("嗜魔弹")
            .register();
        ETERNAL_WITCH_CHARGE = LHItems.Entry.of(
                "eternal_witch_charge",
                new EternalWitchChargeItem(new FabricItemSettings())
            )
            .addModel()
            .addEN()
            .addZH("永恒嗜魔弹")
            .register();
    }
}
