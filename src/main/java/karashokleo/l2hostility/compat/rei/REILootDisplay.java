package karashokleo.l2hostility.compat.rei;

import karashokleo.l2hostility.compat.loot.ITraitLootRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class REILootDisplay implements Display
{
    private final EntryIngredient trinkets;
    private final List<Text> tooltip = new ArrayList<>();
    private EntryIngredient traits;
    private EntryIngredient loot;

    public REILootDisplay(ITraitLootRecipe recipe)
    {
        this.traits = EntryIngredients.ofItemStacks(recipe.getTraits());
        this.trinkets = EntryIngredients.ofItemStacks(recipe.getTrinketsRequired());
        this.loot = EntryIngredients.of(recipe.getLoot());
        recipe.addTooltip(this.tooltip);
    }

    public EntryIngredient getTraits()
    {
        return traits;
    }

    public REILootDisplay setTraits(EntryIngredient traits)
    {
        this.traits = traits;
        return this;
    }

    public EntryIngredient getTrinkets()
    {
        return trinkets;
    }

    public EntryIngredient getLoot()
    {
        return loot;
    }

    public REILootDisplay setLoot(EntryIngredient loot)
    {
        this.loot = loot;
        return this;
    }

    public List<Text> getTooltip()
    {
        return tooltip;
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of(traits, trinkets);
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of(loot);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.LOOT;
    }
}
