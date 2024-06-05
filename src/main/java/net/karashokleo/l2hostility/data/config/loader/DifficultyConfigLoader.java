package net.karashokleo.l2hostility.data.config.loader;

import karashokleo.leobrary.data.AbstractDataLoader;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.DifficultyConfig;
import net.karashokleo.l2hostility.init.LHData;
import net.minecraft.util.Identifier;

public class DifficultyConfigLoader extends AbstractDataLoader<DifficultyConfig>
{
    public DifficultyConfigLoader()
    {
        super(Constants.PARENT_CONFIG_PATH + "/" + Constants.DIFFICULTY_CONFIG_PATH, DifficultyConfig.class);
    }

    @Override
    protected void clear()
    {
        LHData.difficulties = new DifficultyConfig();
    }

    @Override
    protected void load(Identifier id, DifficultyConfig config)
    {
        LHData.difficulties.merge(config);
    }

    @Override
    public Identifier getFabricId()
    {
        return L2Hostility.id("difficulty_config");
    }
}
