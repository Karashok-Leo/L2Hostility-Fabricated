package karashokleo.l2hostility.config;

import karashokleo.l2hostility.init.LHTraits;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Config(name = "common")
public class CommonConfig implements ConfigData
{
    @Comment("Allow entity specific difficulty configs to load")
    public final boolean enableEntitySpecificDatapack = true;
    @ConfigEntry.Gui.CollapsibleObject
    public Difficulty difficulty = new Difficulty();
    @ConfigEntry.Gui.CollapsibleObject
    public Scaling scaling = new Scaling();
    @ConfigEntry.Gui.CollapsibleObject
    public OrbAndSpawner orbAndSpawner = new OrbAndSpawner();
    @ConfigEntry.Gui.CollapsibleObject
    public Items items = new Items();
    @ConfigEntry.Gui.CollapsibleObject
    public Effects effects = new Effects();
    @ConfigEntry.Gui.CollapsibleObject
    public Enchantments enchantments = new Enchantments();
    @ConfigEntry.Gui.CollapsibleObject
    public Traits traits = new Traits();
    @ConfigEntry.Gui.CollapsibleObject
    public Complements complements = new Complements();

    public static class Difficulty
    {
        @Comment("Difficulty increment takes this many kills of same level mob")
        public final int killsPerLevel = 30;
        @Comment("Max player adaptive level")
        public final int maxPlayerLevel = 2000;
        @Comment("Mobs spawned within this range will use lowest player level in range instead of nearest player's level to determine mob level")
        public final int newPlayerProtectRange = 48;
        @Comment("Decay in player difficulty on death")
        public final double playerDeathDecay = 0.8;
        @Comment("Allow KeepInventory to keep difficulty as well")
        public final boolean keepInventoryRuleKeepDifficulty = false;
        @Comment("On player death, clear dimension penalty")
        public final boolean deathDecayDimension = true;
        @Comment("On player death, reduce max trait spawned by 1")
        public final boolean deathDecayTraitCap = true;
    }

    public static class Scaling
    {
        @Comment("Health factor per level")
        public final double healthFactor = 0.03;
        @Comment("Use exponential health")
        public final boolean exponentialHealth = false;
        @Comment("Damage factor per level")
        public final double damageFactor = 0.02;
        @Comment("Use exponential damage")
        public final boolean exponentialDamage = false;
        @Comment("Experience drop factor per level")
        public final double expDropFactor = 0.05;
        @Comment("Chance per level for drowned to hold trident")
        public final double drownedTridentChancePerLevel = 0.005d;
        @Comment("""
                Enchantment bonus per level.
                Note: use it only when Apotheosis is installed,
                Otherwise too high enchantment level will yield no enchantment
                """)
        public final double enchantmentFactor = 0;
        @Comment("Difficulty bonus per level visited")
        public final int dimensionFactor = 10;
        @Comment("Difficulty bonus per block from origin")
        public final double distanceFactor = 0.003;
        @Comment("""
                Chance for health/damage bonus and trait to apply
                Not applicable to mobs with minimum level.
                """)
        public final double globalApplyChance = 1;
        @Comment("""
                Chance for trait to apply
                Not applicable to mobs with minimum level.
                """)
        public final double globalTraitChance = 1;
        @Comment("""
                Chance to stop adding traits after adding a trait
                Not applicable to mobs with minimum level.
                """)
        public final double globalTraitSuppression = 0.1;
        @Comment("Allow legendary traits")
        public final boolean allowLegendary = true;
        @Comment("Allow chunk section to accumulate difficulty")
        public final boolean allowSectionDifficulty = true;
        @Comment("Allow difficulty clearing bypass mob minimum level")
        public final boolean allowBypassMinimum = true;
        @Comment("Allow level-related extra enchantment spawning")
        public final boolean allowExtraEnchantments = true;
        @Comment("Default dimension base difficulty for mod dimensions")
        public final int defaultLevelBase = 20;
        @Comment("Default dimension difficulty variation for mod dimensions")
        public final double defaultLevelVar = 16;
        @Comment("Default dimension difficulty scale for mod dimensions")
        public final double defaultLevelScale = 1.5;
        @Comment("""
                Mobs at Lv.N will have N x k% chance to have trait
                Default k% = 0.01, so Lv.N mobs with have N% chance to have trait
                Mobs with entity config and trait chance of 1 will not be affected
                """)
        public final double initialTraitChanceSlope = 0.01;
        @Comment("Slimes hostility loot drop rate decay per split")
        public final double splitDropRateFactor = 0.25;
        @Comment("Allow mobs without AI to have levels")
        public final boolean allowNoAI = false;
        @Comment("Allow mobs allied to player to have levels")
        public final boolean allowPlayerAllies = false;
        @Comment("Keep traits on mobs tamed by player")
        public final boolean allowTraitOnOwnable = false;
    }

