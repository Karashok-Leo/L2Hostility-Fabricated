package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.l2hostility.compat.shared.ITraitLootRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record EMILootRecipe(
    EmiIngredient trinkets,
    EmiIngredient traits,
    EmiStack loot,
    List<Text> tooltip
) implements EmiRecipe
{
    public EMILootRecipe(ITraitLootRecipe recipe)
    {
        this(
            EmiIngredient.of(Ingredient.ofStacks(recipe.getTrinketsRequired().stream())),
            EmiIngredient.of(Ingredient.ofStacks(recipe.getTraits().stream())),
            EmiStack.of(recipe.getLoot()),
            new ArrayList<>()
        );
        recipe.addTooltip(this.tooltip);
    }

    public EMILootRecipe withTraits(EmiIngredient traits)
    {
        return new EMILootRecipe(this.trinkets, traits, this.loot, this.tooltip);
    }

    public EMILootRecipe withLoot(EmiStack loot)
    {
        return new EMILootRecipe(this.trinkets, this.traits, loot, this.tooltip);
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.LOOT_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId()
    {
        // TODO: fix
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs()
    {
        return List.of(traits, trinkets);
    }

    @Override
    public List<EmiStack> getOutputs()
    {
        return List.of(loot);
    }

    @Override
    public int getDisplayWidth()
    {
        return 88;
    }

    @Override
    public int getDisplayHeight()
    {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        widgets.addSlot(traits, 0, 0);
        widgets.addSlot(trinkets, 20, 0);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 42, 1);
        widgets.addSlot(loot, 70, 0).recipeContext(this);
        widgets.addTooltipText(tooltip, 0, 0, getDisplayWidth(), getDisplayHeight());
    }
}
