package net.karashokleo.l2hostility.content.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class EnchantmentRecipeBuilder extends RecipeJsonBuilder
{
    public static EnchantmentRecipeBuilder create(Enchantment enchantment, int level)
    {
        return new EnchantmentRecipeBuilder(enchantment, level);
    }

    public final Enchantment enchantment;
    public final int level;
    public final List<String> rows = Lists.newArrayList();
    public final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancement = Advancement.Builder.create();
    @Nullable
    private String group;

    public EnchantmentRecipeBuilder(Enchantment enchantment, int level)
    {
        this.enchantment = enchantment;
        this.level = level;
    }

    public EnchantmentRecipeBuilder input(Character character, TagKey<Item> tagKey)
    {
        return this.input(character, Ingredient.fromTag(tagKey));
    }

    public EnchantmentRecipeBuilder input(Character character, ItemConvertible itemConvertible)
    {
        return this.input(character, Ingredient.ofItems(itemConvertible));
    }

    public EnchantmentRecipeBuilder input(Character character, Ingredient ingredient)
    {
        if (this.key.containsKey(character))
            throw new IllegalArgumentException("Symbol '" + character + "' is already defined!");
        else if (character == ' ')
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        else
        {
            this.key.put(character, ingredient);
            return this;
        }
    }

    public EnchantmentRecipeBuilder pattern(String patternStr)
    {
        if (!this.rows.isEmpty() && patternStr.length() != this.rows.get(0).length())
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        else
        {
            this.rows.add(patternStr);
            return this;
        }
    }

    public EnchantmentRecipeBuilder criterion(String string, CriterionConditions criterionConditions)
    {
        this.advancement.criterion(string, criterionConditions);
        return this;
    }

    public EnchantmentRecipeBuilder group(@Nullable String group)
    {
        this.group = group;
        return this;
    }

    public Item getResult()
    {
        return Items.ENCHANTED_BOOK;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter)
    {
        this.offerTo(exporter, L2Hostility.id(Registries.ENCHANTMENT.getId(this.enchantment).getPath()));
    }

    public void offerTo(Consumer<RecipeJsonProvider> pvd, Identifier loc)
    {
        this.ensureValid(loc);
        this.advancement.parent(new Identifier("recipes/root")).criterion("has_the_recipe", RecipeUnlockedCriterion.create(loc)).rewards(AdvancementRewards.Builder.recipe(loc)).criteriaMerger(CriterionMerger.OR);
        pvd.accept(new Result(loc, this.enchantment, this.level, this.group == null ? "" : this.group, this.rows, this.key, this.advancement, new Identifier(loc.getNamespace(), "recipes/enchantments/" + loc.getPath())));
    }

    private void ensureValid(Identifier id)
    {
        if (this.rows.isEmpty())
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        else
        {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for (String s : this.rows)
                for (int i = 0; i < s.length(); ++i)
                {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ')
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");

                    set.remove(c0);
                }

            if (!set.isEmpty())
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            else if (this.rows.size() == 1 && this.rows.get(0).length() == 1)
                throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            else if (this.advancement.getCriteria().isEmpty())
                throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements RecipeJsonProvider
    {
        private final Identifier id;
        private final Enchantment enchantment;
        private final int lvl;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancement;
        private final Identifier advancementId;

        public Result(Identifier id, Enchantment enchantment, int lvl, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancement, Identifier advancementId)
        {
            this.id = id;
            this.enchantment = enchantment;
            this.lvl = lvl;
            this.group = group;
            this.pattern = pattern;
            this.key = key;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serialize(JsonObject json)
        {
            if (!this.group.isEmpty())
            {
                json.addProperty("group", this.group);
            }

            JsonArray jsonarray = new JsonArray();

            for (String s : this.pattern)
            {
                jsonarray.add(s);
            }

            json.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();

            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet())
            {
                jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }

            json.add("key", jsonobject);

            JsonObject result = new JsonObject();
            result.addProperty("item", Registries.ITEM.getId(Items.ENCHANTED_BOOK).toString());
            JsonObject tag = new JsonObject();
            JsonArray list = new JsonArray();
            JsonObject entry = new JsonObject();
            entry.addProperty("id", Registries.ENCHANTMENT.getId(this.enchantment).toString());
            entry.addProperty("lvl", this.lvl);
            list.add(entry);
            tag.add("StoredEnchantments", list);
            result.add("nbt", tag);

            json.add("result", result);
        }

        @Override
        public Identifier getRecipeId()
        {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getSerializer()
        {
            return RecipeSerializer.SHAPED;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson()
        {
            return this.advancement.toJson();
        }

        @Nullable
        public Identifier getAdvancementId()
        {
            return this.advancementId;
        }
    }
}
