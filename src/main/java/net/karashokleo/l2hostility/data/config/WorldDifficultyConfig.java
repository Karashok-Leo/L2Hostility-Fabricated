package net.karashokleo.l2hostility.data.config;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.init.LHConfig;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

@SerialClass
public class WorldDifficultyConfig
{
    public static DifficultyConfig defaultLevel()
    {
        int base = LHConfig.common().scaling.defaultLevelBase;
        double var = LHConfig.common().scaling.defaultLevelVar;
        double scale = LHConfig.common().scaling.defaultLevelScale;
        return new DifficultyConfig(0, base, var, scale, 1, 1);
    }

    @SerialClass.SerialField
    public final HashMap<Identifier, DifficultyConfig> levelMap = new HashMap<>();
    @SerialClass.SerialField
    public final HashMap<Identifier, DifficultyConfig> biomeMap = new HashMap<>();

    public DifficultyConfig getByLevelOrDefault(Identifier id)
    {
        return levelMap.containsKey(id) ? levelMap.get(id) : defaultLevel();
    }

    public void merge(WorldDifficultyConfig config)
    {
        levelMap.putAll(config.levelMap);
        biomeMap.putAll(config.biomeMap);
    }

    public record DifficultyConfig(
            int min,
            int base,
            double variation,
            double scale,
            double apply_chance,
            double trait_chance
    )
    {
    }

    public WorldDifficultyConfig putDim(RegistryKey<World> key, int min, int base, double var, double scale)
    {
        levelMap.put(key.getValue(), new DifficultyConfig(min, base, var, scale, 1, 1));
        return this;
    }

    @SafeVarargs
    public final WorldDifficultyConfig putBiome(int min, int base, double var, double scale, RegistryKey<Biome>... keys)
    {
        for (var key : keys)
            biomeMap.put(key.getValue(), new DifficultyConfig(min, base, var, scale, 1, 1));
        return this;
    }
}