    public static class OrbAndSpawner
    {
        @Comment("Allow to use hostility orb")
        public final boolean allowHostilityOrb = true;
        @Comment("""
                Radius for Hostility Orb to take effect.
                0 means 1x1x1 section, 1 means 3x3x3 sections, 2 means 5x5x5 sections
                """)
        public final int orbRadius = 2;
        @Comment("[NYI] Allow to use hostility spawner")
        public final boolean allowHostilitySpawner = true;
        @Comment("[NYI] Number of mobs to spawn in Hostility Spawner")
        public final int hostilitySpawnCount = 16;
        @Comment("[NYI] Level bonus factor for mobs to spawn in Hostility Spawner")
        public final int hostilitySpawnLevelFactor = 2;
    }

    public static class Items
    {
        @Comment("Number of level to add when using bottle of curse")
        public final int bottleOfCurseLevel = 50;
        @Comment("Minimum duration for witch charge to be effective, in ticks")
        public final int witchChargeMinDuration = 200;
        @Comment("Max percentage of max health a damage can hurt wearer of Ring of Life")
        public final double ringOfLifeMaxDamage = 0.9;
        @Comment("Time in ticks of Soul Flame to inflict")
        public final int flameThornTime = 100;
        @Comment("Radius in blocks for Ring of Reflection to work")
        public final int reflectTrinketRadius = 16;
        @Comment("Factor of effect duration for witch wand, to make up for splash decay")
        public final int witchWandFactor = 4;
        @Comment("Factor of maximum durability to cost for ring of corrosion")
        public final double ringOfCorrosionFactor = 0.2;
        @Comment("Penalty of maximum durability to cost for ring of corrosion")
        public final double ringOfCorrosionPenalty = 0.1;
        @Comment("Percentage of health to heal every second")
        public final double ringOfHealingRate = 0.05;

        @ConfigEntry.Gui.CollapsibleObject
        public final Curse curse = new Curse();

        public static class Curse
        {
            @Comment("Number of level to add when using Curse of Envy")
            public final int envyExtraLevel = 50;
            @Comment("Number of level to add when using Curse of Greed")
            public final int greedExtraLevel = 50;
            @Comment("Number of level to add when using Curse of Lust")
            public final int lustExtraLevel = 50;
            @Comment("Number of level to add when using Curse of Wrath")
            public final int wrathExtraLevel = 50;
            @Comment("Hostility loot drop factor when using Curse of Greed")
            public final double greedDropFactor = 2;
            @Comment("Trait item drop rate per rank when using Curse of Envy")
            public final double envyDropRate = 0.02;
            @Comment("Bottle of Curse drop rate per level when using Curse of Gluttony")
            public final double gluttonyBottleDropRate = 0.02;
            @Comment("Damage bonus per level difference when using Curse of Wrath")
            public final double wrathDamageBonus = 0.05;
            @Comment("Damage bonus per level when using Curse of Pride")
            public final double prideDamageBonus = 0.02;
            @Comment("Health boost per level in percentage when using Curse of Pride")
            public final double prideHealthBonus = 0.02;
            @Comment("Trait cost multiplier when using Curse of Pride")
            public final double prideTraitFactor = 0.5;
        }

