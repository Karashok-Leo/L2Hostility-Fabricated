package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.recipe.BurntRecipe;
import net.karashokleo.l2hostility.content.recipe.EnchantmentIngredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHRecipes
{
    public static final RecipeSerializer<BurntRecipe> BURNT_RECIPE_SERIALIZER = new BurntRecipe.Serializer();
    public static final RecipeType<BurntRecipe> BURNT_RECIPE_TYPE = new BurntRecipe.Type();
    public static final CustomIngredientSerializer<EnchantmentIngredient> ENCHANTMENT_INGREDIENT_SERIALIZER = new EnchantmentIngredient.Serializer();

    public static void register()
    {
        Registry.register(Registries.RECIPE_SERIALIZER, L2Hostility.id("burnt"), BURNT_RECIPE_SERIALIZER);
        Registry.register(Registries.RECIPE_TYPE, L2Hostility.id("burnt"), BURNT_RECIPE_TYPE);
        CustomIngredientSerializer.register(ENCHANTMENT_INGREDIENT_SERIALIZER);
    }
}
