package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.leobrary.datagen.builder.PotionBuilder;
import karashokleo.leobrary.datagen.object.PotionEffectType;
import karashokleo.leobrary.datagen.object.PotionItemType;
import karashokleo.leobrary.datagen.object.PotionSet;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;

public class LHPotions
{
    public static PotionSet EMERALD;
    public static PotionSet FLAME;
    public static PotionSet ICE;
    public static PotionSet ARMOR_REDUCE;
    public static PotionSet STONE_CAGE;
    public static PotionSet CURSE;
    public static PotionSet CLEANSE;
    public static PotionSet LEVITATION;
    public static PotionSet RESISTANCE;

    public static void register()
    {
        EMERALD = new Entry("emerald_splash", LHEffects.EMERALD)
                .register(1200, 0, PotionEffectType.NORMAL)
                .register(2400, 0, PotionEffectType.LONG)
                .register(1200, 1, PotionEffectType.STRONG)
                .recipe(ComplementItems.EMERALD)
                .recipeLong(ComplementItems.FORCE_FIELD)
                .recipeStrong(ComplementItems.RESONANT_FEATHER)
                .addEN()
                .addZH("绿宝石水花")
                .addZH("虫箭-法皇之绿", PotionItemType.TIPPED_ARROW)
                .build();

        FLAME = new Entry("flame", LHEffects.FLAME)
                .register(600, 0, PotionEffectType.NORMAL)
                .register(1000, 0, PotionEffectType.LONG)
                .register(400, 1, PotionEffectType.STRONG)
                .recipe(ComplementItems.SOUL_FLAME)
                .recipeLong()
                .recipeStrong()
                .addEN("Soul Burning")
                .addZH("燃魂")
                .build();

        ICE = new Entry("frozen", LHEffects.ICE)
                .register(3600, 0, PotionEffectType.NORMAL)
                .register(9600, 0, PotionEffectType.LONG)
                .recipe(ComplementItems.HARD_ICE)
                .recipeLong()
                .addEN("Frost")
                .addZH("冰封")
                .build();

        ARMOR_REDUCE = new Entry("armor_reduce", LHEffects.ARMOR_REDUCE)
                .register(1200, 0, PotionEffectType.NORMAL)
                .register(3600, 0, PotionEffectType.LONG)
                .register(600, 1, PotionEffectType.STRONG)
                .recipe(Potions.WEAKNESS, Items.MAGMA_CREAM)
                .recipe(Potions.FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE)
                .recipeLong(Potions.LONG_WEAKNESS, Items.MAGMA_CREAM)
                .recipeLong(Potions.LONG_FIRE_RESISTANCE, Items.FERMENTED_SPIDER_EYE)
                .recipeLong()
                .recipeStrong()
                .addEN("Armor Corrosion")
                .addZH("破甲")
                .build();

        STONE_CAGE = new Entry("stone_cage", LHEffects.STONE_CAGE)
                .register(1200, 0, PotionEffectType.NORMAL)
                .register(3600, 0, PotionEffectType.LONG)
                .recipe(ComplementItems.BLACKSTONE_CORE)
                .recipeLong()
                .addEN("Incarceration")
                .addZH("禁锢")
                .build();

        CURSE = new Entry("curse", LHEffects.CURSE)
                .register(3600, 3, PotionEffectType.NORMAL)
                .register(9600, 3, PotionEffectType.LONG)
                .register(1800, 4, PotionEffectType.STRONG)
                .recipe(ComplementItems.CURSED_DROPLET)
                .recipeLong()
                .recipeStrong()
                .addEN("Cursed")
                .addZH("诅咒")
                .build();

        CLEANSE = new Entry("cleanse", LHEffects.CLEANSE)
                .register(3600, 0, PotionEffectType.NORMAL)
                .register(9600, 0, PotionEffectType.LONG)
                .recipe(ComplementItems.LIFE_ESSENCE)
                .recipeLong()
                .addEN("Cleansed")
                .addZH("净化")
                .build();

        LEVITATION = new Entry("levitation", StatusEffects.LEVITATION)
                .register(200, 0, PotionEffectType.NORMAL)
                .register(600, 0, PotionEffectType.LONG)
                .recipe(ComplementItems.CAPTURED_BULLET)
                .recipeLong()
                .addEN()
                .addZH("悬浮")
                .build();

        RESISTANCE = new Entry("resistance", StatusEffects.RESISTANCE)
                .register(600, 1, PotionEffectType.NORMAL)
                .register(1200, 1, PotionEffectType.LONG)
                .register(400, 2, PotionEffectType.STRONG)
                .recipe(ComplementItems.EXPLOSION_SHARD)
                .recipeLong()
                .recipeStrong()
                .addEN()
                .addZH("抗性")
                .build();
    }

    static class Entry extends PotionBuilder
    {
        public Entry(String name, StatusEffect content)
        {
            super(name, content);
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