        @Comment("Number of level to add when using Abrahadabra")
        public final int abrahadabraExtraLevel = 100;
        @Comment("Number of level to add when using Greed of Nidhoggur")
        public final int nidhoggurExtraLevel = 100;
        @Comment("All loot drop factor when using Greed of Nidhoggur")
        public final double nidhoggurDropFactor = 0.01;
        @Comment("Insulator Enchantment factor for reducing pushing")
        public final double insulatorFactor = 0.8;
    }

    public static class Effects
    {
        @Comment("""
                Effect Categories that can cleansed
                Only supports configuration in the config file.
                """)
        @ConfigEntry.Gui.Excluded
        public final List<StatusEffectCategory> cleansePredicate = List.of(StatusEffectCategory.BENEFICIAL, StatusEffectCategory.HARMFUL, StatusEffectCategory.NEUTRAL);
    }

    public static class Enchantments
    {

    }

    public static class Traits
    {
        @Comment("Health bonus for Tank trait per level")
        public final double tankHealth = 0.5;
        @Comment("Armor bonus for Tank trait per level")
        public final double tankArmor = 10;
        @Comment("Toughness bonus for Tank trait per level")
        public final double tankTough = 4;
        @Comment("Speed bonus for Speedy trait per level")
        public final double speedy = 0.2;
        @Comment("Regen rate for Regeneration trait per second per level")
        public final double regen = 0.02;
        @Comment("Damage factor for Adaptive. Higher means less reduction")
        public final double adaptFactor = 0.5;
        @Comment("Reflect factor per level for Reflect. 0.5 means +50% extra damage")
        public final double reflectFactor = 0.3;
        @Comment("Duration in ticks for enchantments to be disabled per level for Dispell")
        public final int dispellTime = 200;
        @Comment("Duration in seconds to set target on fire by Fiery")
        public final int fieryTime = 5;
        @Comment("Duration in ticks for Weakness")
        public final int weakTime = 200;
        @Comment("Duration in ticks for Slowness")
        public final int slowTime = 200;
        @Comment("Duration in ticks for Poison")
        public final int poisonTime = 200;
        @Comment("Duration in ticks for Wither")
        public final int witherTime = 200;
        @Comment("""
                Duration in ticks for Levitation
                It defaults to 200 in the original mod, but I've reduced it based on my personal preference.
                """)
        public final int levitationTime = 80;
        @Comment("Duration in ticks for Blindness")
        public final int blindTime = 200;
        @Comment("Duration in ticks for Nausea")
        public final int confusionTime = 200;
        @Comment("Duration in ticks for Soul Burner")
        public final int soulBurnerTime = 60;
        @Comment("Duration in ticks for Freezing")
        public final int freezingTime = 200;
        @Comment("Duration in ticks for Cursed")
        public final int curseTime = 200;
        @Comment("Interval in ticks for Teleport")
        public final int teleportDuration = 100;
        @Comment("Range in blocks for Teleport")
        public final int teleportRange = 16;
        @Comment("Range in blocks for Repell")
        public final int repellRange = 10;
        @Comment("Repell force strength, default is 0.2")
        public final double repellStrength = 0.2;
        @Comment("Fraction of remaining durability to corrode, per trait rank")
        public final double corrosionDurability = 0.3;
        @Comment("Damage bonus when nothing to corrode")
        public final double corrosionDamage = 0.25;
        @Comment("Fraction of lost durability to erode, per trait rank")
        public final double erosionDurability = 0.1;
        @Comment("Damage bonus when nothing to erode")
        public final double erosionDamage = 0.25;
        @Comment("Seal time per level for Ragnarok")
        public final int ragnarokTime = 20;
        @Comment("Allow Ragnarok to seal items with Backpack in its id")
        public final boolean ragnarokSealBackpack = false;
        @Comment("Allow Ragnarok to seal trinket items that adds trinket slot")
        public final boolean ragnarokSealSlotAdder = false;
        @Comment("Damage for killer aura")
        public final int killerAuraDamage = 10;
        @Comment("Range for for killer aura")
        public final int killerAuraRange = 6;
        @Comment("Interval for for killer aura")
        public final int killerAuraInterval = 120;
        @Comment("Interval for for shulker")
        public final int shulkerInterval = 40;
        @Comment("Interval for for grenade (explode shulker in the original mod)")
        public final int grenadeInterval = 60;
        @Comment("Damage bonus for each negative effects")
        public final double drainDamage = 0.1;
        @Comment("Duration boost for negative effects")
        public final double drainDuration = 0.5;
        @Comment("Max duration boost for negative effects")
        public final int drainDurationMax = 1200;
        @Comment("Max duration boost for negative effects")
        public final int counterStrikeDuration = 100;
        @Comment("Max duration boost for negative effects")
        public final int counterStrikeRange = 6;
        @Comment("Range in blocks for Pulling")
        public final int pullingRange = 10;
        @Comment("Pulling force strength, default is 0.2")
        public final double pullingStrength = 0.2;
        @Comment("Reprint damage factor per enchantment point")
        public final double reprintDamage = 0.02;
        @Comment("Reprint will gain Void Touch 20 (NYI) and Vanishing Curse when it hits a mob with max Enchantment level of X or higher")
        public final int reprintBypass = 10;

