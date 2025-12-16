package karashokleo.l2hostility.data.config;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import org.apache.commons.lang3.mutable.MutableBoolean;
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
    @SerialClass.SerialField
    public final HashMap<Identifier, ArrayList<EntityConfig.Config>> structureDefaultTraits = new HashMap<>();

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

    @Nullable
    public EntityConfig.Config get(ServerWorld level, BlockPos pos, EntityType<?> type)
    {
        if (!LHConfig.common().enableEntitySpecificDatapack)
        {
            return null;
        }
        if (!LHConfig.common().enableStructureSpecificDatapack)
        {
            return null;
        }
        if (structureDefaultTraits.isEmpty())
        {
            return null;
        }
        var manager = level.getStructureAccessor();
        var map = manager.getStructureReferences(pos);
        EntityConfig.Config def = null;
        for (var ent : map.entrySet())
        {
            var structure = ent.getKey();
            var key = level.getRegistryManager().get(RegistryKeys.STRUCTURE).getId(structure);
            var list = structureDefaultTraits.get(key);
            if (list == null)
            {
                continue;
            }
            MutableBoolean ans = new MutableBoolean(false);
            manager.acceptStructureStarts(structure, ent.getValue(), (e) ->
            {
                if (ans.isFalse() && manager.structureContains(pos, e))
                {
                    ans.setTrue();
                }
            });
            if (ans.isFalse())
            {
                continue;
            }
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

    public DifficultyConfig putLevelDef(RegistryKey<World> id, EntityConfig.Config config)
    {
        levelDefaultTraits.computeIfAbsent(id.getValue(), l -> new ArrayList<>()).add(config);
        return this;
    }

    public DifficultyConfig putStructureDef(RegistryKey<Structure> id, EntityConfig.Config config)
    {
        structureDefaultTraits.computeIfAbsent(id.getValue(), l -> new ArrayList<>()).add(config);
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
