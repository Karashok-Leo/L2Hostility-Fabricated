package karashokleo.l2hostility.data.config;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@SerialClass
public class DifficultyConfig
{
    private static Config DEFAULT = null;
    @SerialClass.SerialField
    public final HashMap<Identifier, Config> levelMap = new HashMap<>();
    @SerialClass.SerialField
    public final HashMap<Identifier, Config> biomeMap = new HashMap<>();
    @SerialClass.SerialField
    public final HashMap<Identifier, ArrayList<EntityConfig.Config>> levelDefaultTraits = new HashMap<>();

    @Nullable
    public EntityConfig.Config get(Identifier level, EntityType<?> type)
    {
        if (!LHConfig.common().enableEntitySpecificDatapack)
        {
            return null;
        }
        var list = levelDefaultTraits.get(level);
        if (list == null)
        {
            return null;
        }
        EntityConfig.Config def = null;
        for (var e : list)
        {
            if (e.entities.contains(type))
            {
                return e;
            }
            if (e.entities.isEmpty())
            {
                def = e;
            }
        }
        return def;
    }

    public static Config defaultConfig()
    {
        if (DEFAULT == null)
        {
            DEFAULT = new Config(
                0,
                LHConfig.common().scaling.defaultLevelBase,
                LHConfig.common().scaling.defaultTraitCountCap,
                LHConfig.common().scaling.defaultLevelVar,
                LHConfig.common().scaling.defaultLevelScale,
                1,
                1
            );
        }
        return DEFAULT;
    }

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

    public DifficultyConfig putDim(RegistryKey<World> key, int min, int base, double var, double scale)
    {
        levelMap.put(key.getValue(), new Config(min, base, 0, var, scale, 1, 1));
        return this;
    }

    @SafeVarargs
    public final DifficultyConfig putBiome(int min, int base, double var, double scale, RegistryKey<Biome>... keys)
    {
        for (var key : keys)
        {
            biomeMap.put(key.getValue(), new Config(min, base, 0, var, scale, 1, 1));
        }
        return this;
    }

    public record Config(
        int min,
        int base,
        int trait_count_cap,
        double variation,
        double scale,
        double apply_chance,
        double trait_chance
    )
    {
    }
}
