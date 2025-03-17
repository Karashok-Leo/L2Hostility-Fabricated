package karashokleo.l2hostility.init;

import dev.xkmc.l2serial.serialization.custom_handler.Handlers;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.trait.MobTraitBuilder;
import karashokleo.l2hostility.content.trait.base.AttributeTrait;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.content.trait.base.SelfEffectTrait;
import karashokleo.l2hostility.content.trait.base.TargetEffectTrait;
import karashokleo.l2hostility.content.trait.common.*;
import karashokleo.l2hostility.content.trait.goals.CounterStrikeTrait;
import karashokleo.l2hostility.content.trait.goals.EnderTrait;
import karashokleo.l2hostility.content.trait.highlevel.*;
import karashokleo.l2hostility.content.trait.legendary.*;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.data.config.provider.TraitConfigProvider;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Formatting;

import java.util.function.BiFunction;

public class LHTraits
{
    public static final RegistryKey<Registry<MobTrait>> TRAIT_KEY = RegistryKey.ofRegistry(L2Hostility.id("trait"));

    public static final Registry<MobTrait> TRAIT = FabricRegistryBuilder.createSimple(TRAIT_KEY).buildAndRegister();

    // No Desc
    public static AttributeTrait TANK;
    public static AttributeTrait SPEEDY;
    public static SelfEffectTrait PROTECTION;
    public static InvisibleTrait INVISIBLE;

    // Common
    public static FieryTrait FIERY;
    public static RegenTrait REGEN;
    public static AdaptingTrait ADAPTIVE;
    public static ReflectTrait REFLECT;
    public static ShulkerTrait SHULKER;
    public static GrenadeTrait GRENADE;
    public static CorrosionTrait CORROSION;
    public static ErosionTrait EROSION;
    public static GrowthTrait GROWTH;
    public static SplitTrait SPLIT;
    public static DrainTrait DRAIN;
    public static CounterStrikeTrait STRIKE;
    public static GravityTrait GRAVITY;
    public static AuraEffectTrait MOONWALK;
    public static ArenaTrait ARENA;

    // Legendary
    public static DementorTrait DEMENTOR;
    public static DispellTrait DISPELL;
    public static UndyingTrait UNDYING;
    public static EnderTrait ENDER;
    public static RepellingTrait REPELLING;
    public static PullingTrait PULLING;
    public static ReprintTrait REPRINT;
    public static KillerAuraTrait KILLER_AURA;
    public static RagnarokTrait RAGNAROK;

    // Effects
    public static TargetEffectTrait WEAKNESS;
    public static TargetEffectTrait SLOWNESS;
    public static TargetEffectTrait POISON;
    public static TargetEffectTrait WITHER;
    public static TargetEffectTrait BLIND;
    public static TargetEffectTrait CONFUSION;
    public static TargetEffectTrait LEVITATION;
    public static TargetEffectTrait SOUL_BURNER;
    public static TargetEffectTrait FREEZING;
    public static TargetEffectTrait CURSED;

