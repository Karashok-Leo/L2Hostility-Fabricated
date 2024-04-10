package net.karashokleo.l2hostility.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.registry.LHTraits;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TraitTagProvider extends FabricTagProvider<MobTrait>
{
    private static final Map<TagKey<MobTrait>, List<MobTrait>> tags = new HashMap<>();

    public static void add(TagKey<MobTrait> key, MobTrait trait)
    {
        tags.putIfAbsent(key, new ArrayList<>()).add(trait);
    }

    public TraitTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, LHTraits.TRAIT_KEY, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        for (Map.Entry<TagKey<MobTrait>, List<MobTrait>> entry : tags.entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (MobTrait trait : entry.getValue())
                builder.add(trait);
        }
    }
}
