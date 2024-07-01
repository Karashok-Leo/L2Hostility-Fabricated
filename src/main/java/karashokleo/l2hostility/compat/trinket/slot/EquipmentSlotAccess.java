package karashokleo.l2hostility.compat.trinket.slot;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public record EquipmentSlotAccess(LivingEntity le, EquipmentSlot slot) implements EntitySlotAccess
{
    @Override
    public ItemStack get()
    {
        return le.getEquippedStack(slot);
    }

    @Override
    public void set(ItemStack stack)
    {
        le.equipStack(slot, stack);
    }

    @Override
    public String getID()
    {
        return "equipment/" + slot.getName();
    }
}
