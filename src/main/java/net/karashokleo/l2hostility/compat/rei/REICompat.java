package net.karashokleo.l2hostility.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.compat.loot.ITraitLootRecipe;
import net.karashokleo.l2hostility.content.loot.modifier.EnvyLootModifier;
import net.karashokleo.l2hostility.content.loot.modifier.GluttonyLootModifier;
import net.karashokleo.l2hostility.content.recipe.BurntRecipe;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHRecipes;
import net.karashokleo.l2hostility.init.LHTags;
import net.karashokleo.l2hostility.init.LHTraits;
import net.minecraft.item.Items;

public class REICompat implements REIClientPlugin
{
    public static final CategoryIdentifier<REILootDisplay> LOOT = CategoryIdentifier.of(L2Hostility.MOD_ID, "loot");
    public static final CategoryIdentifier<REIBurntDisplay> BURNT = CategoryIdentifier.of(L2Hostility.MOD_ID, "burnt");

    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new REILootCategory());
        registry.add(new REIBurntCategory());
        registry.addWorkstations(BURNT, EntryStacks.of(Items.LAVA_BUCKET));
        registry.addWorkstations(BURNT, EntryStacks.of(Items.FLINT_AND_STEEL));
        registry.addWorkstations(BURNT, EntryStacks.of(Items.FIRE_CHARGE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        for (ITraitLootRecipe recipe : ITraitLootRecipe.LIST_CACHE)
        {
            if (recipe instanceof EnvyLootModifier)
                for (MobTrait trait : LHTraits.TRAIT)
                    registry.add(
                            new REILootDisplay(recipe)
                                    .setTraits(EntryIngredients.of(trait.asItem()))
                                    .setLoot(EntryIngredients.of(trait.asItem()))
                    );
            else if (recipe instanceof GluttonyLootModifier)
                registry.add(new REILootDisplay(recipe).setTraits(EntryIngredients.ofItemTag(LHTags.TRAIT_ITEM)));
            else registry.add(new REILootDisplay(recipe));
        }
        for (BurntRecipe recipe : registry.getRecipeManager().listAllOfType(LHRecipes.BURNT_RECIPE_TYPE))
            registry.add(new REIBurntDisplay(recipe));
    }
}
