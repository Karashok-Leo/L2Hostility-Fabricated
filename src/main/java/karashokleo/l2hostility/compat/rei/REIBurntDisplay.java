package karashokleo.l2hostility.compat.rei;

import karashokleo.l2hostility.content.recipe.BurntRecipe;
import karashokleo.l2hostility.init.LHCplTexts;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.text.Text;

import java.util.List;

public record REIBurntDisplay(
    EntryIngredient ingredient,
    EntryIngredient result,
    List<Text> tooltip
) implements Display
{
    public REIBurntDisplay(BurntRecipe recipe)
    {
        this(
            EntryIngredients.ofIngredient(recipe.ingredient),
            EntryIngredients.of(recipe.result),
            List.of(LHCplTexts.BURNT_COUNT.get(recipe.chance))
        );
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
