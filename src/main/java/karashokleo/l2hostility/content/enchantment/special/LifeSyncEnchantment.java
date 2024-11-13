package karashokleo.l2hostility.content.enchantment.special;

import karashokleo.l2hostility.content.enchantment.core.SingleLevelEnchantment;
import karashokleo.l2hostility.init.LHDamageTypes;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class LifeSyncEnchantment extends SingleLevelEnchantment
{
    public LifeSyncEnchantment()
    {
        super(Rarity.VERY_RARE, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }

    public static DamageSource getSource(World world)
    {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(LHDamageTypes.LIFE_SYNC));
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.LIGHT_PURPLE;
    }
}
