package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import karashokleo.l2hostility.compat.shared.LivingEntityWrapper;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record EMITraitRecipe(
    Identifier id,
    LivingEntityWrapper entityWrapper,
    List<EmiStack> traits
) implements EMIPageRecipe
{
    public EMITraitRecipe(LivingEntityWrapper entityWrapper)
    {
        this(
            Registries.ENTITY_TYPE
                .getId(entityWrapper.entity().getType())
                .withPrefixedPath("/"),
            entityWrapper,
            LHTraits.TRAIT
                .stream()
                .filter(trait -> trait.allow(entityWrapper.entity()))
                .map(EmiStack::of)
                .toList()
        );
    }

    @Override
    public List<EmiStack> getStacks()
    {
        return traits;
    }

    @Override
    public EmiRecipeCategory getCategory()
    {
        return EMICompat.TRAIT_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId()
    {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs()
    {
        return List.of();
    }

    @Override
    public List<EmiStack> getOutputs()
    {
        return traits;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        int centerX = widgets.getWidth() / 2;
        int pageYOffset = this.getPageYOffset();
        widgets.addDrawable(
            0, -pageYOffset / 2, 20, 20,
            (context, mouseX, mouseY, delta) -> entityWrapper.render(context, centerX, pageYOffset + 10, mouseX, mouseY)
        );
        EMIPageRecipe.super.addWidgets(widgets);
    }
}
