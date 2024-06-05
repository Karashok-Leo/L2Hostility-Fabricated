package net.karashokleo.l2hostility.init;

import dev.emi.trinkets.api.TrinketItem;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import karashokleo.leobrary.datagen.generator.ModelGenerator;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.item.ComplementItems;
import net.karashokleo.l2hostility.content.item.ConsumableItems;
import net.karashokleo.l2hostility.content.item.MiscItems;
import net.karashokleo.l2hostility.content.item.TrinketItems;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

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
        public static <T extends Item> Entry<T> of(String name, T item)
        {
            return new Entry<>(name, item);
        }

        public Entry(String name, T content)
        {
            super(name, content);
            this.setTab(LHMiscs.GROUP);
        }

        @Override
        public ItemBuilder<T> addModel()
        {
            if (getModelGenerator() == null)
                throw new UnsupportedOperationException();
            if (content instanceof TrinketItem)
                getModelGenerator().addItem(content, Models.GENERATED, "trinket/");
            else
                getModelGenerator().addItem(content);
            return this;
        }

        @Override
        public ItemBuilder<T> addModel(Model model)
        {
            if (getModelGenerator() == null)
                throw new UnsupportedOperationException();
            if (content instanceof TrinketItem)
                getModelGenerator().addItem(content, model, "trinket/");
            else
                getModelGenerator().addItem(content, model);
            return this;
        }

        @Override
        public @Nullable ModelGenerator getModelGenerator()
        {
            return LHData.MODELS;
        }

        @Override
        public @Nullable LanguageGenerator getEnglishGenerator()
        {
            return LHData.EN_TEXTS;
        }

        @Override
        public @Nullable LanguageGenerator getChineseGenerator()
        {
            return LHData.ZH_TEXTS;
        }

        @Override
        public @Nullable TagGenerator<Item> getTagGenerator()
        {
            return LHData.ITEM_TAGS;
        }

        @Override
        protected String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
