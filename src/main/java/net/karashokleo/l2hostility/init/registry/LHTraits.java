package net.karashokleo.l2hostility.init.registry;

import dev.xkmc.l2serial.serialization.custom_handler.Handlers;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.traits.TraitSymbol;
import net.karashokleo.l2hostility.content.trait.base.AttributeTrait;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.content.trait.base.SelfEffectTrait;
import net.karashokleo.l2hostility.content.trait.base.TargetEffectTrait;
import net.karashokleo.l2hostility.content.trait.common.*;
import net.karashokleo.l2hostility.content.trait.goals.CounterStrikeTrait;
import net.karashokleo.l2hostility.content.trait.goals.EnderTrait;
import net.karashokleo.l2hostility.content.trait.highlevel.*;
import net.karashokleo.l2hostility.content.trait.legendary.*;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.data.provider.*;
import net.karashokleo.l2hostility.data.provider.cfg.TraitConfigProvider;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.karashokleo.l2hostility.util.StringUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class LHTraits
{
    public static final RegistryKey<Registry<MobTrait>> TRAIT_KEY = RegistryKey.ofRegistry(L2Hostility.id("trait"));

    public static final Registry<MobTrait> TRAIT = FabricRegistryBuilder.createSimple(TRAIT_KEY).buildAndRegister();

    // No Desc
    public static final AttributeTrait TANK = new AttributeTrait(
            Formatting.GREEN,
            new AttributeTrait.AttributeEntry(
                    "tank_health",
                    () -> EntityAttributes.GENERIC_MAX_HEALTH,
                    () -> LHConfig.common().traits.tankHealth,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            ),
            new AttributeTrait.AttributeEntry(
                    "tank_armor",
                    () -> EntityAttributes.GENERIC_ARMOR,
                    () -> LHConfig.common().traits.tankArmor,
                    EntityAttributeModifier.Operation.ADDITION
            ),
            new AttributeTrait.AttributeEntry(
                    "tank_tough",
                    () -> EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                    () -> LHConfig.common().traits.tankTough,
                    EntityAttributeModifier.Operation.ADDITION
            )
    );
    public static final AttributeTrait SPEEDY = new AttributeTrait(
            Formatting.AQUA,
            new AttributeTrait.AttributeEntry(
                    "speedy",
                    () -> EntityAttributes.GENERIC_MOVEMENT_SPEED,
                    () -> LHConfig.common().traits.speedy,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            )
    );
    public static final SelfEffectTrait PROTECTION = new SelfEffectTrait(() -> StatusEffects.RESISTANCE);
    public static final InvisibleTrait INVISIBLE = new InvisibleTrait();

    // Common
    public static final FieryTrait FIERY = new FieryTrait();
    public static final RegenTrait REGEN = new RegenTrait();
    public static final AdaptingTrait ADAPTIVE = new AdaptingTrait();
    public static final ReflectTrait REFLECT = new ReflectTrait();
    public static final ShulkerTrait SHULKER = new ShulkerTrait();
    public static final GrenadeTrait GRENADE = new GrenadeTrait();
    public static final CorrosionTrait CORROSION = new CorrosionTrait();
    public static final ErosionTrait EROSION = new ErosionTrait();
    public static final GrowthTrait GROWTH = new GrowthTrait();
    public static final SplitTrait SPLIT = new SplitTrait();
    public static final DrainTrait DRAIN = new DrainTrait();
    public static final CounterStrikeTrait STRIKE = new CounterStrikeTrait();
    public static final ArenaTrait ARENA = new ArenaTrait();
    public static final AuraEffectTrait GRAVITY = new AuraEffectTrait(() -> LHEffects.GRAVITY);
    public static final AuraEffectTrait MOONWALK = new AuraEffectTrait(() -> LHEffects.GRAVITY);

    // Legendary
    public static final DementorTrait DEMENTOR = new DementorTrait();
    public static final DispellTrait DISPELL = new DispellTrait();
    public static final UndyingTrait UNDYING = new UndyingTrait();
    public static final EnderTrait ENDER = new EnderTrait();
    public static final RepellingTrait REPELLING = new RepellingTrait();
    public static final PullingTrait PULLING = new PullingTrait();
    public static final ReprintTrait REPRINT = new ReprintTrait();
    public static final KillerAuraTrait KILLER_AURA = new KillerAuraTrait();
    public static final RagnarokTrait RAGNAROK = new RagnarokTrait();

    // Effects
    public static final TargetEffectTrait WEAKNESS = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.WEAKNESS, LHConfig.common().traits.weakTime * lv));
    public static final TargetEffectTrait SLOWNESS = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.SLOWNESS, LHConfig.common().traits.slowTime * lv));
    public static final TargetEffectTrait POISON = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.POISON, LHConfig.common().traits.poisonTime * lv));
    public static final TargetEffectTrait WITHER = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.WITHER, LHConfig.common().traits.witherTime * lv));
    public static final TargetEffectTrait BLIND = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.BLINDNESS, LHConfig.common().traits.blindTime * lv));
    public static final TargetEffectTrait CONFUSION = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.NAUSEA, LHConfig.common().traits.confusionTime * lv));
    public static final TargetEffectTrait LEVITATION = new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.LEVITATION, LHConfig.common().traits.levitationTime * lv));
    public static final TargetEffectTrait SOUL_BURNER = new TargetEffectTrait(lv -> new StatusEffectInstance(LHEffects.FLAME, LHConfig.common().traits.soulBurnerTime * lv));
    public static final TargetEffectTrait FREEZING = new TargetEffectTrait(lv -> new StatusEffectInstance(LHEffects.ICE, LHConfig.common().traits.freezingTime * lv));
    public static final TargetEffectTrait CURSED = new TargetEffectTrait(lv -> new StatusEffectInstance(LHEffects.CURSE, LHConfig.common().traits.curseTime * lv));

    public static void register()
    {
        Handlers.enable(MobTrait.class, () -> TRAIT);

        TraitEntry.of("tank", TANK, 20, 100, 5, 20)
                .addEN()
                .addZH("")
                .register();
        TraitEntry.of("speedy", SPEEDY, 20, 100, 5, 50)
                .addEN()
                .addZH("")
                .register();
        TraitEntry.of("protection", PROTECTION, 30, 100, 4, 50)
                .addEN()
                .addZH("")
                .register();
        TraitEntry.of("invisible", INVISIBLE, 30, 100, 1, 50)
                .addEN()
                .addZH("")
                .configure(config -> config.addBlacklist(LHTags.SEMIBOSS))
                .register();
        TraitEntry.of("fiery", FIERY, 20, 100, 1, 20)
                .addEN()
                .addENDesc("Ignite attacker and attack target for %s seconds. Makes mob immune to fire.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("regenerate", REGEN, 30, 100, 5, 50)
                .addEN()
                .addENDesc("Heals %s%% of full health every second.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("adaptive", ADAPTIVE, 80, 50, 5, 100)
                .addEN()
                .addENDesc("Memorize damage types taken and stack %s%% damage reduction for those damage every time. Memorizes last %s different damage types.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("reflect", REFLECT, 80, 50, 5, 100)
                .addEN()
                .addENDesc("Reflect direct physical damage as %s%% magical damage")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("shulker", SHULKER, 30, 100, 1, 70)
                .addEN()
                .addENDesc("Shoot bullets every %s seconds after the previous bullet disappears.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addBlacklist(LHTags.SEMIBOSS))
                .register();
        TraitEntry.of("grenade", GRENADE, 50, 100, 5, 100)
                .addEN()
                .addENDesc("Shoot explosive bullets every %s seconds after the previous bullet disappears.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addBlacklist(LHTags.SEMIBOSS))
                .register();
        TraitEntry.of("corrosion", CORROSION, 50, 50, 3, 200)
                .addEN()
                .addENDesc("When hit target, randomly picks %s equipments and increase their durability loss by %s. When there aren't enough equipments, increase damage by %s per piece")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("erosion", EROSION, 50, 50, 3, 200)
                .addEN()
                .addENDesc("When hit target, randomly picks %s equipments and reduce their durability by %s. When there aren't enough equipments, increase damage by %s per piece")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("growth", GROWTH, 60, 300, 3, 100)
                .addEN()
                .addENDesc("Slime will grow larger when at full health. Automatically gain Regenerate trait.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("split", SPLIT, 50, 100, 3, 120)
                .addEN()
                .addENDesc("When mob dies, it will split into 2 of itself with half levels but same trait. This trait reduce by 1 when split.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addWhitelist(
                        EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,
                        EntityType.ZOMBIFIED_PIGLIN, EntityType.DROWNED, EntityType.HUSK,
                        EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.STRAY,
                        EntityType.SPIDER, EntityType.CAVE_SPIDER,
                        EntityType.CREEPER, EntityType.VEX,
                        EntityType.SILVERFISH, EntityType.ENDERMITE
                ))
                .register();
        TraitEntry.of("drain", DRAIN, 80, 100, 3, 100)
                .addEN()
                .addENDesc("Grants a random potion trait with same level. When hit target, remove %s beneficial effects, deal %s more damage for every harmful effects, and increase their duration by %s. At most increase to %ss.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("strike", STRIKE, 50, 100, 1, 60)
                .addEN()
                .addENDesc("After attacked, it will attempt to perform a counter strike.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addBlacklist(EntityType.WARDEN).addBlacklist(LHTags.MELEE_WEAPON_TARGET))
                .register();
        TraitEntry.of("gravity", GRAVITY, 50, 100, 3, 80)
                .addEN()
                .addENDesc("Increase gravity for mobs around it")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("moonwalk", MOONWALK, 50, 100, 3, 80)
                .addEN()
                .addENDesc("Decrease gravity for mobs around it")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("arena", ARENA, 1000, 1, 1, 50)
                .addEN()
                .addENDesc("Players around it cannot place or break blocks. Immune damage from entities not affected by this.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addWhitelist(LHTags.SEMIBOSS))
                .register();
        TraitEntry.of("dementor", DEMENTOR, 120, 50, 1, 150)
                .addEN()
                .addENDesc("Immune to physical damage. Damage bypass armor.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("dispell", DISPELL, 100, 50, 3, 150)
                .addEN()
                .addENDesc("Immune to magic damage. Damage bypass magical protections. Randomly picks %s enchanted equipment and disable enchantments on them for %s seconds.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("undying", UNDYING, 150, 100, 1, 150)
                .addEN()
                .addENDesc("Mob will heal to full health every time it dies.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addBlacklist(LHTags.SEMIBOSS))
                .register();
        TraitEntry.of("teleport", ENDER, 120, 100, 1, 150)
                .addEN()
                .addENDesc("Mob will attempt to teleport to avoid physical damage and track targets.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addBlacklist(LHTags.SEMIBOSS))
                .register();
        TraitEntry.of("repelling", REPELLING, 50, 100, 1, 100)
                .addEN()
                .addENDesc("Mob will push away entities hostile to it within %s blocks, and immune to projectiles.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addWhitelist(
                        EntityType.SKELETON, EntityType.STRAY,
                        EntityType.PILLAGER, EntityType.EVOKER, EntityType.WITCH,
                        EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN,
                        EntityType.WITHER
                ))
                .register();
        TraitEntry.of("pulling", PULLING, 50, 100, 1, 100)
                .addEN()
                .addENDesc("Mob will pull entities hostile to it within %s blocks.")
                .addZH("")
                .addZHDesc("")
                .configure(config -> config.addWhitelist(LHTags.MELEE_WEAPON_TARGET))
                .register();
        TraitEntry.of("reprint", REPRINT, 100, 100, 1, 100)
                .addEN()
                .addENDesc("Mob will copy target enchantments, and deal %s more damage per enchantment point")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("killer_aura", KILLER_AURA, 100, 50, 3, 300)
                .addEN()
                .addENDesc("Deal %s magic damage to players and entities targeting it within %s blocks and apply trait effects for every %ss")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("ragnarok", RAGNAROK, 200, 50, 3, 600)
                .addEN()
                .addENDesc("When hit target, randomly picks %s equipments and seal them, which takes %ss to unseal.")
                .addZH("")
                .addZHDesc("")
                .register();
        TraitEntry.of("weakener", WEAKNESS, 25, 50, 5, 40)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("stray", SLOWNESS, 10, 100, 5, 20)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("poisonous", POISON, 15, 100, 3, 20)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("withering", WITHER, 15, 50, 3, 20)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("blindness", BLIND, 30, 50, 3, 40)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("nausea", CONFUSION, 30, 50, 3, 40)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("levitation", LEVITATION, 25, 50, 3, 40)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("soul_burner", SOUL_BURNER, 50, 100, 3, 70)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("freezing", FREEZING, 30, 50, 3, 50)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
        TraitEntry.of("cursed", CURSED, 20, 100, 3, 20)
                .addEN()
                .addZH("")
                .addTag(LHTags.POTION)
                .register();
    }

    static class TraitEntry<T extends MobTrait>
    {
        String name;
        T trait;
        TraitConfig.Config config;

        private TraitEntry(String name, T trait, TraitConfig.Config config)
        {
            this.name = name;
            this.trait = trait;
            this.config = config;
        }

        public static <T extends MobTrait> TraitEntry<T> of(String name, T trait, int cost, int weight, int maxRank, int minLevel)
        {
            return new TraitEntry<>(name, trait, new TraitConfig.Config(L2Hostility.id(name), cost, weight, maxRank, minLevel));
        }

        public T register()
        {
            Identifier id = L2Hostility.id(name);
            TraitSymbol symbol = new TraitSymbol(new FabricItemSettings());
            Registry.register(Registries.ITEM, id, symbol);
            ModelProvider.addItem(symbol);
            TagItemProvider.add(LHTags.TRAIT_ITEM, symbol);
            TraitConfigProvider.add(trait, config);
            return Registry.register(LHTraits.TRAIT, id, trait);
        }

        public TraitEntry<T> addEN()
        {
            return addEN(StringUtil.getNameById(name));
        }

        public TraitEntry<T> addEN(String en)
        {
            EN_US_LangProvider.addTrait(trait, en);
            return this;
        }

        public TraitEntry<T> addENDesc(String en)
        {
            EN_US_LangProvider.addTraitDesc(trait, en);
            return this;
        }

        public TraitEntry<T> addZH(String zh)
        {
            ZH_CN_LangProvider.addTrait(trait, zh);
            return this;
        }

        public TraitEntry<T> addZHDesc(String zh)
        {
            ZH_CN_LangProvider.addTraitDesc(trait, zh);
            return this;
        }

        public TraitEntry<T> addTag(TagKey<MobTrait> key)
        {
            TraitTagProvider.add(key, trait);
            return this;
        }

        @SafeVarargs
        public final TraitEntry<T> addTag(TagKey<MobTrait>... keys)
        {
            for (TagKey<MobTrait> key : keys)
                TraitTagProvider.add(key, trait);
            return this;
        }

        public TraitEntry<T> configure(Consumer<TraitConfig.Config> consumer)
        {
            consumer.accept(config);
            return this;
        }
    }
}
