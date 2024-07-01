package karashokleo.l2hostility.compat.trinket.slot;

import net.minecraft.item.ItemStack;

import java.util.function.Function;

public interface EntitySlotAccess
{
    ItemStack get();

    void set(ItemStack stack);

    default void modify(Function<ItemStack, ItemStack> func)
    {
        set(func.apply(get()));
    }

    String getID();
}
