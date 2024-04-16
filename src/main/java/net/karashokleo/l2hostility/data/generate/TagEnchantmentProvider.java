package net.karashokleo.l2hostility.data.generate;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.karashokleo.l2hostility.init.LHTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TagEnchantmentProvider extends FabricTagProvider.EnchantmentTagProvider
{
    private static final Map<TagKey<Enchantment>, List<Enchantment>> tags = new HashMap<>();

    public static void add(TagKey<Enchantment> key, Enchantment enchantment)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        tags.get(key).add(enchantment);
    }

    public TagEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        getOrCreateTagBuilder(LHTags.NO_DISPELL).add(Enchantments.UNBREAKING);

        for (Map.Entry<TagKey<Enchantment>, List<Enchantment>> entry : tags.entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (Enchantment enchantment : entry.getValue())
                builder.add(enchantment);
        }
    }
}
