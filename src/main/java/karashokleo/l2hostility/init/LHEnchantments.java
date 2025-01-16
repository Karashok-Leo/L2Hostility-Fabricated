package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.enchantment.HostilityEnchantment;
import karashokleo.l2hostility.content.enchantment.RemoveTraitEnchantment;
import karashokleo.l2hostility.content.enchantment.VanishEnchantment;
import karashokleo.l2hostility.content.enchantment.armors.DurableArmorEnchantment;
import karashokleo.l2hostility.content.enchantment.armors.FlameThornEnchantment;
import karashokleo.l2hostility.content.enchantment.armors.IceThornEnchantment;
import karashokleo.l2hostility.content.enchantment.armors.StableBodyEnchantment;
import karashokleo.l2hostility.content.enchantment.core.ImmuneEnchantment;
import karashokleo.l2hostility.content.enchantment.core.SingleLevelEnchantment;
import karashokleo.l2hostility.content.enchantment.special.LegendaryEnchantment;
import karashokleo.l2hostility.content.enchantment.special.LifeMendingEnchantment;
import karashokleo.l2hostility.content.enchantment.special.LifeSyncEnchantment;
import karashokleo.l2hostility.content.enchantment.weapon.*;
import karashokleo.leobrary.datagen.builder.EnchantmentBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class LHEnchantments
{
    public static HostilityEnchantment INSULATOR;
    public static RemoveTraitEnchantment SPLIT_SUPPRESS;
    public static VanishEnchantment VANISH;

    public static SingleLevelEnchantment SHULKER_ARMOR;
    //    public static SingleLevelEnchantment ENDER_MASK;
//    public static SingleLevelEnchantment SHINNY;
//    public static SingleLevelEnchantment SNOW_WALKER;
    public static StableBodyEnchantment STABLE_BODY;
    public static DurableArmorEnchantment DURABLE_ARMOR;
    public static LifeSyncEnchantment LIFE_SYNC;
    public static LifeMendingEnchantment LIFE_MENDING;
    public static SingleLevelEnchantment SAFEGUARD;
    public static LegendaryEnchantment HARDENED;
    public static SingleLevelEnchantment DAMPENED;
    //    public static SoulBindingEnchantment SOUL_BOUND;
    public static LegendaryEnchantment ETERNAL;

    public static WindSweepEnchantment WIND_SWEEP;
    public static SoulFlameBladeEnchantment FLAME_BLADE;
    public static IceBladeEnchantment ICE_BLADE;
    public static SharpBladeEnchantment SHARP_BLADE;
    public static CurseBladeEnchantment CURSE_BLADE;

    public static IceThornEnchantment ICE_THORN;
    public static FlameThornEnchantment FLAME_THORN;
    public static VoidTouchEnchantment VOID_TOUCH;

    public static ImmuneEnchantment ENCH_PROJECTILE;
    public static ImmuneEnchantment ENCH_FIRE;
    public static ImmuneEnchantment ENCH_ENVIRONMENT;
    public static ImmuneEnchantment ENCH_EXPLOSION;
    public static ImmuneEnchantment ENCH_MAGIC;
    public static ImmuneEnchantment ENCH_INVINCIBLE;
    public static ImmuneEnchantment ENCH_MATES;

    public static void register()
    {
        INSULATOR = Entry.of(
                        "insulator",
                        new HostilityEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.ARMOR,
                                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET},
                                3
                        )
                )
                .addEN()
                .addENDesc("Reduce trait effects that pulls or pushes you.")
                .addZH("绝缘")
                .addZHDesc("减少词条效果对你施加的推挤或者拉扯")
                .register();
        SPLIT_SUPPRESS = Entry.of(
                        "split_suppress",
                        new SplitSuppressEnchantment()
                )
                .addEN()
                .addENDesc("Disable Split trait on enemies on hit.")
                .addZH("分裂抑制")
                .addZHDesc("击中目标时删除分裂词条")
                .register();
        VANISH = Entry.of(
                        "vanish",
                        new VanishEnchantment()
                )
                .addEN()
                .addENDesc("This item vanishes when on ground or in hand of survival / adventure player.")
                .addZH("毁灭")
                .addZHDesc("掉落或者被生存玩家持有时消失")
                .register();

        SHULKER_ARMOR = Entry.of(
                        "shulker_armor",
                        new SingleLevelEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.VANISHABLE,
                                EquipmentSlot.values()
                        )
                )
                .addEN("Transparent")
                .addENDesc("Armor invisible to mobs and players when wearer has invisibility effect.")
                .addZH("遁隐")
                .addZHDesc("玩家隐身时，物品也隐身")
                .register();
