package karashokleo.l2hostility.init;

import karashokleo.leobrary.datagen.builder.PotionBuilder;
import karashokleo.leobrary.datagen.builder.PotionSet;
import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import org.jetbrains.annotations.Nullable;

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
                .register(1200, 0)
                .registerLong(2400, 0)
                .registerStrong(1200, 1)
                .recipe(ComplementItems.EMERALD)
                .recipeLong(ComplementItems.FORCE_FIELD)
                .recipeStrong(ComplementItems.RESONANT_FEATHER)
                .addEN()
                .addPotionZH("绿宝石水花")
                .addSplashZH("喷溅型绿宝石水花")
                .addLingeringZH("滞留型绿宝石水花")
                .addTippedArrowZH("虫箭-法皇之绿")
                .build();

        FLAME = new Entry("flame", LHEffects.FLAME)
                .register(600, 0)
                .registerLong(1000, 0)
                .registerStrong(400, 1)
                .recipe(ComplementItems.SOUL_FLAME)
                .recipeLong()
                .recipeStrong()
                .addEN("Soul Burning")
                .addZH("燃魂")
                .build();

        ICE = new Entry("frozen", LHEffects.ICE)
                .register(3600, 0)
                .registerLong(9600, 0)
                .recipe(ComplementItems.HARD_ICE)
                .recipeLong()
                .addEN("Frost")
                .addZH("冰封")
                .build();

        ARMOR_REDUCE = new Entry("armor_reduce", LHEffects.ARMOR_REDUCE)
                .register(1200, 0)
                .registerLong(3600, 0)
                .registerStrong(600, 1)
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
                .register(1200, 0)
                .registerLong(3600, 0)
                .recipe(ComplementItems.BLACKSTONE_CORE)
                .recipeLong()
                .addEN("Incarceration")
                .addZH("禁锢")
                .build();

        CURSE = new Entry("curse", LHEffects.CURSE)
                .register(3600, 0)
                .registerLong(9600, 0)
                .recipe(ComplementItems.CURSED_DROPLET)
                .recipeLong()
                .addEN("Cursed")
                .addZH("诅咒")
                .build();

        CLEANSE = new Entry("cleanse", LHEffects.CLEANSE)
                .register(3600, 0)
                .registerLong(9600, 0)
                .recipe(ComplementItems.LIFE_ESSENCE)
                .recipeLong()
                .addEN("Cleansed")
                .addZH("净化")
                .build();

        LEVITATION = new Entry("levitation", StatusEffects.LEVITATION)
                .register(200, 0)
                .registerLong(600, 0)
                .recipe(ComplementItems.CAPTURED_BULLET)
                .recipeLong()
                .addEN()
                .addZH("悬浮")
                .build();

        RESISTANCE = new Entry("resistance", StatusEffects.RESISTANCE)
                .register(600, 1)
                .registerLong(1200, 1)
                .registerStrong(400, 2)
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
        protected String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