        @Comment("""
                Trait toggle
                Only supports configuration in the config file.
                """)
        @ConfigEntry.Gui.Excluded
        public final Map<String, Boolean> traitToggle = LHTraits.TRAIT.stream().collect(
                Collectors.toMap(
                        mobTrait -> mobTrait.getNonNullId().getPath(),
                        mobTrait -> true
                )
        );
        @Comment("""
                Effect range for aura effect traits
                Only supports configuration in the config file.
                """)
        @ConfigEntry.Gui.Excluded
        public final Map<String, Integer> auraRange = Map.of(
                "gravity", 10,
                "moonwalk", 10,
                "arena", 24
        );
    }

    public static class Complements
    {
        public final boolean enableVanillaItemRecipe = true;
        public final boolean enableSpawnEggRecipe = true;
        public final boolean enableImmunityEnchantments = true;

        @ConfigEntry.Gui.CollapsibleObject
        public final Materials materials = new Materials();

        @ConfigEntry.Gui.CollapsibleObject
        public final FireCharge fireCharge = new FireCharge();

        @ConfigEntry.Gui.CollapsibleObject
        public final Properties properties = new Properties();

        public static class Materials
        {
            public final double windSpeed = 10;
            public final int belowVoid = 16;
            public final int phantomHeight = 200;
            public final int explosionDamage = 80;
            public final boolean enableSpaceShard = true;
            public final int spaceDamage = 16384;
        }

        public static class FireCharge
        {
            public final int soulFireChargeDuration = 60;
            public final int blackFireChargeDuration = 100;
            public final int strongFireChargePower = 2;
        }

        public static class Properties
        {
            public final int totemicHealDuration = 100;
            public final int totemicHealAmount = 1;
            public final double windSweepIncrement = 1;
            public final double emeraldDamageFactor = 0.5;
            public final int emeraldBaseRange = 10;
            public final int iceEnchantDuration = 100;
            public final int flameEnchantDuration = 60;
            public final int bleedEnchantDuration = 80;
            public final int curseEnchantDuration = 100;
            public final int bleedEnchantMax = 3;
            public final double voidTouchChance = 0.05;
            public final double voidTouchChanceBonus = 0.5;
            public final double mobTypeBonus = 1D;
        }
    }

    /*
    banBottles = builder.comment("Ban drinking bottle of curse and sanity")
        .define("banBottles", false);

    disableHostilityLootCurioRequirement = builder.comment("Disable curio requirement for hostility loot")
        .define("disableHostilityLootCurioRequirement", false);

    dropRateFromSpawner = builder.comment("Drop rate of hostility loot from mobs from spawner")
        .defineInRange("dropRateFromSpawner", 0.5d, 0, 1);

    maxMobLevel = builder.comment("Max mob level")
        .defineInRange("maxMobLevel", 3000, 100, 100000);

    nidhoggurCapAtItemMaxStack = builder.comment("Cap drop at item max stack size")
        .define("nidhoggurCapAtItemMaxStack", false);

    bookOfReprintSpread = builder.comment("When using book of reprint to copy books, drop extra on player and does not allow overstacking")
        .define("bookOfReprintSpread",false);
    */
}
