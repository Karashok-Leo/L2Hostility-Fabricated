package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public enum LHAdvTexts
{
    ABRAHADABRA(
            "The Finale", "Obtain Abrahadabra",
            "终幕", "获得扭曲之魂"
    ),
    BREED(
            "Breeding Mobs", "Use a trait item on mobs",
            "养虎为患", "使用词条物品赋予怪物词条"
    ),
    DETECTOR(
            "Safety Compass", "Obtain Hostility Detector",
            "安全指南针", "获得恶意侦测器"
    ),
    EFFECT_KILL_ADAPTIVE(
            "Prevent Adaption", "Use poison/wither/soul flame effect or fire on mobs with Adaptive and kill it",
            "适应抑制", "使用剧毒/凋零/燃魂药水效果或者燃烧来对抗适应词条的怪物并将其击杀"
    ),
    EFFECT_KILL_REGEN(
            "Prevent Healing", "Use curse effect on mobs with Regeneration and kill it",
            "再生抑制", "使用诅咒药水效果来对抗再生词条的怪物并将其击杀"
    ),
    EFFECT_KILL_TELEPORT(
            "Prevent Teleporting", "Use incarceration effect on mobs with Teleport and kill it",
            "传送抑制", "使用禁锢药水效果来对抗传送词条的怪物并将其击杀"
    ),
    EFFECT_KILL_UNDEAD(
            "Prevent Reviving", "Use curse effect on mobs with Undying and kill it",
            "复活抑制", "使用诅咒药水效果来对抗不死词条的怪物并将其击杀"
    ),
    ENVY(
            "I want that!", "Obtain Curse of Envy",
            "我也想要", "获得嫉妒诅咒"
    ),
    GLASSES(
            "The Invisible Threats", "Obtain Detector Glasses to find out invisible mobs",
            "看不见的威胁", "获得侦测眼镜用于发现隐身的敌人"
    ),
    GLUTTONY(
            "Hostility Unlimited", "Obtain Curse of Gluttony",
            "无尽恶意", "获得暴食诅咒"
    ),
    GREED(
            "The More the Better", "Obtain Curse of Greed",
            "多多益善", "获得贪婪诅咒"
    ),
    IMAGINE_BREAKER(
            "Reality Breakthrough", "Obtain Imagine Breaker",
            "扭曲粉碎者", "获得破咒遗骨"
    ),
    INGOT(
            "Pandora's Box", "Obtain a Chaos Ingot",
            "潘多拉魔盒", "获得混沌锭"
    ),
    KILL_10_TRAITS(
            "Legend Slayer", "Kill a mob with 10 traits",
            "传奇杀戮者", "击杀拥有10个词条的怪物"
    ),
    KILL_5_TRAITS(
            "Legendary Battle", "Kill a mob with 5 traits",
            "传说之战", "击杀拥有5个词条的怪物"
    ),
    KILL_ADAPTIVE(
            "Counter-Defensive Measure", "Kill a mob with Protection, Regeneration, Tanky, and Adaptive Trait",
            "破防专家", "击杀保护+重装+再生+适应词条的怪物"
    ),
    KILL_DEMENTOR(
            "Immunity Invalidator", "Kill a mob with Dementor and Dispell Trait",
            "免疫破除", "击杀破魔+摄魂词条的怪物"
    ),
    KILL_FIRST(
            "Worthy Opponent", "Kill a mob with traits",
            "不可忽视的对手", "击杀一个有词条的怪物"
    ),
    KILL_RAGNAROK(
            "The Final Battle", "Kill a mob with Killer Aura and Ragnarok Trait",
            "最终决战", "击杀杀戮光环+诸神黄昏词条的怪物"
    ),
    KILL_TANKY(
            "Can Opener", "Kill a mob with Protection and Tanky Trait",
            "开罐头", "击杀保护+重装词条的怪物"
    ),
    LUST(
            "Naked Corpse", "Obtain Curse of Lust",
            "赤裸尸体", "获得色欲诅咒"
    ),
    MIRACLE(
            "Miracle of the World", "Obtain Miracle Ingot",
            "世界的奇迹", "获得奇迹锭"
    ),
    PATCHOULI(
            "Intro to L2Hostility", "Read the hostility guide",
            "恶意教程：入门到入土", "阅读恶意教程"
    ),
    PRIDE(
            "King of Hostility", "Obtain Curse of Pride",
            "恶意之王", "获得傲慢诅咒"
    ),
    ROOT(
            "Welcome to L2Hostility", "Your survival guide",
            "感谢使用莱特兰恶意", "你的生存指南"
    ),
    SLOTH(
            "I want a break", "Obtain Curse of Sloth",
            "我想休息一下", "获得怠惰诅咒"
    ),
    TRAIT(
            "Gate to the New World", "Obtain a trait item",
            "新世界的大门", "获得一个词条物品"
    ),
    WRATH(
            "Revenge Time", "Obtain Curse of Wrath",
            "复仇时刻", "获得暴怒诅咒"
    ),
    ;

    public final String id, titleEn, descEn, titleZh, descZh;

    LHAdvTexts(String titleEn, String descEn, String titleZh, String descZh)
    {
        this.id = this.name().toLowerCase();
        this.titleEn = titleEn;
        this.descEn = descEn;
        this.titleZh = titleZh;
        this.descZh = descZh;
    }

    public static void init()
    {
        for (LHAdvTexts text : LHAdvTexts.values())
        {
            LHGenerators.EN_TEXTS.addText(text.getTitleKey(), text.titleEn);
            LHGenerators.EN_TEXTS.addText(text.getDescKey(), text.descEn);
            LHGenerators.ZH_TEXTS.addText(text.getTitleKey(), text.titleZh);
            LHGenerators.ZH_TEXTS.addText(text.getDescKey(), text.descZh);
        }
    }

    public MutableText getTitle()
    {
        return Text.translatable(getTitleKey());
    }

    public MutableText getDesc()
    {
        return Text.translatable(getDescKey());
    }

    public String getTitleKey()
    {
        return "advancements." + L2Hostility.MOD_ID + "." + id + ".title";
    }

    public String getDescKey()
    {
        return "advancements." + L2Hostility.MOD_ID + "." + id + ".description";
    }
}
