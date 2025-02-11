package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum LHCplTexts
{
    WIND_BOTTLE("tooltip.misc.wind_bottle", "Used to obtain Captured Wind or Captured Bullet.", "获取千风精华和瓶中潜影弹的基础物品", 0),
    VOID_EYE("tooltip.misc.void_eye", "Obtained by killing an angry Enderman %s block below the void. This item kill holder in void, collect it with care.", "杀死处于虚空%s格以下的被激怒的末影人获得。此物品会杀死虚空以下的持有者，请小心收集", 1),
    SUN_MEMBRANE("tooltip.misc.sun_membrane", "Obtained by killing a sun-burning Phantom %s blocks above max build height.", "在世界建筑上限的%s格以上杀死被晒伤的幻翼获得", 1),
    SOUL_FLAME("tooltip.misc.soul_flame", "Obtained by killing a ghast with soul flame.", "用魂火烧死恶魂获得", 0),
    CAPTURED_WIND("tooltip.misc.captured_wind", "Obtained by moving faster than %s blocks per second while having Wind Bottle in hand or inventory.", "玩家移动速度超过%s格每秒时持有/背包内的捕风瓶转化获得", 1),
    CAPTURED_BULLET("tooltip.misc.captured_shulker_bullet", "Obtained by right clicking shulker bullet with Wind Bottle.", "通过拿捕风瓶右键潜影贝的子弹获得", 0),
    EXPLOSION_SHARD("tooltip.misc.explosion_shard", "Obtained by surviving an explosion damage of at least %s.", "承受%s的爆炸伤害并存活获得", 1),
    HARD_ICE("tooltip.misc.hard_ice", "Obtained by killing a Drowned with Powdered Snow.", "用细雪冻死溺尸获得", 0),
    STORM_CORE("tooltip.misc.storm_core", "Obtained by killing a Phantom with explosion.", "炸死幻翼获得", 0),
    BLACKSTONE_CORE("tooltip.misc.blackstone_core", "Obtained by killing a Piglin Brute that has Stone Cage effect.", "通过杀死处于禁锢效果下的猪灵蛮兵获得", 0),
    RESONANT_FEATHER("tooltip.misc.resonant_feather", "Obtained when a chicken survives a sonic boom attack.", "鸡受到音爆攻击而存活时掉落", 0),
    SPACE_SHARD("tooltip.misc.space_shard", "Obtained by causing a projectile damage of at least %s.", "造成%s的弓箭伤害获得", 1),
    FORCE_FIELD("tooltip.misc.force_field", "Obtained by killing a wither with arrow.", "通过弹射物伤害杀死凋零获得", 0),
    WARDEN_BONE_SHARD("tooltip.misc.warden_bone_shard", "Dropped when Warden is killed by player.", "玩家亲自击杀监守者时掉落", 0),
    GUARDIAN_EYE("tooltip.misc.guardian_eye", "Dropped when Elder Guardian is killed by lightning strike.", "用雷击杀死远古守卫者获得", 0),
    GUARDIAN_RUNE("tooltip.misc.guardian_rune", "Right click guardian to turn it into an elder guardian.", "对守卫者右键，使守卫者转化为远古守卫者", 0),
    PIGLIN_RUNE("tooltip.misc.piglin_rune", "Right click piglin to turn it into a piglin brute.", "对猪灵右键，使猪灵转化为猪灵蛮兵", 0),

    BURNT_TITLE("jei.burnt.title", "Burning", "焚烧转化", 0),
    BURNT_COUNT("jei.burnt.count", "One in %s chance of conversion", "1/%s的概率转化", 1),

    FLOAT("tooltip.misc.float", "This item will float in the air.", "这个物品会悬浮在空中。", 0),
    BANNED_ENCH("tooltip.misc.banned_ench", "Disabled", "已禁用", 0),

    WARP_RECORD("tooltip.misc.warp_record", "Right click to record position. After that, right click to teleport. Durability: %s", "右键记录坐标，再次右键传送。耐久：%s", 1),
    WARP_TELEPORT("tooltip.misc.warp_teleport", "Right click to teleport. Durability: %s", "右键传送。剩余耐久：%s", 1),
    WARP_POS("tooltip.misc.warp_pos", "Target: %s, (%s,%s,%s)", "目标: %s, (%s,%s,%s)", 4),
    TOTEM_DREAM("tooltip.misc.totem_dream", "Return players back to home when triggers, and becomes a fragile warp stone to go back. Valid against void damage. Also heal player to full health.", "激活时让玩家回到出生点，同时变成一个绑定了激活地点的易碎传送石。对虚空伤害也有效。回复全部血量。", 0),
    TOTEM_SEA("tooltip.misc.totem_sea", "It's stackable, but can only be triggered when in water or rain.", "可堆叠，但能只在水中/雨中激活。", 0),

    CHARGE_THROW("tooltip.misc.charge_throw", "Right click to throw at target", "右键朝目标丢出", 0),
    EFFECT_CHARGE("tooltip.misc.effect_charge", "Apply on Hit: %s", "击中目标时施加: %s", 1),
    EXPLOSION_CHARGE("tooltip.misc.explosion_charge", "Create explosion of level %s on Hit", "造成等级%s的爆炸", 1),

    POSEIDITE_TOOL("tooltip.tool.poseidite_tool", "Sharper and faster when user is in rain or water. Effective against water based mobs and mobs sensitive to water.", "在水/雨中时提升伤害和攻击速度。对水生生物和怕水生物造成更高伤害", 0),
    POSEIDITE_ARMOR("tooltip.tool.poseidite_armor", "When user is in rain or water: provides extra protection, walk/swim speed boost, and conduit/dolphin grace effect.", "当使用者在水/雨中时，提供额外保护，提升移动/游泳速度，提供潮涌/海豚祝福效果", 0),
    SCULKIUM_TOOL("tooltip.tool.sculkium_tool", "Breaks all breakable blocks for the same speed. Be aware of the breaking level.", "快速破坏任意可破坏方块。请注意挖掘等级", 0),
    SCULKIUM_ARMOR("tooltip.tool.sculkium_armor", "Dampened: When wearing 4 pieces of armors with dampened effect, cancel all vibrations emitted by wearer.", "沉默:穿着4件带有沉默效果的装备时，玩家发出的任何震动都会被抵消", 0),
    SHULKERATE_TOOL("tooltip.tool.shulkerate_tool", "Really durable. Not easily damaged. Increase Reach and Attack distance", "极高耐久，不易损坏。略微提升触及距离和攻击距离", 0),
    SHULKERATE_ARMOR("tooltip.tool.shulkerate_armor", "Really durable. Not easily damaged.", "极高耐久，不易损坏", 0),
    TOTEMIC_TOOL("tooltip.tool.totemic_tool", "Heal user when used. Effective against undead mobs.", "消耗耐久时治疗使用者。对亡灵生物造成更高伤害。", 0),
    TOTEMIC_ARMOR("tooltip.tool.totemic_armor", "Heal user when damaged. Regenerate health over time.", "消耗耐久时治疗使用者，缓慢回复生命", 0),
    ;

    final String id, defEn, defZh;
    final int count;

    LHCplTexts(String id, String defEn, String defZh, int count)
    {
        this.id = id;
        this.defEn = defEn;
        this.defZh = defZh;
        this.count = count;
    }

    public static void init()
    {
        for (LHCplTexts text : LHCplTexts.values())
        {
            LHGenerators.EN_TEXTS.addText(L2Hostility.MOD_ID + "." + text.id, text.defEn);
            LHGenerators.ZH_TEXTS.addText(L2Hostility.MOD_ID + "." + text.id, text.defZh);
        }
    }

    public MutableText get(Object... objs)
    {
        if (objs.length != count)
            throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
        return Text.translatable(L2Hostility.MOD_ID + "." + id, objs);
    }
}
