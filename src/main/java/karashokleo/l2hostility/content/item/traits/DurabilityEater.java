package karashokleo.l2hostility.content.item.traits;

import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class DurabilityEater
{
    public static void corrosion(LivingEntity target, EquipmentSlot slot)
    {
        ItemStack stack = target.getEquippedStack(slot);
        if (!stack.isDamageable())
        {
            return;
        }
        double factor = LHConfig.common().traits.corrosionDurability;
        int add = (int) (stack.getDamage() * factor);
        if (add <= 0)
        {
            return;
        }
        stack.damage(add, target, e -> e.sendEquipmentBreakStatus(slot));
    }

    public static void erosion(LivingEntity target, EquipmentSlot slot)
    {
        ItemStack stack = target.getEquippedStack(slot);
        if (!stack.isDamageable())
        {
            return;
        }
        double factor = LHConfig.common().traits.erosionDurability;
        int add = (int) ((stack.getMaxDamage() - stack.getDamage()) * factor);
        if (add <= 0)
        {
            return;
        }
        stack.damage(add, target, e -> e.sendEquipmentBreakStatus(slot));
    }

    public static void flat(LivingEntity target, EquipmentSlot slot, double factor)
    {
        ItemStack stack = target.getEquippedStack(slot);
        if (!stack.isDamageable())
        {
            return;
        }
        int add = (int) (stack.getMaxDamage() * factor);
        if (add <= 0)
        {
            return;
        }
        stack.damage(add, target, e -> e.sendEquipmentBreakStatus(slot));
    }
}
