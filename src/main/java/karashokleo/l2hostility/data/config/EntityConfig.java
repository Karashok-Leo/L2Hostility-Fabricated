package karashokleo.l2hostility.data.config;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SerialClass
public class EntityConfig
{
    public static boolean allow(EntityType<?> type, MobTrait trait)
    {
        Config config = LHData.entities.get(type);
        if (config == null) return true;
        return !config.blacklist.contains(trait);
    }

    @SerialClass.SerialField
    public final ArrayList<Config> list = new ArrayList<>();

    private final Map<EntityType<?>, Config> cache = new HashMap<>();

    public void merge(EntityConfig config)
    {
        list.addAll(config.list);
        for (var e : config.list)
            for (var type : e.entities)
                cache.put(type, e);
    }

    @Nullable
    public Config get(EntityType<?> type)
    {
        return LHConfig.common().enableEntitySpecificDatapack ? cache.get(type) : null;
    }

    @SerialClass
    public static class Config
    {
        @SerialClass.SerialField
        public final ArrayList<EntityType<?>> entities = new ArrayList<>();
        @SerialClass.SerialField
        public final ArrayList<TraitBase> traits = new ArrayList<>();
        @SerialClass.SerialField
        public final LinkedHashSet<MobTrait> blacklist = new LinkedHashSet<>();
        @SerialClass.SerialField
        public DifficultyConfig.Config difficulty = new DifficultyConfig.Config(0, 0, 0, 0, 1, 1);
        @SerialClass.SerialField
        public final ArrayList<ItemPool> items = new ArrayList<>();
        @SerialClass.SerialField
        public int minSpawnLevel = 0;
        @SerialClass.SerialField
        public int maxLevel = 0;

        @Deprecated
        public Config()
        {
        }

        public Config(
                List<EntityType<?>> entities,
                DifficultyConfig.Config difficulty
        )
        {
            this.entities.addAll(entities);
            this.difficulty = difficulty;
        }

        public Set<MobTrait> blacklist()
        {
            return blacklist;
        }

        public List<TraitBase> traits()
        {
            return traits;
        }

        public DifficultyConfig.Config difficulty()
        {
            return difficulty;
        }

        public Config minLevel(int level)
        {
            minSpawnLevel = level;
            return this;
        }

        public Config maxLevel(int level)
        {
            maxLevel = level;
            return this;
        }

        public Config trait(List<TraitBase> list)
        {
            traits.addAll(list);
            return this;
        }

        public Config item(List<ItemPool> list)
        {
            items.addAll(list);
            return this;
        }

        public Config blacklist(MobTrait... list)
        {
            Collections.addAll(blacklist, list);
            return this;
        }
    }

    public record ItemBuilder(int weight, ItemStack stack)
    {
    }

    public record ItemPool(int level, float chance, String slot, ArrayList<ItemBuilder> entries)
    {
    }

    public record TraitBase(MobTrait trait, int free, int min, @Nullable TraitCondition condition)
    {
    }

    public record TraitCondition(int lv, float chance, @Nullable Identifier id)
    {
        public boolean match(LivingEntity entity, int mobLevel, MobDifficultyCollector ins)
        {
            if (entity.getRandom().nextDouble() > chance) return false;
            if (mobLevel < lv) return false;
            return id == null || ins.hasAdvancement(id);
        }
    }

    public final EntityConfig put(Config config)
    {
        list.add(config);
        return this;
    }

    public final EntityConfig putEntity(int min, int base, double var, double scale, List<EntityType<?>> keys, List<TraitBase> traits)
    {
        return putEntityAndItem(min, base, var, scale, keys, traits, List.of());
    }

    public final EntityConfig putEntityAndItem(int min, int base, double var, double scale, List<EntityType<?>> keys, List<TraitBase> traits, List<ItemPool> items)
    {
        return put(entity(min, base, var, scale, keys).trait(traits).item(items));
    }

    public static Config entity(int min, int base, double var, double scale, List<EntityType<?>> keys)
    {
        return new Config(
                new ArrayList<>(keys),
                new DifficultyConfig.Config(min, base, var, scale, 1, 1)
        );
    }

    public static ItemPool simplePool(int level, String slot, ItemStack stack)
    {
        return new ItemPool(level, 1, slot, new ArrayList<>(List.of(new ItemBuilder(100, stack))));
    }

    public static TraitBase trait(MobTrait trait, int free, int min)
    {
        return new TraitBase(trait, free, min, null);
    }

    public static TraitBase trait(MobTrait trait, int free, int min, int lv, float chance)
    {
        return new TraitBase(trait, free, min, new TraitCondition(lv, chance, null));
    }
}
