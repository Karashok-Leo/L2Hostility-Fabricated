package net.karashokleo.l2hostility.data.generate;

import dev.emi.trinkets.api.TrinketItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.karashokleo.l2hostility.content.item.traits.TraitSymbol;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModelProvider extends FabricModelProvider
{
    private static final Map<Item, Model> items = new HashMap<>();

    public static void addItem(Item item)
    {
        items.put(item, Models.GENERATED);
    }

    public static void addItem(Item item, Model model)
    {
        items.put(item, model);
    }

    public ModelProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator)
    {

    }

    @Override
    public void generateItemModels(ItemModelGenerator generator)
    {
        generator.register();
        for (Map.Entry<Item, Model> entry : items.entrySet())
            register(generator, entry.getKey(), entry.getValue());
    }

    public final void register(ItemModelGenerator generator, Item item, Model model)
    {
        if (item instanceof TraitSymbol)
            registerWithPrefix(generator, item, model, "trait/");
        else if (item instanceof TrinketItem)
            registerWithPrefix(generator, item, model, "trinket/");
        else generator.register(item, model);
    }

    public final void registerWithPrefix(ItemModelGenerator generator, Item item, Model model, String prefix)
    {
        Identifier id = Registries.ITEM.getId(item);
        model.upload(
                id.withPrefixedPath("item/"),
                TextureMap.layer0(id.withPrefixedPath("item/" + prefix)),
                generator.writer
        );
    }
}
