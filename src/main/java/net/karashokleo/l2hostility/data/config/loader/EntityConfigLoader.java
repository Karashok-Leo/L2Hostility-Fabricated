package net.karashokleo.l2hostility.data.config.loader;

import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.init.LHData;
import net.karashokleo.leobrary.data.AbstractDataLoader;
import net.minecraft.util.Identifier;

public class EntityConfigLoader extends AbstractDataLoader<EntityConfig>
{
    public EntityConfigLoader()
    {
        super(Constants.PARENT_CONFIG_PATH + "/" + Constants.ENTITY_CONFIG_PATH, EntityConfig.class);
    }

    @Override
    protected void clear()
    {
        LHData.entities = new EntityConfig();
    }

    @Override
    protected void load(Identifier id, EntityConfig config)
    {
        LHData.entities.merge(config);
    }
}
