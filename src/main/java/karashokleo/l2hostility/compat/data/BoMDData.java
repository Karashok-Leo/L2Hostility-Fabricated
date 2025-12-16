package karashokleo.l2hostility.compat.data;

import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoMDData
{
    public static final String COMPAT_MOD_ID = "bosses_of_mass_destruction";

    public static final Identifier LICH = new Identifier(COMPAT_MOD_ID, "lich");
    public static final Identifier OBSIDILITH = new Identifier(COMPAT_MOD_ID, "obsidilith");
    public static final Identifier GAUNTLET = new Identifier(COMPAT_MOD_ID, "gauntlet");
    public static final Identifier VOID_BLOSSOM = new Identifier(COMPAT_MOD_ID, "void_blossom");

    public static Map<Identifier, EntityConfig> getConfigs()
    {
        Map<Identifier, EntityConfig> configs = new HashMap<>();
        addEntity(configs, 200, 50, LICH,
            EntityConfig.trait(LHTraits.TANK, 2, 3),
            EntityConfig.trait(LHTraits.REPRINT, 1, 1),
            EntityConfig.trait(LHTraits.FREEZING, 2, 3)
        );
        addEntity(configs, 200, 50, OBSIDILITH,
            EntityConfig.trait(LHTraits.TANK, 2, 3),
            EntityConfig.trait(LHTraits.REFLECT, 2, 3),
            EntityConfig.trait(LHTraits.WEAKNESS, 5, 5)
        );
        addEntity(configs, 200, 50, GAUNTLET,
            EntityConfig.trait(LHTraits.TANK, 2, 3),
            EntityConfig.trait(LHTraits.REFLECT, 2, 3),
            EntityConfig.trait(LHTraits.SOUL_BURNER, 2, 3)
        );
        addEntity(configs, 200, 50, VOID_BLOSSOM,
            EntityConfig.trait(LHTraits.TANK, 2, 3),
            EntityConfig.trait(LHTraits.REGEN, 5, 5),
            EntityConfig.trait(LHTraits.ADAPTIVE, 2, 3)
        );
        return configs;
    }

    @SuppressWarnings("all")
    private static void addEntity(Map<Identifier, EntityConfig> configMap, int min, int base, Identifier id, EntityConfig.TraitBase... traits)
    {
        configMap.put(
            id,
            new EntityConfig()
                .putEntity(min, base, 0, 0, List.of(Registries.ENTITY_TYPE.get(id)), List.of(traits))
        );
    }
}
