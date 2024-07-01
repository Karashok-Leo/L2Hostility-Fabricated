package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.MiscItems;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum LHTexts
{
    SHIFT("tooltip.shift", "Press SHIFT to show spawn condition", "按SHIFT显示生成要求", 0),
    LV("tooltip.lv", "Lv.%s", "Lv.%s", 1),
    BANNED("tooltip.misc.banned", "This item is disabled.", "这个物品被禁用了", 0),
    TOOLTIP_DISABLE("tooltip.disable", "%s enchantments disabled for %ss. Any new enchantment will vanish.", "%s个附魔被抑制了，剩余%s秒。任何新附魔将会被抹消", 2),
    TOOLTIP_SELF_EFFECT("tooltip.self_effect", "Mob gains continuous effect: ", "怪物获得常驻效果：", 0),
    TOOLTIP_TARGET_EFFECT("tooltip.target_effect", "Mob inflict effect on hit: ", "怪物攻击将会施加：", 0),
    TOOLTIP_LEGENDARY("tooltip.legendary", "Legendary", "传奇词条", 0),
    TOOLTIP_MIN_LEVEL("tooltip.min_level", "Minimum mob level: %s", "最低怪物等级: %s", 1),
    TOOLTIP_LEVEL_COST("tooltip.level_cost", "Mob level cost: %s", "怪物等级消耗: %s", 1),
    TOOLTIP_WEIGHT("tooltip.weight", "Weight: %s", "权重: %s", 1),
    TOOLTIP_BANNED("tooltip.banned", "This trait is disabled.", "这个词条被禁用了", 0),
    TOOLTIP_SEAL_DATA("tooltip.sealed_item", "Item sealed within:", "被封印的物品：", 0),
    TOOLTIP_SEAL_TIME("tooltip.seal_time", "Hold use to unseal. Takes %ss.", "持续使用%s秒来破除封印", 1),
    TOOLTIP_WITCH_CHARGE("tooltip.witch_charge", "Right click to fire. When hit entity, increase duration of all harmful effects longer than %ss by %s%%. At most increase to %ss.", "右键发射。击中的目标的所有超过%s秒的负面效果时间增加%s%%。最多增加至%s秒", 3),
    TOOLTIP_WITCH_ETERNAL("tooltip.witch_eternal", "Right click to fire. When hit entity, all harmful effects longer than %ss becomes infinite duration.", "右键发射。击中的目标的所有超过%s秒的负面效果时间变为无限", 1),
    TOOLTIP_WITCH_BOTTLE("tooltip.witch_bottle", "Increase duration of all effects longer than %ss by %s%%. At most increase to %ss.", "所有超过%s秒的药水效果时间增加%s%%。最多增加至%s秒", 3),
    ITEM_WAND_AI("item.wand.ai", "Right click to remove or restore mob AI.", "右键怪物移除或者恢复怪物AI", 0),
    ITEM_WAND_EQUIPMENT("item.wand.equipment", "Right click to open mob equipment menu. Shift right click to open mob curios menu.", "右键怪物打开怪物装备栏，潜行右键打开怪物饰品栏", 0),
    ITEM_WAND_TARGET("item.wand.target", "Right click 2 mobs to make them fight each other.", "右键2个怪物让它们互相攻击", 0),
    ITEM_WAND_ADDER("item.wand.adder", "Right click blocks to select trait. Right click mobs to select trait rank. Press shift to select in opposite direction.", "右键方块选择词条。右键生物调整词条等级。按住Shift切换选择方向", 0),
    ITEM_GLASSES("item.equipment.glasses", "Allow you to see invisible mobs, and see mobs when you have blindness or darkness effects", "让你能看见隐身怪物，以及在失明/黑暗效果下看见怪物", 0),
    ITEM_DETECTOR("item.equipment.detector", "Shows player and regional difficulty information when held in hand / offhand", "在主副手持有时显示玩家和区域难度信息", 0),
    ITEM_SECTION_RENDER("item.equipment.section_render", "Hold [%s] and Equip [%s] to see regions cleared of hostility", "持有[%s]且佩戴[%s]时渲染被净化的区域", 2),
    ITEM_SPAWNER("item.spawner", "Summon strong mobs. Kill them all to make a chunk section no longer spawn mobs with levels", "召唤强力怪物。击败它们能让当前子区块不再生成有等级的怪物", 0),
    ITEM_ORB("item.consumable.orb", "Make %sx%sx%s chunk sections no longer spawn mobs with levels.", "让%sx%sx%s的子区块不再生成有等级的怪物", 3),
    ITEM_BOTTLE_CURSE("item.consumable.bottle_of_curse", "Increase player difficulty by %s", "玩家难度等级提升%s", 1),
    ITEM_BOTTLE_SANITY("item.consumable.bottle_of_sanity", "Clear all base player difficulty", "清空玩家基础难度等级", 0),
    ITEM_BOOK_COPY("item.book.copy", "Merge it with enchanted book in anvil to copy.", "在铁砧中和附魔书结合来复印", 0),
    ITEM_BOOK_EVERYTHING_SHIFT("item.book.everything_shift", "Shift right click with it to turn it into a book with all enchantments obtainable from enchantment table.", "手持这个物品潜行右键把这个物品变成包含所有可以从附魔台中产出的附魔的附魔书", 0),
    ITEM_BOOK_EVERYTHING_USE("item.book.everything_use", "Rename this item with ID of an enchantment obtainable from enchantment table, then right click to generate a book of that enchantment, costing player experience.", "把这个物品重命名成可以从附魔台中产出的附魔的ID，然后手持右键消耗经验产出该附魔", 0),
    ITEM_BOOK_EVERYTHING_INVALID("item.book.everything_invalid", "Name is not ID of an enchantment", "当前物品名字不是附魔ID", 0),
    ITEM_BOOK_EVERYTHING_READY("item.book.everything_ready", "Enchantment %s cost %s levels", "产出%s消耗%s级经验", 2),
    ITEM_BOOK_EVERYTHING_FORBIDDEN("item.book.everything_forbidden", "%s is not obtainable", "%s不可获取", 1),
    ITEM_CHARM_ENVY("item.equipment.curse_of_envy", "Get trait items when you kill mobs with traits, with a chance of %s%% per trait rank", "击杀怪物获取其词条，概率为词条每阶%s%%", 1),
    ITEM_CHARM_GLUTTONY("item.equipment.curse_of_gluttony", "Get Bottle of Curse when you kill mobs with level, with a chance of %s%% per level", "击杀怪物掉落瓶装恶意，概率为每级%s%%", 1),
    ITEM_CHARM_GREED("item.equipment.curse_of_greed", "x%s hostility loot drop chance", "击杀怪物的词条掉落物掉落概率x%s", 1),
    ITEM_CHARM_LUST("item.equipment.curse_of_lust", "Mobs you kill will drop all equipments", "击杀怪物掉落所有装备", 0),
    ITEM_CHARM_PRIDE("item.equipment.curse_of_pride", "Gain %s%% health and %s%% attack damage per difficulty level", "根据玩家难度每级获得%s%%血量上限和%s%%伤害提升", 2),
    ITEM_CHARM_SLOTH("item.equipment.curse_of_sloth", "You will not gain difficulty by killing mobs", "击杀生物不再提升难度", 0),
    ITEM_CHARM_WRATH("item.equipment.curse_of_wrath", "Gain %s%% attack damage per difficulty level difference against mobs with higher level than you.", "攻击等级比自己高的怪物时每级等级差伤害提升%s%%", 1),
    ITEM_CHARM_ADD_LEVEL("item.equipment.curse_add_level", "Gain %s extra difficulty level", "玩家难度提升%s", 1),
    ITEM_CHARM_NO_DROP("item.equipment.curse_no_drop", "Mobs you kill will not drop hostility loot", "玩家击杀的怪物不会掉落词条掉落物", 0),
    ITEM_CHARM_TRAIT_CHEAP("item.equipment.curse_trait_cheap", "Mob traits will be +%s%% more frequent", "怪物词条总量+%s%%", 1),
    ITEM_FLAME_THORN("item.equipment.flame_thorn", "When you damage a mob, inflict Soul Flame with level equals to total number of effects that mob has, for %ss", "当你对一个怪物造成伤害时，对其施加%s秒，等级等同其目前持有药水效果种类的魂火效果", 1),
    ITEM_IMAGINE_BREAKER("item.equipment.imagine_breaker", "All your melee damage bypass magical protection.", "你所有的近战伤害穿透魔法防御", 0),
    ITEM_PLATINUM_STAR("item.equipment.platinum_star", "All your melee damage bypass damage cool down.", "你所有的近战伤害无视伤害冷却", 0),
    ITEM_WITCH_WAND("item.equipment.witch_wand", "Throw random splash potions on right click. Types selected from random potion traits.", "投掷随机喷溅药水，种类从药水词条中随机选择", 0),
    ITEM_RING_OCEAN("item.equipment.ring_of_ocean", "You will always be wet", "玩家永远湿润", 0),
    ITEM_RING_LIFE("item.equipment.ring_of_life", "You will not lose more than %s%% of your max health at once", "玩家一次损失血量不会超过总血量的%s%%", 1),
    ITEM_RING_DIVINITY("item.equipment.ring_of_divinity", "Immune to magic damage. Gets permanent Cleanse effect", "玩家免疫魔法伤害，获得持续的净化效果", 0),
    ITEM_RING_REFLECTION("item.equipment.ring_of_reflection", "When a mob trait tries to apply a negative effect on you, apply it to surrounding enemies that target you instead.", "当一个怪物携带的词条试图对你施加药水效果时，将其施加在周围所有对你有敌意的生物上作为替代", 0),
    ITEM_RING_CORROSION("item.equipment.ring_of_corrosion", "When you deal damage, damage all their equipments by %s%% of max durability.", "造成伤害时，目标所有装备耐久下降总耐久的%s%%", 1),
    ITEM_RING_CORROSION_NEG("item.equipment.ring_of_corrosion_neg", "When you take damage, damage all your equipments by %s%% of max durability.", "受到伤害时，你所有装备耐久下降总耐久的%s%%", 1),
    ITEM_RING_INCARCERATION("item.equipment.ring_of_incarceration", "When sneaking, apply Incarceration effect on you and all mobs within your attack range.", "潜行时，对你和你攻击范围内所有生物施加禁锢", 0),
    ITEM_RING_HEALING("item.equipment.ring_of_healing", "Heals %s%% of max health every second.", "每秒回复总血量的%s%%", 1),
    ARMOR_IMMUNE("l2damagetracker.tooltip.tool.immune", "Immune to: ", "免疫: ", 0),
    ABRAHADABRA("item.equipment.abrahadabra", "When a mob trait tries to apply a trait effect on you, apply it to surrounding enemies that target you instead.", "当一个怪物携带的词条试图对你施加词条效果时，将其施加在周围所有对你有敌意的生物上作为替代", 0),
    NIDHOGGUR("item.equipment.nidhoggur", "Mobs you kill will drop +%s%% loot per mob level", "击杀怪物掉落的所有掉落物根据怪物等级每级增加+%s%%", 1),
    POCKET_OF_RESTORATION("item.equipment.pocket_of_restoration", "Automatically put sealed item inside, unseal it, and put it back when finished.", "自动收纳被封印的物品，将其解除封印后放回原位", 0),
    LOOTING_CHARM("item.equipment.looting", "Enables some hostility trait drops. Check REI/EMI for detail.", "击杀带部分词条的生物时掉落额外奖励。查看REI/EMI了解详情", 0),
    ABYSSAL_THORN("item.equipment.abyssal_thorn", "Mobs get all possible traits at their level, but with Curse of Envy, they will always drop trait symbols of those when killed", "怪物会获得当前等级下所有能获得的词条，但佩戴嫉妒诅咒时，必定掉落那些词条", 0),
    DIVINITY_CROSS("item.equipment.divinity_cross", "Cleanse effect will not clear Lv.1 beneficial effects", "净化效果不会清除1级的正面效果", 0),
    DIVINITY_LIGHT("item.equipment.divinity_light", "Your adaptive level will stay at 0.", "玩家动态等级维持在0", 0),
    MSG_AI("msg.ai", "Configure %s: Set NoAI to %s.", "配置实体%s：将NoAI设为%s", 2),
    MSG_SET_TARGET("msg.set_target", "Set %s and %s to fight", "让%s和%s对战", 2),
    MSG_TARGET_FAIL("msg.target_fail", "%s and %s cannot fight", "%s和%s无法对战", 2),
    MSG_TARGET_RECORD("msg.target_record", "Recorded %s", "选中实体%s", 1),
    MSG_SET_TRAIT("msg.set_trait", "Set trait %1$s on entity %2$s to level %3$s", "将实体%2$s的词条%1$s设为%3$s级", 3),
    MSG_SELECT_TRAIT("msg.select_trait", "Selected trait: %s", "词条：%s", 1),
    MSG_ERR_MAX("msg.err_max", "Trait level already reached max value", "该词条已达到最大值", 0),
    MSG_ERR_DISALLOW("msg.err_disallow", "Trait not applicable on this entity", "该词条不适用于当前生物", 0),
    INFO_PLAYER_LEVEL("info.player_level", "Player difficulty level: %s", "玩家难度等级：%s", 1),
    INFO_PLAYER_EXP("info.player_exp", "Difficulty progress: %s%%", "玩家难度等级进度：%s%%", 1),
    INFO_PLAYER_CAP("info.player_cap", "Mob trait rank limit: %s", "怪物词条阶级上限：%s", 1),
    INFO_CHUNK_LEVEL("info.chunk_level", "Chunk base difficulty: %s", "区块难度基础值：%s", 1),
    INFO_CHUNK_SCALE("info.chunk_scale", "Chunk difficulty scale: %s", "区块玩家难度倍率：%s", 1),
    INFO_CHUNK_CLEAR("info.clear", "Chunk Section difficulty cleared", "子区块难度已清除", 0),
    INFO_TAB_TITLE("info.title", "Difficulty Information", "难度信息", 0),
    INFO_REWARD("info.reward", "Obtained %s rewards", "已获得%s个奖励", 1),
    INFO_PLAYER_ADAPTIVE_LEVEL("info.player_detail.adaptive", "Adaptive level: %s", "动态等级: %s", 1),
    INFO_PLAYER_DIM_LEVEL("info.player_detail.dim", "Visited dimension bonus: %s", "到访维度数量加成: %s", 1),
    INFO_PLAYER_ITEM_LEVEL("info.player_detail.item", "Item bonus: %s", "物品加成: %s", 1),
    INFO_PLAYER_EXT_LEVEL("info.player_detail.external", "External difficulty: %s", "外部加成: %s", 1),
    INFO_SECTION_DIM_LEVEL("info.section_detail.dim", "Dimension level: %s", "维度基础等级: %s", 1),
    INFO_SECTION_BIOME_LEVEL("info.section_detail.biome", "Biome level: %s", "生物群系基础等级: %s", 1),
    INFO_SECTION_ADAPTIVE_LEVEL("info.section_detail.adaptive", "Adaptive level: %s", "动态等级: %s", 1),
    INFO_SECTION_DISTANCE_LEVEL("info.section_detail.distance", "Distance bonus: %s", "距离加成: %s", 1),
    BOSS_EVENT("boss_event", "Hostility Clearing Progress: %s/%s", "恶意清除进度：%s/%s", 2),
    LOOT_TITLE("compat.loot_title", "Trait Loot", "恶意词条掉落", 0),
    LOOT_CHANCE("compat.loot_chance", "%s chance for %s rank %s", "%3$s阶%2$s词条%1$s概率掉落", 3),
    LOOT_MIN_LEVEL("compat.min_level", "Drops on mobs with level %s or higher", "等级%s及以上的怪物才会掉落", 1),
    LOOT_MIN_HEALTH("compat.min_health", "Drops on mobs with %s or higher max health", "总血量在%s及以上的怪物才会掉落", 1),
    LOOT_NO_TRAIT("compat.no_trait", "%s chance to drop when conditions met", "击杀满足条件的怪物时有%s概率掉落", 1),
    LOOT_OTHER_TRAIT("compat.other_trait", "Requires %s at rank %s", "同时需要%2$s阶%1$s词条", 2),
    TOOLTIP_LOOT_ENVY("compat.envy", "Drops when you equips Curse of Envy while killing mobs of this trait", "装备嫉妒诅咒时击杀有词条的怪物有概率掉落对应词条", 0),
    TOOLTIP_LOOT_GLUTTONY("compat.gluttony", "Drops when you equips Curse of Gluttony while killing mobs with levels", "装备暴食诅咒时击杀有等级的怪物有概率掉落", 0),
    TOOLTIP_LOOT_REQUIRED("compat.required", "Requires you to equip %s while killing mobs", "需要佩戴[%s]饰品才能掉落", 1),
    COMMAND_PLAYER_SUCCEED("command.player.success", "Performed actions on %s players", "指令已对%s个玩家起效", 1),
    COMMAND_PLAYER_FAIL("command.player.fail", "Command has no target or no effect", "指令无目标或者无效", 0),
    COMMAND_PLAYER_GET_BASE("command.player.get_base", "%s has base difficulty level %s", "%s的基础难度等级位Lv.%s", 2),
    COMMAND_PLAYER_GET_TOTAL("command.player.get_total", "%s has overall difficulty level %s", "%s综合难度等级为Lv.%s", 2),
    COMMAND_PLAYER_GET_DIM("command.player.get_dim", "%s has visited %s dimensions", "%s造访过%s个维度", 2),
    COMMAND_PLAYER_GET_TRAIT_CAP("command.player.trait_cap", "The max rank %s has killed is rank %s", "%s击杀的最高阶级词条为阶级%s", 2),
    COMMAND_REGION_SUCCEED("command.region.success", "Action Succeed", "指令已生效", 0),
    COMMAND_REGION_LOCAL_OFF("command.region.local_off", "Section difficulty is turned off", "子区块难度积累已禁用", 0),
    COMMAND_REGION_COUNT("command.region.count", "Performed Actions for %s chunk sections", "指令已对%s个子区块起效", 1),
    COMMAND_REGION_GET_BASE("command.region.get_base", "Target section has base difficulty level %s", "目标子区块的累积难度为%s", 1),
    COMMAND_REGION_GET_TOTAL("command.region.get_total", "Target section has total difficulty level %s", "目标子区块的总难度为%s", 1),
    COMMAND_REGION_GET_SCALE("command.region.get_scale", "Target section has difficulty scale %s", "目标子区块的难度倍率为%s", 1),
    COMMAND_REGION_CLEAR("command.region.clear", "Section Cleared", "子区块难度已净化", 0),
    COMMAND_REGION_NOT_CLEAR("command.region.not_clear", "Section Not Cleared", "子区块难度还未净化", 0),

    COMMAND_MOB_SUCCEED("command.mob.success", "Performed actions on %s mobs", "指令已对%s个怪物起效", 1),
    //    PATCHOULI_TITLE("patchouli.title", "L2Hostility Guide", "莱特兰·恶意-教程与词条列表", 0),
//    PATCHOULI_LANDING("patchouli.landing", "Welcome to Champion-like difficulty scaling mod", "这个模组提升了怪物数值，并添加了各种能力", 0),
    PATCHOULI_TITLE("patchouli.title", "L2Hostility Guide", "莱特兰恶意教程", 0),
    PATCHOULI_LANDING("patchouli.landing", "Welcome to Champion-like difficulty scaling mod", "了解难度机制和怪物词条，以更好地应对它们", 0);

    public final String id, defEn, defZh;
    public final int count;

    LHTexts(String id, String defEn, String defZh, int count)
    {
        this.id = id;
        this.defEn = defEn;
        this.defZh = defZh;
        this.count = count;
    }

    public MutableText get(Object... objs)
    {
        if (objs.length != count)
            throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
        return Text.translatable(L2Hostility.MOD_ID + "." + id, objs);
    }

    public static MutableText item(ItemStack stack)
    {
        return stack.getName().copy().formatted(stack.getRarity().formatting);
    }

    public static MutableText sectionRender()
    {
        return ITEM_SECTION_RENDER.get(item(MiscItems.DETECTOR.getDefaultStack()), item(MiscItems.DETECTOR_GLASSES.getDefaultStack()));
    }

    public static void init()
    {
        for (LHTexts text : LHTexts.values())
        {
            LHData.EN_TEXTS.addText(L2Hostility.MOD_ID + "." + text.id, text.defEn);
            LHData.ZH_TEXTS.addText(L2Hostility.MOD_ID + "." + text.id, text.defZh);
        }
        LHData.EN_TEXTS.addText("config.jade.plugin_l2hostility.mob", "L2Hostility");
        LHData.ZH_TEXTS.addText("config.jade.plugin_l2hostility.mob", "莱特兰 - 恶意");

        LHData.EN_TEXTS.addText("trinkets.slot.chest.hostility_curse", "L2Hostility - Curse");
        LHData.ZH_TEXTS.addText("trinkets.slot.chest.hostility_curse", "恶意 - 诅咒");
        LHData.EN_TEXTS.addText("trinkets.slot.chest.charm", "Charm");
        LHData.ZH_TEXTS.addText("trinkets.slot.chest.charm", "护符");
    }
}