package net.karashokleo.l2hostility.content.recipe;

import com.google.gson.*;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import dev.xkmc.l2serial.serialization.codec.PacketCodec;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.List;

@SerialClass
public class EnchantmentIngredient implements CustomIngredient
{
    public static Ingredient of(Enchantment enchantment, int min_level)
    {
        return new EnchantmentIngredient(enchantment, min_level).toVanilla();
    }

    public static final CustomIngredientSerializer<EnchantmentIngredient> SERIALIZER = new Serializer();

    @SerialClass.SerialField
    public Enchantment enchantment;
    @SerialClass.SerialField
    public int min_level;

    @Deprecated
    public EnchantmentIngredient()
    {
    }

    public EnchantmentIngredient(Enchantment enchantment, int min_level)
    {
        this.enchantment = enchantment;
        this.min_level = min_level;
    }

    @Override
    public boolean test(ItemStack stack)
    {
        return stack.getItem() instanceof EnchantedBookItem && EnchantmentHelper.get(stack).getOrDefault(this.enchantment, 0) >= this.min_level;
    }

    @Override
    public List<ItemStack> getMatchingStacks()
    {
        return List.of(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, min_level)));
    }

    @Override
    public boolean requiresTesting()
    {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer()
    {
        return SERIALIZER;
    }

    private static class Serializer implements CustomIngredientSerializer<EnchantmentIngredient>
    {
        private final Identifier id = L2Hostility.id("enchantment");

        @Override
        public Identifier getIdentifier()
        {
            return id;
        }

        @Override
        public EnchantmentIngredient read(JsonObject json)
        {
            return JsonCodec.from(json, EnchantmentIngredient.class, null);
        }

        @Override
        public void write(JsonObject json, EnchantmentIngredient ingredient)
        {
            JsonCodec.toJsonObject(ingredient, json);
        }

        @Override
        public EnchantmentIngredient read(PacketByteBuf buf)
        {
            return PacketCodec.from(buf, EnchantmentIngredient.class, null);
        }

        @Override
        public void write(PacketByteBuf buf, EnchantmentIngredient ingredient)
        {
            PacketCodec.to(buf, ingredient);
        }
    }
}
