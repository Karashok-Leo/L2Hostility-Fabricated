package karashokleo.l2hostility.content.screen.equipment;

import dev.xkmc.l2tabs.lib.menu.BaseInventoryScreenHandler;
import net.minecraft.item.ItemStack;

public class EquipmentInventory extends BaseInventoryScreenHandler.BaseInventory<EquipmentScreenHandler>
{
    public EquipmentInventory(EquipmentScreenHandler menu)
    {
        super(0, menu);
    }

    @Override
    public ItemStack getStack(int slot)
    {
        if (parent.mob == null) return ItemStack.EMPTY;
        return parent.mob.getEquippedStack(EquipmentScreenHandler.SLOTS[slot]);
    }

    @Override
    public void setStack(int slot, ItemStack stack)
    {
        if (parent.mob == null) return;
        parent.mob.equipStack(EquipmentScreenHandler.SLOTS[slot], stack);

    }

    @Override
    public ItemStack removeStack(int slot, int amount)
    {
        if (parent.mob == null) return ItemStack.EMPTY;
        return parent.mob.getEquippedStack(EquipmentScreenHandler.SLOTS[slot]).split(amount);
    }
}
