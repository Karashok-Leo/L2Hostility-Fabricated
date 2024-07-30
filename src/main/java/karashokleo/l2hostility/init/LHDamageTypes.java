package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.damage.DamageEffects;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

public class LHDamageTypes
{
    public static RegistryKey<DamageType> KILLER_AURA = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("killer_aura"));
    public static RegistryKey<DamageType> EMERALD = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("emerald"));
    public static RegistryKey<DamageType> SOUL_FLAME = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("soul_flame"));
    public static RegistryKey<DamageType> BLEED = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("bleed"));
    public static RegistryKey<DamageType> LIFE_SYNC = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("life_sync"));
    public static RegistryKey<DamageType> VOID_EYE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("void_eye"));

    public static void register()
    {
        register(
                KILLER_AURA,
                new DamageType("killer_aura", DamageScaling.NEVER, 0.1F),
                "%s was killed by killer aura",
                "%s was killed by %s's killer aura",
                "%s被杀戮光环杀死了",
                "%s被%s被杀戮光环杀死了",
                DamageTypeTags.BYPASSES_ARMOR,
                LHTags.MAGIC
        );
        register(
                EMERALD,
                new DamageType("emerald", DamageScaling.NEVER, 0.1F),
                "%s was killed by emerald splash",
                "%s was killed by emerald splash by %s",
                "%s被绿宝石水花杀死了",
                "%s被%s的绿宝石水花杀死了",
                DamageTypeTags.AVOIDS_GUARDIAN_THORNS
        );
        register(
                SOUL_FLAME,
                new DamageType("soul_flame", DamageScaling.NEVER, 0F, DamageEffects.BURNING),
                "%s has its soul burnt out",
                "%s has its soul burnt out by %s",
                "%s的灵魂燃尽了",
                "%s的灵魂被%s烧掉了",
                DamageTypeTags.BYPASSES_ARMOR,
                DamageTypeTags.AVOIDS_GUARDIAN_THORNS,
                DamageTypeTags.NO_IMPACT,
                LHTags.MAGIC
        );
        register(
                BLEED,
                new DamageType("bleed", DamageScaling.NEVER, 0.1F),
                "%s bleed to death",
                "%s bleed to death",
                "%s流血致死",
                "%s流血致死",
                DamageTypeTags.BYPASSES_ARMOR,
                DamageTypeTags.BYPASSES_ENCHANTMENTS,
                DamageTypeTags.BYPASSES_RESISTANCE,
                DamageTypeTags.BYPASSES_EFFECTS,
                DamageTypeTags.NO_IMPACT
        );
        register(
                LIFE_SYNC,
                new DamageType("life_sync", DamageScaling.NEVER, 0F),
                "%s was drained",
                "%s was drained",
                "%s被榨干了",
                "%s被榨干了",
                DamageTypeTags.BYPASSES_ARMOR,
                DamageTypeTags.BYPASSES_ENCHANTMENTS,
                DamageTypeTags.BYPASSES_RESISTANCE,
                DamageTypeTags.BYPASSES_EFFECTS,
                DamageTypeTags.NO_IMPACT
        );
        register(
                VOID_EYE,
                new DamageType("void_eye", DamageScaling.NEVER, 0F),
                "%s was cursed by void eye",
                "%s was cursed by void eye",
                "%s死于虚空之眼的诅咒",
                "%s死于虚空之眼的诅咒",
                DamageTypeTags.BYPASSES_ARMOR,
                DamageTypeTags.BYPASSES_ENCHANTMENTS,
                DamageTypeTags.BYPASSES_RESISTANCE,
                DamageTypeTags.BYPASSES_INVULNERABILITY,
                DamageTypeTags.BYPASSES_EFFECTS,
                DamageTypeTags.NO_IMPACT
        );
    }

    @SafeVarargs
    public static void register(RegistryKey<DamageType> registryKey, DamageType damageType, String deathMsgEn, String deathMsgPlayerEn, String deathMsgZh, String deathMsgPlayerZh, TagKey<DamageType>... tags)
    {
        LHData.DYNAMICS.add(registryKey, damageType);
        String deathMsg = "death.attack." + damageType.msgId();
        String deathMsgPlayer = "death.attack." + damageType.msgId() + ".player";
        LHData.EN_TEXTS.addText(deathMsg, deathMsgEn);
        LHData.EN_TEXTS.addText(deathMsgPlayer, deathMsgPlayerEn);
        LHData.ZH_TEXTS.addText(deathMsg, deathMsgZh);
        LHData.ZH_TEXTS.addText(deathMsgPlayer, deathMsgPlayerZh);
        for (TagKey<DamageType> tag : tags)
            LHData.DAMAGE_TYPE_TAGS.add(tag, registryKey);
    }
}
