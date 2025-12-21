package karashokleo.l2hostility.compat.rei;

import karashokleo.l2hostility.compat.shared.ITraitLootRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public record REILootDisplay(
    EntryIngredient trinkets,
    EntryIngredient traits,
    EntryIngredient loot,
    List<Text> tooltip
) implements Display
{
    public REILootDisplay(ITraitLootRecipe recipe)
    {
        this(
            EntryIngredients.ofItemStacks(recipe.getTrinketsRequired()),
            EntryIngredients.ofItemStacks(recipe.getTraits()),
            EntryIngredients.of(recipe.getLoot()),
            new ArrayList<>()
        );
        recipe.addTooltip(this.tooltip);
    }

    public REILootDisplay withTraits(EntryIngredient traits)
    {
        return new REILootDisplay(this.trinkets, traits, this.loot, this.tooltip);
    }

    public REILootDisplay withLoot(EntryIngredient loot)
    {
        return new REILootDisplay(this.trinkets, this.traits, loot, this.tooltip);
    }

    public EntryIngredient getTraits()
    {
        return traits;
    }

    public EntryIngredient getTrinkets()
    {
        return trinkets;
    }

    public EntryIngredient getLoot()
    {
        return loot;
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
