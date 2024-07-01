package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.recipe.BurntRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHRecipes
{
    public static final RecipeSerializer<BurntRecipe> BURNT_RECIPE_SERIALIZER = new BurntRecipe.Serializer();
    public static final RecipeType<BurntRecipe> BURNT_RECIPE_TYPE = new BurntRecipe.Type();

    public static void register()
    {
        Registry.register(Registries.RECIPE_SERIALIZER, L2Hostility.id("burnt"), BURNT_RECIPE_SERIALIZER);
        Registry.register(Registries.RECIPE_TYPE, L2Hostility.id("burnt"), BURNT_RECIPE_TYPE);
    }
}