    public static void register()
    {
        Handlers.enable(MobTrait.class, () -> TRAIT);

        TANK = Entry.of(
                        "tank",
                        new AttributeTrait(
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
                        ),
                        20, 100, 5, 20)
                .addEN()
                .addZH("重装")
                .register();
        SPEEDY = Entry.of(
                        "speedy",
                        new AttributeTrait(
                                Formatting.AQUA,
                                new AttributeTrait.AttributeEntry(
                                        "speedy",
                                        () -> EntityAttributes.GENERIC_MOVEMENT_SPEED,
                                        () -> LHConfig.common().traits.speedy,
                                        EntityAttributeModifier.Operation.MULTIPLY_TOTAL
                                )
                        ),
                        20, 100, 5, 50)
                .addEN()
                .addZH("神速")
                .register();
        PROTECTION = Entry.of(
                        "protection",
                        new SelfEffectTrait(() -> StatusEffects.RESISTANCE),
                        30, 100, 4, 50)
                .addEN()
                .addZH("保护")
                .register();
        INVISIBLE = Entry.of(
                        "invisible",
                        new InvisibleTrait(),
                        30, 100, 1, 50)
                .addEN()
                .addZH("隐身")
                .addBlacklist(LHTags.SEMIBOSS)
                .register();
        FIERY = Entry.of(
                        "fiery",
                        new FieryTrait(),
                        20, 100, 1, 20)
                .addEN()
                .addENDesc("Ignite attacker and attack target for %s seconds. Makes mob immune to fire.")
                .addZH("烈焰")
                .addZHDesc("点燃攻击者和被攻击者%s秒，怪物免疫火焰伤害")
                .register();
        REGEN = Entry.of(
                        "regenerate",
                        new RegenTrait(),
                        30, 100, 5, 50)
                .addEN()
                .addENDesc("Heals %s%% of full health every second.")
                .addZH("再生")
                .addZHDesc("每秒回复总血量的%s%%")
                .register();
        ADAPTIVE = Entry.of(
                        "adaptive",
                        new AdaptingTrait(),
                        80, 50, 5, 100)
                .addEN()
                .addENDesc("Memorize damage types taken and stack %s%% damage reduction for those damage every time. Memorizes last %s different damage types.")
                .addZH("适应")
                .addZHDesc("记忆最近收到的%2$s种伤害。每次受到记忆中的伤害时，对那个伤害叠加%s%%的减伤（无上限）")
                .register();
        REFLECT = Entry.of(
                        "reflect",
                        new ReflectTrait(),
                        80, 50, 5, 100)
                .addEN()
                .addENDesc("Reflect damage within %s blocks as %s%% magical damage")
                .addZH("反射")
                .addZHDesc("将%s格内的伤害以%s%%魔法伤害的形式反射")
                .register();
        SHULKER = Entry.of(
                        "shulker",
                        new ShulkerTrait(),
                        30, 100, 5, 70)
                .addEN()
                .addENDesc("Shoot a shulker bullet every %s seconds. Bullets will not levitate target, but deal 4 damage per level on hit.")
                .addZH("潜影")
                .addZHDesc("每隔%s秒发射一个潜影子弹。子弹命中目标时不会使其飘浮，但造成每级4点伤害。")
                .addBlacklist(LHTags.SEMIBOSS)
                .register();
        GRENADE = Entry.of(
                        "grenade",
                        new GrenadeTrait(),
                        50, 100, 5, 100)
                .addEN()
                .addENDesc("Shoot a explosive fireball every %s seconds. Fireballs will not destroy blocks and deal 4 damage per level on hit.")
                .addZH("榴弹")
                .addZHDesc("每隔%s秒发射一个会爆炸的火球。火球不会破坏方块，命中目标时造成每级4点伤害。")
                .addBlacklist(LHTags.SEMIBOSS)
                .register();
        CORROSION = Entry.of(
                        "corrosion",
                        new CorrosionTrait(),
                        50, 50, 3, 200)
                .addEN()
                .addENDesc("When hit target, randomly picks %s equipments and increase their durability loss by %s. When there aren't enough equipments, increase damage by %s per piece")
                .addZH("腐蚀")
                .addZHDesc("击中目标时，随机选中%s个装备，消耗等同于其损失的耐久的%s。如果装备不够，每少一件增加%s伤害。")
                .register();
        EROSION = Entry.of(
                        "erosion",
                        new ErosionTrait(),
                        50, 50, 3, 200)
                .addEN()
                .addENDesc("When hit target, randomly picks %s equipments and reduce their durability by %s. When there aren't enough equipments, increase damage by %s per piece")
                .addZH("风蚀")
                .addZHDesc("击中目标时，随机选中%s个装备，消耗当前耐久的%s。如果装备不够，每少一件增加%s伤害。")
                .register();
        GROWTH = Entry.of(
                        "growth",
                        new GrowthTrait(),
                        60, 300, 3, 100)
                .addEN()
                .addENDesc("Slime will grow larger when at full health. Automatically gain Regenerate trait.")
                .addZH("生长")
                .addZHDesc("史莱姆自动获得再生词条，满血时长大")
                .register();
        SPLIT = Entry.of(
                        "split",
                        new SplitTrait(),
                        50, 100, 3, 120)
                .addEN()
                .addENDesc("When mob dies, it will split into 2 of itself with half levels but same trait. This trait reduce by 1 when split.")
                .addZH("分裂")
                .addZHDesc("怪物死亡时分裂，等级减半并继承所有词条。本词条分裂时等级-1")
                .addBlacklist(LHTags.SEMIBOSS)
                .addWhitelist(
                        EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER,
                        EntityType.ZOMBIFIED_PIGLIN, EntityType.DROWNED, EntityType.HUSK,
                        EntityType.SKELETON, EntityType.WITHER_SKELETON, EntityType.STRAY,
                        EntityType.SPIDER, EntityType.CAVE_SPIDER,
                        EntityType.CREEPER, EntityType.VEX,
                        EntityType.SILVERFISH, EntityType.ENDERMITE
                )
                .register();
        DRAIN = Entry.of(
                        "drain",
                        new DrainTrait(),
                        80, 100, 3, 100)
                .addEN()
                .addENDesc("Grants a random potion trait with same level. When hit target, remove %s beneficial effects, deal %s more damage for every harmful effects, and increase their duration by %s. At most increase to %ss.")
                .addZH("嗜魔")
                .addZHDesc("怪物获得相同等级的随机药水词条。怪物击中目标时，随机移除目标身上%s个正面效果，目标身上每有一个负面效果伤害上升%s，并且所有负面效果时间延长%s，最高延长至%s秒")
                .register();
        STRIKE = Entry.of(
                        "counter_strike",
                        new CounterStrikeTrait(),
                        50, 100, 1, 60)
                .addEN()
                .addENDesc("After attacked, it will attempt to perform a counter strike.")
                .addZH("反击")
                .addZHDesc("被攻击后会试图冲刺反击")
                .addWhitelist(EntityType.WARDEN)
                .addWhitelist(LHTags.MELEE_WEAPON_TARGET)
                .register();
        GRAVITY = Entry.of(
                        "gravity",
                        new GravityTrait(),
                        50, 100, 3, 80)
                .addEN()
                .addENDesc("Increase gravity for mobs around it. Knock attackers downward when damaged.")
                .addZH("重力")
                .addZHDesc("增加周围区域重力。受到空中生物攻击时使攻击者向下坠落")
                .register();
        MOONWALK = Entry.of(
                        "moonwalk",
                        new AuraEffectTrait(() -> LHEffects.MOONWALK),
                        50, 100, 3, 80)
                .addEN()
                .addENDesc("Decrease gravity for mobs around it")
                .addZH("月步")
                .addZHDesc("减少周围区域重力")
                .register();
        ARENA = Entry.of(
                        "arena",
                        new ArenaTrait(),
                        1000, 1, 1, 50)
                .addEN()
                .addENDesc("Players around it cannot place or break blocks. Immune damage from entities not affected by this.")
                .addZH("领域")
                .addZHDesc("区域内玩家无法放置和破坏方块，免疫来自区域外的生物的伤害")
                .addWhitelist(LHTags.SEMIBOSS)
                .register();
        DEMENTOR = Entry.of(
                        "dementor",
                        new DementorTrait(),
                        120, 50, 1, 150)
                .addEN()
                .addENDesc("Immune to physical damage with a %ss cooldown. Damage bypass armor and shield with a %ss cooldown.")
                .addZH("摄魂")
                .addZHDesc("免疫物理伤害，冷却%s秒。伤害穿透护甲和盾牌，冷却%s秒")
                .register();
        DISPELL = Entry.of(
                        "dispell",
                        new DispellTrait(),
                        100, 50, 3, 150)
                .addEN()
                .addENDesc("Immune to magic damage with a %ss cooldown. Damage bypass magical protections with a %ss cooldown. Randomly picks up to %s enchanted equipment and disable enchantments on them for %s seconds, with a %ss cooldown.")
                .addZH("破魔")
                .addZHDesc("免疫魔法伤害，冷却%s秒。伤害穿透魔法保护，冷却%s秒。攻击时随机选中最多%s个附魔的装备并将其附魔封印%s秒，冷却%s秒")
                .register();
        UNDYING = Entry.of(
                        "undying",
                        new UndyingTrait(),
                        150, 100, 5, 150)
                .addEN()
                .addENDesc("Mob will heal to full health when it dies, up to %s times.")
                .addZH("不死")
                .addZHDesc("怪物死亡时满血复活，最多%s次")
                .addBlacklist(LHTags.SEMIBOSS)
                .register();
        ENDER = Entry.of(
                        "teleport",
                        new EnderTrait(),
                        120, 100, 1, 150)
                .addEN()
                .addENDesc("Mob will attempt to teleport to avoid physical damage and track targets.")
                .addZH("传送")
                .addZHDesc("怪物会通过传送躲避伤害和追踪目标")
                .addBlacklist(LHTags.SEMIBOSS)
                .register();
        REPELLING = Entry.of(
                        "repelling",
                        new RepellingTrait(),
                        50, 100, 1, 100)
                .addEN()
                .addENDesc("Mob will push away entities hostile to it within %s blocks, and immune to projectiles.")
                .addZH("排斥")
                .addZHDesc("怪物会推开%s格内对自己有敌意的生物，并且免疫弹射物")
                .addWhitelist(
                        EntityType.SKELETON, EntityType.STRAY,
                        EntityType.PILLAGER, EntityType.EVOKER, EntityType.WITCH,
                        EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN,
                        EntityType.WITHER
                )
                .register();
        PULLING = Entry.of(
                        "pulling",
                        new PullingTrait(),
                        50, 100, 1, 100)
                .addEN()
                .addENDesc("Mob will pull entities hostile to it within %s blocks.")
                .addZH("吸引")
                .addZHDesc("怪物会吸引%s格内对自己有敌意的生物")
                .addWhitelist(LHTags.MELEE_WEAPON_TARGET)
                .register();
        REPRINT = Entry.of(
                        "reprint",
                        new ReprintTrait(),
                        100, 100, 1, 100)
                .addEN()
                .addENDesc("Mob will copy target enchantments, and deal %s more damage per enchantment point")
                .addZH("复印")
                .addZHDesc("目标装备附魔点数每有1点，怪物伤害提升%s。近战复制所有装备附魔。x级附魔提供2^x附魔点数")
                .register();
        KILLER_AURA = Entry.of(
                        "killer_aura",
                        new KillerAuraTrait(),
                        100, 50, 3, 300)
                .addEN()
                .addENDesc("Deal %s magic damage to players and entities targeting it within %s blocks and apply trait effects for every %ss")
                .addZH("杀戮光环")
                .addZHDesc("每%3$s对%2$s格内的玩家和对自己有敌意的生物造成%1$s魔法伤害并施加词条效果")
                .register();
        RAGNAROK = Entry.of(
                        "ragnarok",
                        new RagnarokTrait(),
                        200, 50, 3, 600)
                .addEN()
                .addENDesc("When hit target, randomly picks up to %s equipments/trinkets and seal them with a %ss cooldown, which takes %ss to unseal.")
                .addZH("诸神黄昏")
                .addZHDesc("击中目标时，随机选中最多%s个装备/饰品，并封印它们，冷却%s秒。右键使用被封印物品%s秒以解封")
                .register();
        WEAKNESS = Entry.of(
                        "weakness",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.WEAKNESS, LHConfig.common().traits.weakTime * lv)),
                        25, 50, 5, 40)
                .addEN("Weakener")
                .addZH("虚弱")
                .addTag(LHTags.POTION)
                .register();
        SLOWNESS = Entry.of(
                        "slowness",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.SLOWNESS, LHConfig.common().traits.slowTime * lv)),
                        10, 100, 5, 20)
                .addEN("Stray")
                .addZH("流沙")
                .addTag(LHTags.POTION)
                .register();
        POISON = Entry.of(
                        "poison",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.POISON, LHConfig.common().traits.poisonTime * lv)),
                        15, 100, 3, 20)
                .addEN("Poisonous")
                .addZH("剧毒")
                .addTag(LHTags.POTION)
                .register();
        WITHER = Entry.of(
                        "wither",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.WITHER, LHConfig.common().traits.witherTime * lv)),
                        15, 50, 3, 20)
                .addEN("Withering")
                .addZH("凋零")
                .addTag(LHTags.POTION)
                .register();
        BLIND = Entry.of(
                        "blindness",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.BLINDNESS, LHConfig.common().traits.blindTime * lv)),
                        30, 50, 3, 40)
                .addEN("Blinder")
                .addZH("致盲")
                .addTag(LHTags.POTION)
                .register();
        CONFUSION = Entry.of(
                        "nausea",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.NAUSEA, LHConfig.common().traits.confusionTime * lv)),
                        30, 50, 3, 40)
                .addEN("Distorter")
                .addZH("扭曲")
                .addTag(LHTags.POTION)
                .register();
        LEVITATION = Entry.of(
                        "levitation",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(StatusEffects.LEVITATION, LHConfig.common().traits.levitationTime * lv)),
                        25, 50, 3, 40)
                .addEN("Levitater")
                .addZH("升空")
                .addTag(LHTags.POTION)
                .register();
        SOUL_BURNER = Entry.of(
                        "soul_burner",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(LHEffects.FLAME, LHConfig.common().traits.soulBurnerTime * lv)),
                        50, 100, 3, 70)
                .addEN()
                .addZH("业火")
                .addTag(LHTags.POTION)
                .register();
        FREEZING = Entry.of(
                        "freezing",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(LHEffects.ICE, LHConfig.common().traits.freezingTime * lv)),
                        30, 50, 3, 50)
                .addEN()
                .addZH("寒流")
                .addTag(LHTags.POTION)
                .register();
        CURSED = Entry.of(
                        "cursed",
                        new TargetEffectTrait(lv -> new StatusEffectInstance(LHEffects.CURSE, LHConfig.common().traits.curseTime * lv)),
                        20, 100, 3, 20)
                .addEN()
                .addZH("诅咒")
                .addTag(LHTags.POTION)
                .register();
    }

    static class Entry<T extends MobTrait> extends MobTraitBuilder<T>
    {
        private Entry(String name, T trait, TraitConfig.Config config)
        {
            super(name, trait, config);
        }

        public static <T extends MobTrait> Entry<T> of(String name, T trait, int cost, int weight, int maxRank, int minLevel)
        {
            return new Entry<>(name, trait, new TraitConfig.Config(L2Hostility.id(name), cost, weight, maxRank, minLevel));
        }

        @Override
        public <I extends Item> BiFunction<String, I, ItemBuilder<I>> getItemBuilder()
        {
            return LHItems.Entry::of;
        }

        @Override
        public void generateConfig(MobTrait trait, TraitConfig.Config config)
        {
            TraitConfigProvider.add(trait, config);
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
