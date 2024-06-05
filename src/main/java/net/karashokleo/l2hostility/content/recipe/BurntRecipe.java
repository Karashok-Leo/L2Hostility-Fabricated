package net.karashokleo.l2hostility.content.recipe;

import com.google.gson.JsonObject;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import dev.xkmc.l2serial.serialization.codec.PacketCodec;
import net.karashokleo.l2hostility.init.LHRecipes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@SerialClass
public class BurntRecipe implements Recipe<BurntRecipe.Inv>
{
    public static void onItemKill(World world, Entity entity, ItemStack stack)
    {
        Inv inv = new Inv(stack);
        var opt = world.getRecipeManager().getFirstMatch(LHRecipes.BURNT_RECIPE_TYPE, inv, world);
        if (opt.isEmpty()) return;
        BurntRecipe recipe = opt.get();
        ItemStack result = recipe.craft(inv, world.getRegistryManager());
        int chance = recipe.chance;
        int trial = stack.getCount();
        int det = trial / chance;
        trial = trial % chance;
        if (world.random.nextInt(chance) < trial) det++;
        det *= result.getCount();
        while (det > 0)
        {
            int sup = Math.min(det, result.getMaxCount());
            det -= sup;
            ItemStack copy = result.copy();
            copy.setCount(sup);
            world.spawnEntity(new ItemEntity(world, entity.getX(), entity.getY(), entity.getZ(), copy, 0, 0.5, 0));
        }
    }

    protected Identifier id;
    @SerialClass.SerialField
    public Ingredient ingredient;

    @SerialClass.SerialField
    public ItemStack result;

    @SerialClass.SerialField
    public int chance;

    public BurntRecipe(Identifier id)
    {
        this.id = id;
    }

    public BurntRecipe(Identifier id, Ingredient ingredient, ItemStack result, int chance)
    {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.chance = chance;
    }

    @Override
    public boolean matches(Inv inventory, World world)
    {
        return ingredient.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inv inventory, DynamicRegistryManager registryManager)
    {
        return result.copy();
    }

    @Override
    public boolean fits(int width, int height)
    {
        return false;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId()
    {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return LHRecipes.BURNT_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType()
    {
        return LHRecipes.BURNT_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<BurntRecipe>
    {
        @Override
        public BurntRecipe read(Identifier id, JsonObject json)
        {
            return JsonCodec.from(json, BurntRecipe.class, new BurntRecipe(id));
        }

        @Override
        public BurntRecipe read(Identifier id, PacketByteBuf buf)
        {
            return PacketCodec.from(buf, BurntRecipe.class, new BurntRecipe(id));
        }

        @Override
        public void write(PacketByteBuf buf, BurntRecipe recipe)
        {
            PacketCodec.to(buf, recipe);
        }
    }

    public static class Type implements RecipeType<BurntRecipe>
    {
        @Override
        public String toString()
        {
            return "burnt";
        }
    }

    public static class Inv extends SimpleInventory
    {
        public Inv(ItemStack stack)
        {
            super(stack);
        }
    }
}
