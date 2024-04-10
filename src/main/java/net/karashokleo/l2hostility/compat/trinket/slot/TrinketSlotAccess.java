package net.karashokleo.l2hostility.compat.trinket.slot;

import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record TrinketSlotAccess(LivingEntity le, String group, String name, int slot) implements EntitySlotAccess
{
    @Nullable
    public TrinketInventory getInventory()
    {
        return TrinketsApi.getTrinketComponent(le).map(trinketComponent -> trinketComponent.getInventory().get(group).get(name)).orElse(null);
    }

    @Override
    public ItemStack get()
    {
        var opt = TrinketsApi.getTrinketComponent(le);
        return opt.isEmpty() ? ItemStack.EMPTY : opt.get().getInventory().get(group).get(name).getStack(slot);
    }

    @Override
    public void set(ItemStack stack)
    {
        var opt = TrinketsApi.getTrinketComponent(le);
        if (opt.isEmpty()) return;
        opt.get().getInventory().get(group).get(name).setStack(slot, stack);
    }

    @Override
    public String getID()
    {
        return "curios/" + group + "/" + name + "/" + slot;
    }
}
