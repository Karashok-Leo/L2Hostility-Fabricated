package net.karashokleo.l2hostility.content.recipe;

import com.google.gson.JsonObject;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.karashokleo.l2hostility.init.LHRecipes;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BurntRecipeBuilder extends RecipeJsonBuilder
{
    private final Advancement.Builder advancementBuilder = Advancement.Builder.createUntelemetered();

    @SerialClass.SerialField
    public Ingredient ingredient;

    @SerialClass.SerialField
    public ItemStack result;

    @SerialClass.SerialField
    public int chance;

    public BurntRecipeBuilder(Ingredient ingredient, ItemStack result, int chance)
    {
        this.ingredient = ingredient;
        this.result = result;
        this.chance = chance;
    }

    public BurntRecipeBuilder criterion(String string, CriterionConditions criterionConditions)
    {
        this.advancementBuilder.criterion(string, criterionConditions);
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId)
    {
        this.validate(recipeId);
        this.advancementBuilder
                .parent(CraftingRecipeJsonBuilder.ROOT)
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        exporter.accept(
                new BurntRecipeJsonProvider(
                        new BurntRecipe(recipeId, ingredient, result, chance),
                        recipeId,
                        advancementBuilder,
                        recipeId.withPrefixedPath("recipes/burnt/")
                )
        );
    }

    protected void validate(Identifier id)
    {
        if (this.advancementBuilder.getCriteria().isEmpty())
            throw new IllegalStateException("No way of obtaining recipe " + id);
    }

    public record BurntRecipeJsonProvider(
            BurntRecipe recipe,
            Identifier recipeId,
            Advancement.Builder builder,
            Identifier advancementId
    ) implements RecipeJsonProvider
    {
        @Override
        public void serialize(JsonObject json)
        {
            JsonCodec.toJsonObject(recipe, json);
        }

        @Override
        public Identifier getRecipeId()
        {
            return recipeId;
        }

        @Override
        public RecipeSerializer<?> getSerializer()
        {
            return LHRecipes.BURNT_RECIPE_SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson()
        {
            return builder.toJson();
        }

        @Nullable
        @Override
        public Identifier getAdvancementId()
        {
            return advancementId;
        }
    }
}
