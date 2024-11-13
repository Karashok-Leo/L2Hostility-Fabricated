package karashokleo.l2hostility.content.feature;

import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public enum EntityFeature
{
    STABLE_BODY(new SlotEnchantmentFeaturePredicate(EquipmentSlot.CHEST, () -> LHEnchantments.STABLE_BODY)),
    PROJECTILE_REJECT(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_PROJECTILE)),
    EXPLOSION_REJECT(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_EXPLOSION)),
    FIRE_REJECT(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_FIRE)),
    ENVIRONMENTAL_REJECT(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_ENVIRONMENT)),
    MAGIC_REJECT(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_MAGIC)),
    OWNER_PROTECTION(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_MATES)),
    INVINCIBLE(new EnchantmentFeaturePredicate(() -> LHEnchantments.ENCH_INVINCIBLE)),
//    SNOW_WALKER(new EnchantmentFeaturePredicate(() -> LHEnchantments.SNOW_WALKER)),
//    LAVA_WALKER(List.of()),
    ;

    private final ArrayList<FeaturePredicate> list;

    EntityFeature(FeaturePredicate predicate)
    {
        this(List.of(predicate));
    }

    EntityFeature(List<FeaturePredicate> list)
    {
        this.list = new ArrayList<>(list);
    }

    public void add(FeaturePredicate pred)
    {
        list.add(pred);
    }

    public boolean test(LivingEntity e)
    {
        return list.stream().anyMatch(x -> x.test(e));
    }
}
