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
    public boolean enableEntitySpecificDatapack = true;
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
        public int killsPerLevel = 30;
        @Comment("Max player adaptive level")
        public int maxPlayerLevel = 2000;
        @Comment("Max mob level")
        public int maxMobLevel = 3000;
        @Comment("Mobs spawned within this range will use lowest player level in range instead of nearest player's level to determine mob level")
        public int newPlayerProtectRange = 48;
        @Comment("Decay in player difficulty on death")
        public double playerDeathDecay = 0.8;
        @Comment("Allow KeepInventory to keep difficulty as well")
        public boolean keepInventoryRuleKeepDifficulty = false;
        @Comment("On player death, clear dimension penalty")
        public boolean deathDecayDimension = true;
        @Comment("On player death, reduce max trait spawned by 1")
        public boolean deathDecayTraitCap = true;
    }

    public static class Scaling
    {
        @Comment("Health factor per level")
        public double healthFactor = 0.03;
        @Comment("Use exponential health")
        public boolean exponentialHealth = false;
        @Comment("Damage factor per level")
        public double damageFactor = 0.02;
        @Comment("Use exponential damage")
        public boolean exponentialDamage = false;
        @Comment("Experience drop factor per level")
        public double expDropFactor = 0.05;
        @Comment("Chance per level for drowned to hold trident")
        public double drownedTridentChancePerLevel = 0.005d;
        @Comment("""
                Enchantment bonus per level.
                Note: use it only when Apotheosis is installed,
                Otherwise too high enchantment level will yield no enchantment
                """)
        public double enchantmentFactor = 0;
        @Comment("Difficulty bonus per level visited")
        public int dimensionFactor = 10;
        @Comment("Difficulty bonus per block from origin")
        public double distanceFactor = 0.003;
        @Comment("""
                Chance for health/damage bonus and trait to apply
                Not applicable to mobs with minimum level.
                """)
        public double globalApplyChance = 1;
        @Comment("""
                Chance for trait to apply
                Not applicable to mobs with minimum level.
                """)
        public double globalTraitChance = 1;
        @Comment("""
                Chance to stop adding traits after adding a trait
                Not applicable to mobs with minimum level.
                """)
        public double globalTraitSuppression = 0.1;
        @Comment("Allow legendary traits")
        public boolean allowLegendary = true;
        @Comment("Allow chunk section to accumulate difficulty")
        public boolean allowSectionDifficulty = true;
        @Comment("Allow difficulty clearing bypass mob minimum level")
        public boolean allowBypassMinimum = true;
        @Comment("Allow level-related extra enchantment spawning")
        public boolean allowExtraEnchantments = true;
        @Comment("Default dimension base difficulty for mod dimensions")
        public int defaultLevelBase = 20;
        @Comment("Default dimension difficulty variation for mod dimensions")
        public double defaultLevelVar = 16;
        @Comment("Default dimension difficulty scale for mod dimensions")
        public double defaultLevelScale = 1.5;
        @Comment("""
                Mobs at Lv.N will have N x k% chance to have trait
                Default k% = 0.01, so Lv.N mobs with have N% chance to have trait
                Mobs with entity config and trait chance of 1 will not be affected
                """)
        public double initialTraitChanceSlope = 0.01;
        @Comment("Slimes hostility loot drop rate decay per split")
        public double splitDropRateFactor = 0.25;
        @Comment("Allow mobs without AI to have levels")
        public boolean allowNoAI = false;
        @Comment("Allow mobs allied to player to have levels")
        public boolean allowPlayerAllies = false;
        @Comment("Keep traits on mobs tamed by player")
        public boolean allowTraitOnOwnable = false;
    }

    public static class OrbAndSpawner
    {
        @Comment("Allow to use hostility orb")
        public boolean allowHostilityOrb = true;
        @Comment("""
                Radius for Hostility Orb to take effect.
                0 means 1x1x1 section, 1 means 3x3x3 sections, 2 means 5x5x5 sections
                """)
        public int orbRadius = 2;
        @Comment("[NYI] Allow to use hostility spawner")
        public boolean allowHostilitySpawner = true;
        @Comment("[NYI] Number of mobs to spawn in Hostility Spawner")
        public int hostilitySpawnCount = 16;
        @Comment("[NYI] Level bonus factor for mobs to spawn in Hostility Spawner")
        public int hostilitySpawnLevelFactor = 2;
    }

    public static class Items
    {
        @Comment("Number of level to add when using bottle of curse")
        public int bottleOfCurseLevel = 50;
        @Comment("Minimum duration for witch charge to be effective, in ticks")
        public int witchChargeMinDuration = 200;
        @Comment("Max percentage of max health a damage can hurt wearer of Ring of Life")
        public double ringOfLifeMaxDamage = 0.9;
        @Comment("Time in ticks of Soul Flame to inflict")
        public int flameThornTime = 100;
        @Comment("Radius in blocks for Ring of Reflection to work")
        public int reflectTrinketRadius = 16;
        @Comment("Factor of effect duration for witch wand, to make up for splash decay")
        public int witchWandFactor = 4;
        @Comment("Factor of maximum durability to cost for ring of corrosion")
        public double ringOfCorrosionFactor = 0.2;
        @Comment("Penalty of maximum durability to cost for ring of corrosion")
        public double ringOfCorrosionPenalty = 0.1;
        @Comment("Percentage of health to heal every second")
        public double ringOfHealingRate = 0.05;

        @ConfigEntry.Gui.CollapsibleObject
        public Curse curse = new Curse();
        @Comment("Number of level to add when using Abrahadabra")
        public int abrahadabraExtraLevel = 100;
        @Comment("Number of level to add when using Greed of Nidhoggur")
        public int nidhoggurExtraLevel = 100;
        @Comment("All loot drop factor when using Greed of Nidhoggur")
        public double nidhoggurDropFactor = 0.01;
        @Comment("Cap drop at item max stack size")
        public boolean nidhoggurCapAtItemMaxStack = true;
        @Comment("When using book of reprint to copy books, drop extra on player and does not allow overstacking")
        public boolean bookOfReprintSpread = false;
        @Comment("Insulator Enchantment factor for reducing pushing")
        public double insulatorFactor = 0.8;

        public static class Curse
        {
            @Comment("Number of level to add when using Curse of Envy")
            public int envyExtraLevel = 50;
            @Comment("Number of level to add when using Curse of Greed")
            public int greedExtraLevel = 50;
            @Comment("Number of level to add when using Curse of Lust")
            public int lustExtraLevel = 50;
            @Comment("Number of level to add when using Curse of Wrath")
            public int wrathExtraLevel = 50;
            @Comment("Hostility loot drop factor when using Curse of Greed")
            public double greedDropFactor = 2;
            @Comment("Trait item drop rate per rank when using Curse of Envy")
            public double envyDropRate = 0.02;
            @Comment("Bottle of Curse drop rate per level when using Curse of Gluttony")
            public double gluttonyBottleDropRate = 0.02;
            @Comment("Damage bonus per level difference when using Curse of Wrath")
            public double wrathDamageBonus = 0.05;
            @Comment("Damage bonus per level when using Curse of Pride")
            public double prideDamageBonus = 0.02;
            @Comment("Health boost per level in percentage when using Curse of Pride")
            public double prideHealthBonus = 0.02;
            @Comment("Trait cost multiplier when using Curse of Pride")
            public double prideTraitFactor = 0.5;
        }
    }

    public static class Effects
    {
        @Comment("""
                Effect Categories that can cleansed
                Only supports configuration in the config file.
                """)
        @ConfigEntry.Gui.Excluded
        public List<StatusEffectCategory> cleansePredicate = List.of(StatusEffectCategory.BENEFICIAL, StatusEffectCategory.HARMFUL, StatusEffectCategory.NEUTRAL);
    }

    public static class Enchantments
    {

    }

    public static class Traits
    {
        @Comment("Health bonus for Tank trait per level")
        public double tankHealth = 0.5;
        @Comment("Armor bonus for Tank trait per level")
        public double tankArmor = 10;
        @Comment("Toughness bonus for Tank trait per level")
        public double tankTough = 4;
        @Comment("Speed bonus for Speedy trait per level")
        public double speedy = 0.2;
        @Comment("Regen rate for Regeneration trait per second per level")
        public double regen = 0.02;
        @Comment("Damage factor for Adaptive. Higher means less reduction")
        public double adaptFactor = 0.5;
        @Comment("Trigger range for Reflect")
        public double reflectRange = 3;
        @Comment("Allow Reflect to reflect magic damage")
        public boolean reflectMagic = true;
        @Comment("Reflect factor per level for Reflect. 0.5 means +50% extra damage")
        public double reflectFactor = 0.3;
        @Comment("Duration in ticks for enchantments to be disabled per level for Dispell")
        public int dispellTime = 200;
        @Comment("Duration in seconds to set target on fire by Fiery")
        public int fieryTime = 5;
        @Comment("Duration in ticks for Weakness")
        public int weakTime = 200;
        @Comment("Duration in ticks for Slowness")
        public int slowTime = 200;
        @Comment("Duration in ticks for Poison")
        public int poisonTime = 200;
        @Comment("Duration in ticks for Wither")
        public int witherTime = 200;
        @Comment("""
                Duration in ticks for Levitation
                It defaults to 200 in the original mod, but I've reduced it based on my personal preference.
                """)
        public int levitationTime = 80;
        @Comment("Duration in ticks for Blindness")
        public int blindTime = 200;
        @Comment("Duration in ticks for Nausea")
        public int confusionTime = 200;
        @Comment("Duration in ticks for Soul Burner")
        public int soulBurnerTime = 60;
        @Comment("Duration in ticks for Freezing")
        public int freezingTime = 200;
        @Comment("Duration in ticks for Cursed")
        public int curseTime = 200;
        @Comment("Interval in ticks for Teleport")
        public int teleportDuration = 100;
        @Comment("Range in blocks for Teleport")
        public int teleportRange = 16;
        @Comment("Range in blocks for Repell")
        public int repellRange = 10;
        @Comment("Repell force strength, default is 0.2")
        public double repellStrength = 0.2;
        @Comment("Fraction of remaining durability to corrode, per trait rank")
        public double corrosionDurability = 0.3;
        @Comment("Damage bonus when nothing to corrode")
        public double corrosionDamage = 0.25;
        @Comment("Fraction of lost durability to erode, per trait rank")
        public double erosionDurability = 0.1;
        @Comment("Damage bonus when nothing to erode")
        public double erosionDamage = 0.25;
        @Comment("Seal time per level for Ragnarok")
        public int ragnarokTime = 20;
        @Comment("Allow Ragnarok to seal items with Backpack in its id")
        public boolean ragnarokSealBackpack = false;
        @Comment("Allow Ragnarok to seal trinket items that adds trinket slot")
        public boolean ragnarokSealSlotAdder = false;
        @Comment("Damage for killer aura")
        public int killerAuraDamage = 10;
        @Comment("Range for for killer aura")
        public int killerAuraRange = 6;
        @Comment("Interval for for killer aura")
        public int killerAuraInterval = 120;
        @Comment("Interval for for shulker")
        public int shulkerInterval = 40;
        @Comment("Interval for for grenade (explode shulker in the original mod)")
        public int grenadeInterval = 60;
        @Comment("Damage bonus for each negative effects")
        public double drainDamage = 0.1;
        @Comment("Duration boost for negative effects")
        public double drainDuration = 0.5;
        @Comment("Max duration boost for negative effects")
        public int drainDurationMax = 1200;
        @Comment("Max duration boost for negative effects")
        public int counterStrikeDuration = 100;
        @Comment("Max duration boost for negative effects")
        public int counterStrikeRange = 6;
        @Comment("Range in blocks for Pulling")
        public int pullingRange = 10;
        @Comment("Pulling force strength, default is 0.2")
        public double pullingStrength = 0.2;
        @Comment("Reprint damage factor per enchantment point")
        public double reprintDamage = 0.02;
        @Comment("Reprint will gain Void Touch 20 (NYI) and Vanishing Curse when it hits a mob with max Enchantment level of X or higher")
        public int reprintBypass = 10;

        @Comment("""
                Trait toggle
                Only supports configuration in the config file.
                """)
        @ConfigEntry.Gui.Excluded
        public Map<String, Boolean> traitToggle = LHTraits.TRAIT.stream().collect(
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
        public Map<String, Integer> auraRange = Map.of(
                "gravity", 10,
                "moonwalk", 10,
                "arena", 24
        );
    }

    public static class Complements
    {
        //        public boolean enableVanillaItemRecipe = true;
//        public boolean enableSpawnEggRecipe = true;
        public boolean enableImmunityEnchantments = true;

        @ConfigEntry.Gui.CollapsibleObject
        public Materials materials = new Materials();

        @ConfigEntry.Gui.CollapsibleObject
        public FireCharge fireCharge = new FireCharge();

        @ConfigEntry.Gui.CollapsibleObject
        public Properties properties = new Properties();

        public static class Materials
        {
            @Comment("Requirement for obtaining Captured Wind. Unit: Block per Tick")
            public double windSpeed = 10;
            @Comment("Requirement for void eye drop")
            public int belowVoid = 16;
            @Comment("Requirement for sun membrane drop")
            public int phantomHeight = 200;
            @Comment("Requirement for explosion shard drop")
            public int explosionDamage = 80;
            @Comment("Requirement for space shard drop")
            public int spaceDamage = 16384;
            public boolean enableSpaceShard = true;
        }

        public static class FireCharge
        {
            @Comment("Soul Fire Charge Duration")
            public int soulFireChargeDuration = 60;
            @Comment("Black Fire Charge Duration")
            public int blackFireChargeDuration = 100;
            @Comment("Strong Fire Charge Power")
            public int strongFireChargePower = 2;
            @Comment("Strong Fire Charge Breaks Block")
            public boolean strongFireChargeBreakBlock = true;
        }

        public static class Properties
        {
            //            @Comment("Totemic Armor healing interval")
//            public int totemicHealDuration = 100;
//            @Comment("Totemic Armor healing amount")
//            public int totemicHealAmount = 1;
            @Comment("Wind Sweep enchantment increment to sweep hit box")
            public double windSweepIncrement = 1;
            @Comment("Damage factor of emerald splash")
            public double emeraldDamageFactor = 0.5;
            @Comment("Base range for emerald splash")
            public int emeraldBaseRange = 10;
            //            @Comment("Sonic Shooter Damage")
//            public int sonicShooterDamage = 10;
//            @Comment("Hellfire Wand Damage")
//            public int hellfireWandDamage = 10;
            @Comment("Base duration for iceBlade")
            public int iceEnchantDuration = 100;
            @Comment("Duration for flameBlade")
            public int flameEnchantDuration = 60;
            @Comment("Base duration for sharpBlade")
            public int bleedEnchantDuration = 80;
            @Comment("Base duration for cursedBlade")
            public int curseEnchantDuration = 100;
            @Comment("Max effect level for sharpBlade")
            public int bleedEnchantMax = 10;
            @Comment("Void Touch chance for true damage")
            public double voidTouchChance = 0.05;
            @Comment("Void Touch chance for true damage if bypass armor or magic")
            public double voidTouchChanceBonus = 0.5;
            //            @Comment("Bonus damage factor for specific materials against specific mob types")
//            public double mobTypeBonus = 1D;
            @Comment("Damage factor for lifeSync (damage to user per durability cost)")
            public double lifeSyncFactor = 1;
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
    */
}
