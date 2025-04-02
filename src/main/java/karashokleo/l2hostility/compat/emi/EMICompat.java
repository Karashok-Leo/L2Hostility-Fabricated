package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.compat.shared.ITraitLootRecipe;
import karashokleo.l2hostility.compat.shared.LivingEntityWrapper;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.loot.modifier.EnvyLootModifier;
import karashokleo.l2hostility.content.loot.modifier.GluttonyLootModifier;
import karashokleo.l2hostility.content.recipe.BurntRecipe;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.*;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class EMICompat implements EmiPlugin
{
    public static final Identifier LOOT = L2Hostility.id("loot");
    public static final Identifier BURNT = L2Hostility.id("burnt");
    public static final Identifier TRAIT = L2Hostility.id("trait");
    public static final EmiRecipeCategory LOOT_CATEGORY = new EMICategory(LOOT, EmiStack.of(Items.IRON_SWORD), LHTexts.LOOT_TITLE::get);
    public static final EmiRecipeCategory BURNT_CATEGORY = new EMICategory(BURNT, EmiStack.of(Items.LAVA_BUCKET), LHCplTexts.BURNT_TITLE::get);
    public static final EmiRecipeCategory TRAIT_CATEGORY = new EMICategory(TRAIT, EmiStack.of(Items.SKELETON_SKULL), LHTexts.TRAIT_TITLE::get);

    @Override
    public void register(EmiRegistry registry)
    {
        registry.addCategory(LOOT_CATEGORY);
        registry.addCategory(BURNT_CATEGORY);
        registry.addCategory(TRAIT_CATEGORY);

        for (ITraitLootRecipe recipe : ITraitLootRecipe.LIST_CACHE)
        {
            if (recipe instanceof EnvyLootModifier)
                for (MobTrait trait : LHTraits.TRAIT)
                    registry.addRecipe(
                            new EMILootRecipe(recipe)
                                    .setTraits(EmiStack.of(trait.asItem()))
                                    .setLoot(EmiStack.of(trait.asItem()))
                    );
            else if (recipe instanceof GluttonyLootModifier)
                registry.addRecipe(new EMILootRecipe(recipe).setTraits(EmiIngredient.of(LHTags.TRAIT_ITEM)));
            else registry.addRecipe(new EMILootRecipe(recipe));
        }
        for (BurntRecipe recipe : registry.getRecipeManager().listAllOfType(LHRecipes.BURNT_RECIPE_TYPE))
            registry.addRecipe(new EMIBurntRecipe(recipe));
        LivingEntityWrapper.streamRegistry(MobDifficulty::validate, 2)
                .forEach(entityWrapper -> registry.addRecipe(new EMITraitRecipe(entityWrapper)));
    }
}
