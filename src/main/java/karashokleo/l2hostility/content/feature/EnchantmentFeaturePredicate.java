package karashokleo.l2hostility.content.feature;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;

import java.util.function.Supplier;

public record EnchantmentFeaturePredicate(Supplier<Enchantment> e) implements FeaturePredicate
{
    @Override
    public boolean test(LivingEntity living)
    {
        return EnchantmentHelper.getEquipmentLevel(e.get(), living) > 0;
    }
}
