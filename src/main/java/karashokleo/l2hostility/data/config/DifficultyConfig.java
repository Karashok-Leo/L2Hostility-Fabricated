package karashokleo.l2hostility.data.config;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Optional;

@SerialClass
public class DifficultyConfig
{
    private static Config DEFAULT = null;

    public static Config defaultConfig()
    {
        if (DEFAULT == null)
            DEFAULT = new Config(
                    0,
                    LHConfig.common().scaling.defaultLevelBase,
                    LHConfig.common().scaling.defaultLevelVar,
                    LHConfig.common().scaling.defaultLevelScale,
                    1,
                    1
            );
        return DEFAULT;
    }

    @SerialClass.SerialField
    public final HashMap<Identifier, Config> levelMap = new HashMap<>();
    @SerialClass.SerialField
    public final HashMap<Identifier, Config> biomeMap = new HashMap<>();

    public Config getByLevelOrDefault(Identifier id)
    {
        return levelMap.containsKey(id) ? levelMap.get(id) : defaultConfig();
    }

    public Optional<Config> getByBiome(World world, BlockPos pos)
    {
        return world.getBiome(pos).getKey().map(e -> biomeMap.get(e.getValue()));
    }

    public void merge(DifficultyConfig config)
    {
        levelMap.putAll(config.levelMap);
        biomeMap.putAll(config.biomeMap);
    }

    public record Config(
            int min,
            int base,
            double variation,
            double scale,
            double apply_chance,
            double trait_chance
    )
    {
    }

    public DifficultyConfig putDim(RegistryKey<World> key, int min, int base, double var, double scale)
    {
        levelMap.put(key.getValue(), new Config(min, base, var, scale, 1, 1));
        return this;
    }

    @SafeVarargs
    public final DifficultyConfig putBiome(int min, int base, double var, double scale, RegistryKey<Biome>... keys)
    {
        for (var key : keys)
            biomeMap.put(key.getValue(), new Config(min, base, var, scale, 1, 1));
        return this;
    }
}
