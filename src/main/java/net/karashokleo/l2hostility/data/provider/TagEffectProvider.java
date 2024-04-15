package net.karashokleo.l2hostility.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TagEffectProvider extends FabricTagProvider<StatusEffect>
{
    private static final Map<TagKey<StatusEffect>, List<StatusEffect>> tags = new HashMap<>();

    public static void add(TagKey<StatusEffect> key, StatusEffect effect)
    {
        tags.putIfAbsent(key, new ArrayList<>());
        tags.get(key).add(effect);
    }

    public TagEffectProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, RegistryKeys.STATUS_EFFECT, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg)
    {
        getOrCreateTagBuilder(LHTags.SKILL_EFFECT).add(
                StatusEffects.NIGHT_VISION,
                StatusEffects.BAD_OMEN,
                StatusEffects.HERO_OF_THE_VILLAGE,
                StatusEffects.DOLPHINS_GRACE,
                StatusEffects.CONDUIT_POWER,
                StatusEffects.WATER_BREATHING
        );

        for (Map.Entry<TagKey<StatusEffect>, List<StatusEffect>> entry : tags.entrySet())
        {
            var builder = getOrCreateTagBuilder(entry.getKey());
            for (StatusEffect effect : entry.getValue())
                builder.add(effect);
        }
    }

    @Override
    protected RegistryKey<StatusEffect> reverseLookup(StatusEffect element)
    {
        return Registries.STATUS_EFFECT.getKey(element).orElseThrow(() -> new IllegalArgumentException("Status Effect " + element + " is not registered"));
    }
}
