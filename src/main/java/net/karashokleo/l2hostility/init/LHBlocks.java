package net.karashokleo.l2hostility.init;

import karashokleo.leobrary.datagen.builder.BlockBuilder;
import karashokleo.leobrary.datagen.builder.BlockSet;
import karashokleo.leobrary.datagen.generator.BlockLootGenerator;
import karashokleo.leobrary.datagen.generator.LanguageGenerator;
import karashokleo.leobrary.datagen.generator.ModelGenerator;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.block.HostilitySpawner;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class LHBlocks
{
    public static BlockSet SPAWNER;

    public static void register()
    {
        SPAWNER = Entry.of(
                        "hostility_spawner",
                        new HostilitySpawner()
                )
                .addSimpleItem()
                .addEN()
                .addZH("恶意刷怪笼")
                .registerWithItem();

        LHData.MODELS.addBlock(LHBlocks::generateSpawnerState);

        LHMiscs.GROUP.add(SPAWNER.item());
    }

    public static void generateSpawnerState(BlockStateModelGenerator generator)
    {
        Identifier id = generator.createSubModel(SPAWNER.block(), "", Models.CUBE_ALL, TextureMap::all);
        Identifier id_activated = generator.createSubModel(SPAWNER.block(), "_activated", Models.CUBE_ALL, TextureMap::all);
        Identifier id_clear = generator.createSubModel(SPAWNER.block(), "_clear", Models.CUBE_ALL, TextureMap::all);
        Identifier id_failed = generator.createSubModel(SPAWNER.block(), "_failed", Models.CUBE_ALL, TextureMap::all);
        generator.registerParentedItemModel(SPAWNER.block(), id);
        generator.blockStateCollector.accept(VariantsBlockStateSupplier
                .create(SPAWNER.block())
                .coordinate(
                        BlockStateVariantMap
                                .create(HostilitySpawner.STATE)
                                .register(phase -> BlockStateVariant
                                        .create()
                                        .put(
                                                VariantSettings.MODEL,
                                                switch (phase)
                                                {
                                                    case IDLE -> id;
                                                    case ACTIVATED -> id_activated;
                                                    case CLEAR -> id_clear;
                                                    case FAILED -> id_failed;
                                                }
                                        )
                                )
                )
        );
    }

    public static class Entry<T extends Block> extends BlockBuilder<T>
    {
        public static <T extends Block> Entry<T> of(String name, T block)
        {
            return new Entry<>(name, block);
        }

        public Entry(String name, T content)
        {
            super(name, content);
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
        public @Nullable TagGenerator<Block> getTagGenerator()
        {
            return LHData.BLOCK_TAGS;
        }

        @Override
        public @Nullable ModelGenerator getModelGenerator()
        {
            return LHData.MODELS;
        }

        @Override
        public @Nullable BlockLootGenerator getLootGenerator()
        {
            return LHData.BLOCK_LOOTS;
        }

        @Override
        protected String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
