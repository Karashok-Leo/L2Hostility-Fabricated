package karashokleo.l2hostility.compat.data;

import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IaFData
{
    public static final String COMPAT_MOD_ID = "iceandfire";

    public static final Identifier FIRE_DRAGON = new Identifier(COMPAT_MOD_ID, "fire_dragon");
    public static final Identifier ICE_DRAGON = new Identifier(COMPAT_MOD_ID, "ice_dragon");
    public static final Identifier LIGHTNING_DRAGON = new Identifier(COMPAT_MOD_ID, "lightning_dragon");
    public static final Identifier GHOST = new Identifier(COMPAT_MOD_ID, "ghost");
    public static final Identifier SIREN = new Identifier(COMPAT_MOD_ID, "siren");
    public static final Identifier DEATH_WORM = new Identifier(COMPAT_MOD_ID, "deathworm");
    public static final Identifier SEA_SERPENT = new Identifier(COMPAT_MOD_ID, "sea_serpent");

    public static Map<Identifier, EntityConfig> getConfigs()
    {
        Map<Identifier, EntityConfig> configs = new HashMap<>();
        addEntity(configs, 100, 50, FIRE_DRAGON, List.of(
                EntityConfig.trait(LHTraits.ADAPTIVE, 1, 2),
                EntityConfig.trait(LHTraits.REGEN, 2, 3),
                EntityConfig.trait(LHTraits.SOUL_BURNER, 2, 3)
        ), List.of(LHTraits.TANK));
        addEntity(configs, 100, 50, ICE_DRAGON, List.of(
                EntityConfig.trait(LHTraits.ADAPTIVE, 1, 2),
                EntityConfig.trait(LHTraits.REGEN, 2, 3),
                EntityConfig.trait(LHTraits.FREEZING, 2, 3)
        ), List.of(LHTraits.TANK));
        addEntity(configs, 100, 50, LIGHTNING_DRAGON, List.of(
                EntityConfig.trait(LHTraits.ADAPTIVE, 1, 2),
                EntityConfig.trait(LHTraits.REGEN, 2, 3),
                EntityConfig.trait(LHTraits.REFLECT, 2, 3)
        ), List.of(LHTraits.TANK));
        addEntity(configs, 30, 10, GHOST, List.of(
                EntityConfig.trait(LHTraits.DEMENTOR, 0, 1)
        ), List.of(LHTraits.DISPELL));
        addEntity(configs, 30, 10, SIREN, List.of(
                EntityConfig.trait(LHTraits.CONFUSION, 1, 1),
                EntityConfig.trait(LHTraits.DRAIN, 0, 1)
        ), List.of());
        return configs;
    }

    public static void addEntity(Map<Identifier, EntityConfig> configMap, int min, int base, Identifier id, List<EntityConfig.TraitBase> traits, List<MobTrait> ban)
    {
        var config = new EntityConfig();
        config.putEntityAndItem(min, base, 0, 0, List.of(Registries.ENTITY_TYPE.get(id)), traits, List.of());
        config.list.get(0).blacklist.addAll(ban);
        configMap.put(id, config);
    }
}
