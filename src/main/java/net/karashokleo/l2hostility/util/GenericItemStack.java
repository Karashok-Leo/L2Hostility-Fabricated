package net.karashokleo.l2hostility.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public record GenericItemStack<T extends Item>(T item, ItemStack stack)
{
    @SuppressWarnings({"unchecked"})
    public static <T extends Item> GenericItemStack<T> of(ItemStack stack)
    {
        return new GenericItemStack<>((T) stack.getItem(), stack);
    }

    public static <T extends Item> GenericItemStack<T> from(T item)
    {
        return new GenericItemStack<>(item, item.getDefaultStack());
    }
}
