package net.karashokleo.l2hostility.data.generate;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TagEntityTypeProvider extends FabricTagProvider.EntityTypeTagProvider
{
    private static final Map<TagKey<EntityType<?>>, List<EntityType<?>>> types = new HashMap<>();
    private static final Map<TagKey<EntityType<?>>, List<TagKey<EntityType<?>>>> tags = new HashMap<>();

    public static void add(TagKey<EntityType<?>> key, EntityType<?>... entityTypes)
    {
        types.putIfAbsent(key, new ArrayList<>());
        for (EntityType<?> type : entityTypes)
            types.get(key).add(type);
    }

    public static void add(TagKey<EntityType<?>> key, TagKey<EntityType<?>> tag)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        tags.get(key).add(tag);
    }

    public TagEntityTypeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture)
    {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        for (Map.Entry<TagKey<EntityType<?>>, List<EntityType<?>>> entry : types.entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (EntityType<?> type : entry.getValue())
                builder.add(type);
        }
        for (Map.Entry<TagKey<EntityType<?>>, List<TagKey<EntityType<?>>>> entry : tags.entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (TagKey<EntityType<?>> tag : entry.getValue())
                builder.forceAddTag(tag);
        }
    }
}
