package net.karashokleo.l2hostility.init;

import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

public class LHDamageTypes
{
    public static RegistryKey<DamageType> KILLER_AURA = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id("killer_aura"));

    public static TagKey<DamageType> MAGIC = of("magic");

    private static TagKey<DamageType> of(String path)
    {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id(path));
    }

    public static void register()
    {
        register(
                KILLER_AURA,
                new DamageType("killer_aura", DamageScaling.NEVER, 0.1F),
                "%s was killed by killer aura",
                "%s was killed by %s's killer aura",
                "%s被杀戮光环杀死了",
                "%s被%s被杀戮光环杀死了"
        );
    }

    public static void register(RegistryKey<DamageType> registryKey, DamageType damageType, String deathMsgEn, String deathMsgPlayerEn, String deathMsgZh, String deathMsgPlayerZh)
    {
        LHData.DYNAMICS.add(entries -> entries.add(registryKey, damageType));
        String deathMsg = "death.attack." + damageType.msgId();
        String deathMsgPlayer = "death.attack." + damageType.msgId() + ".player";
        LHData.EN_TEXTS.addText(deathMsg, deathMsgEn);
        LHData.EN_TEXTS.addText(deathMsgPlayer, deathMsgPlayerEn);
        LHData.ZH_TEXTS.addText(deathMsg, deathMsgZh);
        LHData.ZH_TEXTS.addText(deathMsgPlayer, deathMsgPlayerZh);
    }

    public static boolean isMagic(DamageSource source)
    {
        return source.isIn(LHDamageTypes.MAGIC);
    }

    public static boolean bypasses(DamageSource source)
    {
        return source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
                source.isIn(DamageTypeTags.BYPASSES_EFFECTS);
    }
}
