package net.karashokleo.l2hostility.data.provider.cfg;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.init.registry.LHTraits;
import net.minecraft.entity.EntityType;

import java.util.List;

public class EntityConfigProvider extends ConfigDataProvider
{
    public EntityConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.ENTITY_CONFIG_PATH);
    }

    @Override
    public String getName()
    {
        return "LH Entity Config";
    }

    @Override
    public void add()
    {
        add(L2Hostility.id("bosses"), new EntityConfig()
                .putEntity(0, 20, 1, 0, List.of(EntityType.ELDER_GUARDIAN), List.of(
                        EntityConfig.trait(LHTraits.REPELLING, 1, 1, 300, 0.5f)
                ))
                .putEntity(0, 20, 1, 0, List.of(EntityType.PIGLIN_BRUTE), List.of(
                        EntityConfig.trait(LHTraits.PULLING, 1, 1, 300, 0.5f)
                ))
                .putEntity(0, 20, 1, 0, List.of(EntityType.WARDEN), List.of(
                        EntityConfig.trait(LHTraits.DISPELL, 1, 1, 200, 1),
                        EntityConfig.trait(LHTraits.REPRINT, 1, 1, 300, 1)
                ))
                .putEntity(0, 50, 1, 0, List.of(EntityType.WITHER), List.of(
                        EntityConfig.trait(LHTraits.CURSED, 0, 1)
                ))
                .putEntity(100, 50, 1, 0, List.of(EntityType.ENDER_DRAGON), List.of())
        );
    }
}
