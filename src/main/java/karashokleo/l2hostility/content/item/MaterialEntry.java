package karashokleo.l2hostility.content.item;

import karashokleo.l2hostility.init.LHBlocks;
import karashokleo.l2hostility.init.LHItems;
import karashokleo.leobrary.datagen.builder.BlockBuilder;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.leobrary.datagen.builder.MaterialBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.function.BiFunction;

public class MaterialEntry extends MaterialBuilder
{
    public static MaterialEntry of(String name_en, String name_zh)
    {
        return new MaterialEntry(name_en, name_zh);
    }

    public MaterialEntry(String name_en, String name_zh)
    {
        super(name_en, name_zh);
    }

    @Override
    public <T extends Item> BiFunction<String, T, ItemBuilder<T>> getItemBuilder()
    {
        return LHItems.Entry::of;
    }

    @Override
    public <T extends Block> BiFunction<String, T, BlockBuilder<T>> getBlockBuilder()
    {
        return LHBlocks.Entry::of;
    }
}
