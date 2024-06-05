package net.karashokleo.l2hostility.compat.loot;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public interface ITraitLootRecipe
{
    List<ITraitLootRecipe> LIST_CACHE = new ArrayList<>();

    List<ItemStack> getTraits();

    List<ItemStack> getTrinketsRequired();

    ItemStack getLoot();

    void addTooltip(List<Text> tooltip);
}
