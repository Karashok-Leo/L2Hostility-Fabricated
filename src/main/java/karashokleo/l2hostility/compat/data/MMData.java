package karashokleo.l2hostility.compat.data;

import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MMData
{
    public static final String COMPAT_MOD_ID = "mutantmonsters";

    public static Map<Identifier, EntityConfig> getConfigs()
    {
        Map<Identifier, EntityConfig> configs = new HashMap<>();
        configs.put(
            new Identifier(COMPAT_MOD_ID, "bosses"),
            new EntityConfig()
                .putEntity(0, 0, 0, 0,
                    List.of(
                        ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get(),
                        ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get(),
                        ModRegistry.MUTANT_CREEPER_ENTITY_TYPE.get(),
                        ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get()
                    ),
                    List.of(
                        EntityConfig.trait(LHTraits.REPRINT, 1, 1, 150, 0.5f),
                        EntityConfig.trait(LHTraits.ADAPTIVE, 1, 2, 200, 0.5f)
                    )
                )
        );
        return configs;
    }
}
