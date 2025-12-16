package karashokleo.l2hostility.content.enchantment.core;

import io.github.fabricators_of_create.porting_lib.entity.PartEntity;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class BattleEnchantment extends UnobtainableEnchantment
{
    public BattleEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Nullable
    protected LivingEntity getTarget(Entity target)
    {
        if (target instanceof LivingEntity le)
        {
            return le;
        }
        if (target instanceof PartEntity<?> part)
        {
            if (part.getParent() == target)
            {
                return null;
            }
            return getTarget(part.getParent());
        }
        return null;
    }

    @Override
    public int getMinLevel()
    {
        return 1;
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.GOLD;
    }
}
