package net.karashokleo.l2hostility.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

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
        for (Map.Entry<Item, Model> entry : items.entrySet())
            generator.register(entry.getKey(), entry.getValue());
    }
}
