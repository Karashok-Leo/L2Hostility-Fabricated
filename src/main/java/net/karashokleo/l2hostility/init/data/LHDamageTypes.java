package net.karashokleo.l2hostility.init.data;

import net.karashokleo.l2hostility.L2Hostility;
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
