package net.karashokleo.l2hostility.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TagItemProvider extends FabricTagProvider.ItemTagProvider
{
    private static final Map<TagKey<Item>, List<Item>> tags = new HashMap<>();

    public static void add(TagKey<Item> key, Item item)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        tags.get(key).add(item);
    }

    public TagItemProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        for (Map.Entry<TagKey<Item>, List<Item>> entry : tags.entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (Item item : entry.getValue())
                builder.add(item);
        }
    }
}
