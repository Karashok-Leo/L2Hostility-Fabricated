package net.karashokleo.l2hostility.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.karashokleo.l2hostility.content.recipe.BurntRecipe;
import net.karashokleo.l2hostility.init.LHCplTexts;
import net.minecraft.text.Text;

import java.util.List;

public class REIBurntDisplay implements Display
{
    private final EntryIngredient ingredient;
    private final EntryIngredient result;
    private final List<Text> tooltip;

    public REIBurntDisplay(BurntRecipe recipe)
    {
        this.ingredient = EntryIngredients.ofIngredient(recipe.ingredient);
        this.result = EntryIngredients.of(recipe.result);
        this.tooltip = List.of(LHCplTexts.BURNT_COUNT.get(recipe.chance));
    }

    public EntryIngredient getIngredient()
    {
        return ingredient;
    }

    public EntryIngredient getResult()
    {
        return result;
    }

    public List<Text> getTooltip()
    {
        return tooltip;
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(ingredient);
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of(result);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.BURNT;
    }
}