//        ENDER_MASK = Entry.of(
//                        "ender_mask",
//                        new SingleLevelEnchantment(
//                                Enchantment.Rarity.VERY_RARE,
//                                EnchantmentTarget.ARMOR_HEAD,
//                                new EquipmentSlot[]{EquipmentSlot.HEAD}
//                        )
//                )
//                .addEN()
//                .addENDesc("Endermen won't be mad at you for direct eye contact")
//                .addZH("末影帷幕")
//                .addZHDesc("视线不激怒末影人")
//                .register();
//        SHINNY = Entry.of(
//                        "shinny",
//                        new SingleLevelEnchantment(
//                                Enchantment.Rarity.VERY_RARE,
//                                EnchantmentTarget.WEARABLE,
//                                EquipmentSlot.values()
//                        )
//                )
//                .addEN()
//                .addENDesc("Piglins loves it.")
//                .addZH("金光闪闪")
//                .addZHDesc("让猪灵以为是金制品")
//                .register();
//        SNOW_WALKER = Entry.of(
//                        "snow_walker",
//                        new SingleLevelEnchantment(
//                                Enchantment.Rarity.VERY_RARE,
//                                EnchantmentTarget.ARMOR_FEET,
//                                new EquipmentSlot[]{EquipmentSlot.FEET}
//                        )
//                )
//                .addEN()
//                .addENDesc("Allow Wearer to walk on powdered snow.")
//                .addZH("细雪行者")
//                .addZHDesc("让使用者能在细雪上行走")
//                .register();
        STABLE_BODY = Entry.of(
                        "stable_body",
                        new StableBodyEnchantment()
                )
                .addEN()
                .addENDesc("Player won't be knocked back when wearing chestplate with this enchantment.")
                .addZH("稳定之体")
                .addZHDesc("玩家不会因攻击而摇晃")
                .register();
        DURABLE_ARMOR = Entry.of(
                        "durable_armor",
                        new DurableArmorEnchantment()
                )
                .addEN()
                .addENDesc("Armor will have higher durability. Conflict with Unbreaking.")
                .addZH("盔甲耐久")
                .addZHDesc("让盔甲获得更高的耐久。和耐久附魔不兼容")
                .register();
        LIFE_SYNC = Entry.of(
                        "life_sync",
                        new LifeSyncEnchantment()
                )
                .addEN()
                .addENDesc("Cost health instead of durability when possible. May kill the user")
                .addZH("同心")
                .addZHDesc("将消耗耐久时改为消耗玩家生命值。可能会杀死玩家")
                .register();
        LIFE_MENDING = Entry.of(
                        "life_mending",
                        new LifeMendingEnchantment()
                )
                .addEN()
                .addENDesc("When healing, cost heal amount to repair item first.")
                .addZH("生命修补")
                .addZHDesc("回复生命时，优先消耗回复数值来修补装备")
                .register();
        SAFEGUARD = Entry.of(
                        "safeguard",
                        new SingleLevelEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.BREAKABLE,
                                EquipmentSlot.values()
                        )
                )
                .addEN()
                .addENDesc("when item has more than 1 durability, it will keep at least 1 durability if damaged")
                .addZH("保险")
                .addZHDesc("耐久大于1的此物品受到耐久损耗时，最多削减耐久到1")
                .register();
        HARDENED = Entry.of(
                        "hardened",
                        new LegendaryEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.BREAKABLE,
                                EquipmentSlot.values()
                        )
                )
                .addEN()
                .addENDesc("Durability loss will be capped to 1.")
                .addZH("硬化")
                .addZHDesc("物品的单次耐久损耗最多为1")
                .register();
        DAMPENED = Entry.of(
                        "dampened",
                        new SingleLevelEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.WEARABLE,
                                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}
                        )
                )
                .addEN()
                .addENDesc("Cancel all vibrations emitted by wearer")
                .addZH("沉默")
                .addZHDesc("穿着4件带有沉默效果的装备时，玩家发出的任何震动都会被抵消")
                .register();
        ETERNAL = Entry.of(
                        "eternal",
                        new LegendaryEnchantment(
                                Enchantment.Rarity.VERY_RARE,
                                EnchantmentTarget.BREAKABLE,
                                EquipmentSlot.values()
                        )
                )
                .addEN()
                .addENDesc("Item will ignore all durability damage.")
                .addZH("永恒(创造)")
                .addZHDesc("物品无视耐久损耗")
                .register();

        WIND_SWEEP = Entry.of(
                        "wind_sweep",
                        new WindSweepEnchantment()
                )
                .addEN()
                .addENDesc("Increase sweeping hit box")
                .addZH("剑风")
                .addZHDesc("增加横扫范围")
                .register();
        FLAME_BLADE = Entry.of(
                        "soul_flame_blade",
                        new SoulFlameBladeEnchantment()
                )
                .addEN()
                .addENDesc("Apply flame effect to target. Higher levels have higher damage.")
                .addZH("炎刃")
                .addZHDesc("对敌人施加魂火效果。更高级的附魔造成的伤害更高")
                .register();
        ICE_BLADE = Entry.of(
                        "ice_blade",
                        new IceBladeEnchantment()
                )
                .addEN()
                .addENDesc("Apply freezing effect to target. Higher levels have longer duration.")
                .addZH("冰刃")
                .addZHDesc("对敌人施加寒流效果。更高级的附魔延长效果时间")
                .register();
        SHARP_BLADE = Entry.of(
                        "sharp_blade",
                        new SharpBladeEnchantment()
                )
                .addEN()
                .addENDesc("Stack bleeding effect to target. Higher levels have higher stack cap.")
                .addZH("撕裂之刃")
                .addZHDesc("对敌人叠加流血效果。更高级的附魔增加叠加上限")
                .register();
        CURSE_BLADE = Entry.of(
                        "cursed_blade",
                        new CurseBladeEnchantment()
                )
                .addEN()
                .addENDesc("Apply cursed effect to target. Higher levels have longer duration.")
                .addZH("诅咒之刃")
                .addZHDesc("对敌人施加诅咒效果。更高级的附魔延长效果时间")
                .register();

        ICE_THORN = Entry.of(
                        "ice_thorn",
                        new IceThornEnchantment()
                )
                .addEN()
                .addENDesc("Apply freezing effect to attacker. Higher levels have longer duration.")
                .addZH("冰棘")
                .addZHDesc("对攻击者施加寒流效果。更高级的附魔延长效果时间")
                .register();
        FLAME_THORN = Entry.of(
                        "flame_thorn",
                        new FlameThornEnchantment()
                )
                .addEN()
                .addENDesc("Apply flame effect to attacker. Higher levels have higher damage.")
                .addZH("炎棘")
                .addZHDesc("对攻击者施加魂火效果。更高级的附魔造成对伤害更高")
                .register();
        VOID_TOUCH = Entry.of(
                        "void_touch",
                        new VoidTouchEnchantment()
                )
                .addEN()
                .addENDesc("Have a small chance to deal true damage. Chance increase significantly if the damage bypasses armor or magic already.")
                .addZH("虚空之触")
                .addZHDesc("小概率造成真伤。如果伤害本身穿透护甲或者魔法，大幅提高概率")
                .register();

        ENCH_PROJECTILE = Entry.of(
                        "projectile_reject",
                        new ImmuneEnchantment()
                )
                .addEN("Projectile Reject")
                .addENDesc("Deflects all projectiles. Make wearer immune to projectile damage.")
                .addZH("弹射物免疫")
                .addZHDesc("反射所有弹射物。使用者免疫弹射物伤害")
                .register();
        ENCH_FIRE = Entry.of(
                        "fire_reject",
                        new ImmuneEnchantment()
                )
                .addEN("Fire Immune")
                .addENDesc("Make wearer immune to fire damage.")
                .addZH("火焰免疫")
                .addZHDesc("使用者免疫火焰伤害")
                .register();
        ENCH_ENVIRONMENT = Entry.of(
                        "environment_reject",
                        new ImmuneEnchantment()
                )
                .addEN("Environmental Damage Immune")
                .addENDesc("Make wearer immune to damage without attacker.")
                .addZH("环境免疫")
                .addZHDesc("使用者免疫环境伤害")
                .register();
        ENCH_EXPLOSION = Entry.of(
                        "explosion_reject",
                        new ImmuneEnchantment()
                )
                .addEN("Explosion Immune")
                .addENDesc("Make wearer immune to explosion damage.")
                .addZH("爆炸免疫")
                .addZHDesc("使用者免疫爆炸伤害")
                .register();
        ENCH_MAGIC = Entry.of(
                        "magic_reject",
                        new ImmuneEnchantment()
                )
                .addEN("Magic Immune")
                .addENDesc("Make wearer immune to magic damage.")
                .addZH("魔法免疫")
                .addZHDesc("使用者免疫魔法伤害")
                .register();
        ENCH_INVINCIBLE = Entry.of(
                        "invincible",
                        new ImmuneEnchantment()
                )
                .addEN("Invincible (Creative)")
                .addENDesc("Player is invincible to all damage.")
                .addZH("伤害免疫(创造)")
                .addZHDesc("玩家免疫所有伤害")
                .register();
        ENCH_MATES = Entry.of(
                        "owner_protection",
                        new ImmuneEnchantment()
                )
                .addEN("Owner Protection")
                .addENDesc("Negate all damages from entities owned by you.")
                .addZH("召唤者保护")
                .addZHDesc("从属于你的生物无法对你造成伤害")
                .register();
    }

    static class Entry<T extends Enchantment> extends EnchantmentBuilder<T>
    {
        public static <T extends Enchantment> Entry<T> of(String name, T enchantment)
        {
            return new Entry<>(name, enchantment);
        }

        private Entry(String name, T enchantment)
        {
            super(name, enchantment);
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
