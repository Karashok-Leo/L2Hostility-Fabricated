package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.l2hostility.compat.loot.ITraitLootRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EMILootRecipe implements EmiRecipe
{
    private EmiIngredient traits;
    private final EmiIngredient trinkets;
    private EmiStack loot;
    private final List<Text> tooltip = new ArrayList<>();

    public EMILootRecipe(ITraitLootRecipe recipe)
    {
        this.traits = EmiIngredient.of(Ingredient.ofStacks(recipe.getTraits().stream()));
        this.trinkets = EmiIngredient.of(Ingredient.ofStacks(recipe.getTrinketsRequired().stream()));
        this.loot = EmiStack.of(recipe.getLoot());
        recipe.addTooltip(this.tooltip);
    }

    public EMILootRecipe setTraits(EmiIngredient traits)
    {
        this.traits = traits;
        return this;
    }

    public EMILootRecipe setLoot(EmiStack loot)
    {
        this.loot = loot;
        return this;
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.LOOT_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId()
    {
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
