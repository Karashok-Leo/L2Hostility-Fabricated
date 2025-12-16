package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.l2hostility.content.recipe.BurntRecipe;
import karashokleo.l2hostility.init.LHCplTexts;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EMIBurntRecipe implements EmiRecipe
{
    private final EmiIngredient ingredient;
    private final EmiStack result;
    private final List<Text> tooltip;

    public EMIBurntRecipe(BurntRecipe recipe)
    {
        this.ingredient = EmiIngredient.of(recipe.ingredient);
        this.result = EmiStack.of(recipe.result);
        this.tooltip = List.of(LHCplTexts.BURNT_COUNT.get(recipe.chance));
    }

    @Override
    public List<EmiIngredient> getCatalysts()
    {
        return List.of(
            EmiStack.of(Items.LAVA_BUCKET),
            EmiStack.of(Items.FLINT_AND_STEEL),
            EmiStack.of(Items.FIRE_CHARGE)
        );
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.BURNT_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId()
    {
        return null;
    }

    @Override
    public List<EmiIngredient> getInputs()
    {
        return List.of(ingredient);
    }

    @Override
    public List<EmiStack> getOutputs()
    {
        return List.of(result);
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
        widgets.addSlot(ingredient, 0, 0);
        widgets.addTexture(EmiTexture.FULL_FLAME, 23, 2);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 42, 1);
        widgets.addSlot(result, 70, 0).recipeContext(this);
        widgets.addTooltipText(tooltip, 0, 0, getDisplayWidth(), getDisplayHeight());
    }
}
