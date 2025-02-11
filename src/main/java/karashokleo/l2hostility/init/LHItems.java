package karashokleo.l2hostility.init;

import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.leobrary.datagen.generator.ModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class LHItems
{
    public static void register()
    {
        TrinketItems.register();
        ConsumableItems.register();
        ComplementItems.register();
        MiscItems.register();
    }

    public static class Entry<T extends Item> extends ItemBuilder<T>
    {
        private static final Identifier SYMBOL_BG = L2Hostility.id("item/bg");

        public Entry(String name, T content)
        {
            super(name, content);
            if (content instanceof TraitSymbol)
                this.setTab(LHMiscs.TRAITS);
            else
                this.setTab(LHMiscs.GROUP);
        }

        public static <T extends Item> Entry<T> of(String name, T item)
        {
            return new Entry<>(name, item);
        }

        @Override
        public ItemBuilder<T> addModel()
        {
            ModelGenerator modelGenerator = this.getModelGenerator();
            if (content instanceof TrinketItem)
                modelGenerator.addItemWithTexturePrefix(content, Models.GENERATED, "trinket/");
            else if (content instanceof TraitSymbol)
                modelGenerator.addItem(generator ->
                        Models.GENERATED_TWO_LAYERS.upload(
                                this.getId().withPrefixedPath("item/"),
                                TextureMap.layered(SYMBOL_BG, this.getId().withPrefixedPath("item/trait/")),
                                generator.writer
                        ));
            else
                modelGenerator.addItem(content);
            return this;
        }

        @Override
        public ItemBuilder<T> addModel(Model model)
        {
            ModelGenerator modelGenerator = this.getModelGenerator();
            if (content instanceof TrinketItem)
                modelGenerator.addItemWithTexturePrefix(content, model, "trinket/");
            else
                modelGenerator.addItem(content, model);
            return this;
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
