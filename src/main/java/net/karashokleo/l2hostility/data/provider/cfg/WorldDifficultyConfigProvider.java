package net.karashokleo.l2hostility.data.provider.cfg;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.WorldDifficultyConfig;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;

public class WorldDifficultyConfigProvider extends ConfigDataProvider
{
    public WorldDifficultyConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.DIFFICULTY_CONFIG_PATH);
    }

    @Override
    public String getName()
    {
        return "LH World Difficulty Config";
    }

    @Override
    public void add()
    {
        add(L2Hostility.id("overworld"), new WorldDifficultyConfig()
                .putDim(World.OVERWORLD, 0, 0, 4, 1)
                .putBiome(0, 5, 1, 0,
                        BiomeKeys.LUSH_CAVES,
                        BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.BIRCH_FOREST,
                        BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.SPARSE_JUNGLE,
                        BiomeKeys.DESERT,
                        BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU,
                        BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA
                )
                .putBiome(0, 5, 1, 0.2,
                        BiomeKeys.DRIPSTONE_CAVES,
                        BiomeKeys.DARK_FOREST,
                        BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_SAVANNA
                )
                .putBiome(0, 10, 1, 0.2,
                        BiomeKeys.BADLANDS, BiomeKeys.ERODED_BADLANDS, BiomeKeys.WOODED_BADLANDS,
                        BiomeKeys.DEEP_COLD_OCEAN, BiomeKeys.DEEP_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.DEEP_LUKEWARM_OCEAN,
                        BiomeKeys.MUSHROOM_FIELDS,
                        BiomeKeys.STONY_SHORE,
                        BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP
                )
                .putBiome(0, 20, 1, 0.2,
                        BiomeKeys.SNOWY_SLOPES,
                        BiomeKeys.ICE_SPIKES,
                        BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.STONY_PEAKS
                )
                .putBiome(0, 50, 4, 0.5, BiomeKeys.DEEP_DARK)
        );

        add(L2Hostility.id("nether"), new WorldDifficultyConfig()
                .putDim(World.NETHER, 0, 20, 9, 1.2)
        );

        add(L2Hostility.id("end"), new WorldDifficultyConfig()
                .putDim(World.END, 0, 40, 16, 1.5)
        );
    }
}
