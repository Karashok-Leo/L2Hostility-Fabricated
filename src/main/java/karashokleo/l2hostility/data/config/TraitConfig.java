package karashokleo.l2hostility.data.config;

import com.google.common.collect.ImmutableMap;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class TraitConfig
{
    public static final Config DEFAULT = new Config(L2Hostility.id("default"), 10, 100, 1, 10);

    private final Map<Identifier, Config> map = new HashMap<>();

    public void put(Identifier id, Config config)
    {
        config.id = id;
        map.put(id, config);
    }

    public Map<Identifier, Config> getAll()
    {
        return ImmutableMap.copyOf(map);
    }

    public Config getOrDefault(Identifier id)
    {
        return map.getOrDefault(id, DEFAULT);
    }

    @SerialClass
    public static class Config
    {
        public Identifier id;
        @SerialClass.SerialField
        public int min_level, cost, max_rank, weight;

        @Deprecated
        public Config()
        {
        }

        public Config(Identifier id, int cost, int weight, int maxRank, int minLevel)
        {
            this.id = id;
            this.cost = cost;
            this.weight = weight;
            this.max_rank = maxRank;
            this.min_level = minLevel;
        }

        public TagKey<EntityType<?>> getBlacklistTag()
        {
            assert id != null;
            Identifier tag = new Identifier(id.getNamespace(), id.getPath() + "_blacklist");
            return TagKey.of(Registries.ENTITY_TYPE.getKey(), tag);
        }

        public TagKey<EntityType<?>> getWhitelistTag()
        {
            assert id != null;
            Identifier tag = new Identifier(id.getNamespace(), id.getPath() + "_whitelist");
            return TagKey.of(Registries.ENTITY_TYPE.getKey(), tag);
        }

        public boolean allows(EntityType<?> type)
        {
            var blacklist = getBlacklistTag();
            var whitelist = getWhitelistTag();
            var manager = Registries.ENTITY_TYPE;
            assert manager != null;
            boolean def = true;
            if (manager.getEntryList(whitelist).isPresent())
            {
                if (type.isIn(whitelist)) return true;
                def = false;
            }
            if (manager.getEntryList(blacklist).isPresent())
            {
                if (type.isIn(blacklist)) return false;
                def = true;
            }
            return def;
        }
    }
}