package net.karashokleo.l2hostility.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.karashokleo.l2hostility.init.LHTraits;

import java.util.Map;
import java.util.stream.Collectors;

@Config(name = "common")
public class CommonConfig implements ConfigData
{
    @ConfigEntry.Gui.CollapsibleObject
    public Difficulty difficulty = new Difficulty();
    @ConfigEntry.Gui.CollapsibleObject
    public Scaling scaling = new Scaling();
    @ConfigEntry.Gui.CollapsibleObject
    public OrbAndSpawner orbAndSpawner = new OrbAndSpawner();
    @ConfigEntry.Gui.CollapsibleObject
    public Items items = new Items();
    @ConfigEntry.Gui.CollapsibleObject
    public Traits traits = new Traits();
    @ConfigEntry.Gui.CollapsibleObject
    public final Map<String, Boolean> map = LHTraits.TRAIT.stream().collect(
            Collectors.toMap(
                    mobTrait -> mobTrait.getNonNullId().getPath(),
                    mobTrait -> true
            )
    );
    @ConfigEntry.Gui.CollapsibleObject
    public final Map<String, Integer> range = Map.of(
            "gravity", 10,
            "moonwalk", 10,
            "arena", 24
    );

    public static class Difficulty
    {
        public final int killsPerLevel = 30;
        public final int maxPlayerLevel = 2000;
        public final int newPlayerProtectRange = 48;
        public final double playerDeathDecay = 0.8;
        public final boolean keepInventoryRuleKeepDifficulty = false;
        public final boolean deathDecayDimension = true;
        public final boolean deathDecayTraitCap = true;
    }

    public static class Scaling
    {
        public final boolean enableEntitySpecificDatapack = true;
        public final double healthFactor = 0.03;
        public final boolean exponentialHealth = false;
        public final double damageFactor = 0.02;
        public final boolean exponentialDamage = false;
        public final double expDropFactor = 0.05;
        public final int armorFactor = 10;
        public final double enchantmentFactor = 0;
        public final int dimensionFactor = 10;
        public final double distanceFactor = 0.003;
        public final double globalApplyChance = 1;
        public final double globalTraitChance = 1;
        public final double globalTraitSuppression = 0.1;
        public final boolean allowLegendary = true;
        public final boolean allowSectionDifficulty = true;
        public final boolean allowBypassMinimum = true;
        public final boolean allowExtraEnchantments = true;
        public final int defaultLevelBase = 20;
        public final double defaultLevelVar = 16;
        public final double defaultLevelScale = 1.5;
        public final double initialTraitChanceSlope = 0.01;
        public final double splitDropRateFactor = 0.25;
        public final boolean allowNoAI = false;
        public final boolean allowPlayerAllies = false;
        public final boolean allowTraitOnOwnable = false;
    }

    public static class OrbAndSpawner
    {
        public final boolean allowHostilityOrb = true;
        public final int orbRadius = 2;
        public final boolean allowHostilitySpawner = true;
        public final int hostilitySpawnCount = 16;
        public final int hostilitySpawnLevelFactor = 2;
    }

    public static class Items
    {
        public final boolean disableHostilityLootCurioRequirement = false;
        public final int bottleOfCurseLevel = 50;
        public final int witchChargeMinDuration = 200;
        public final double ringOfLifeMaxDamage = 0.9;
        public final int flameThornTime = 100;
        public final int ringOfReflectionRadius = 16;
        public final int witchWandFactor = 4;
        public final double ringOfCorrosionFactor = 0.2;
        public final double ringOfCorrosionPenalty = 0.1;
        public final double ringOfHealingRate = 0.05;

        @ConfigEntry.Gui.CollapsibleObject
        public final Curse curse = new Curse();

        public static class Curse
        {
            public final int envyExtraLevel = 50;
            public final int greedExtraLevel = 50;
            public final int lustExtraLevel = 50;
            public final int wrathExtraLevel = 50;
            public final double greedDropFactor = 2;
            public final double envyDropRate = 0.02;
            public final double gluttonyBottleDropRate = 0.02;
            public final double wrathDamageBonus = 0.05;
            public final double prideDamageBonus = 0.02;
            public final double prideHealthBonus = 0.02;
            public final double prideTraitFactor = 0.5;
        }

        public final int abrahadabraExtraLevel = 100;
        public final int nidhoggurExtraLevel = 100;
        public final double nidhoggurDropFactor = 0.01;
        public final double insulatorFactor = 0.8;
    }

    public static class Traits
    {
        public final double tankHealth = 0.5;
        public final double tankArmor = 10;
        public final double tankTough = 4;
        public final double speedy = 0.2;
        public final double regen = 0.02;
        public final double adaptFactor = 0.5;
        public final double reflectFactor = 0.3;
        public final int dispellTime = 200;
        public final int fieryTime = 5;
        public final int weakTime = 200;
        public final int slowTime = 200;
        public final int poisonTime = 200;
        public final int witherTime = 200;
        public final int levitationTime = 200;
        public final int blindTime = 200;
        public final int confusionTime = 200;
        public final int soulBurnerTime = 60;
        public final int freezingTime = 200;
        public final int curseTime = 200;
        public final int teleportDuration = 100;
        public final int teleportRange = 16;
        public final int repellRange = 10;
        public final double repellStrength = 0.2;
        public final double corrosionDurability = 0.3;
        public final double corrosionDamage = 0.25;
        public final double erosionDurability = 0.1;
        public final double erosionDamage = 0.25;
        public final int ragnarokTime = 20;
        public final boolean ragnarokSealBackpack = false;
        public final boolean ragnarokSealSlotAdder = false;
        public final int killerAuraDamage = 10;
        public final int killerAuraRange = 6;
        public final int killerAuraInterval = 120;
        public final int shulkerInterval = 40;
        public final int grenadeInterval = 60;
        public final double drainDamage = 0.1;
        public final double drainDuration = 0.5;
        public final int drainDurationMax = 1200;
        public final int counterStrikeDuration = 100;
        public final int counterStrikeRange = 6;
        public final int pullingRange = 10;
        public final double pullingStrength = 0.2;
        public final double reprintDamage = 0.02;
        public final int reprintBypass = 10;
    }
}
