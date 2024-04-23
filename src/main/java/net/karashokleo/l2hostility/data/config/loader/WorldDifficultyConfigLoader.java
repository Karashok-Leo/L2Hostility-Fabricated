package net.karashokleo.l2hostility.data.config.loader;

import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.WorldDifficultyConfig;
import net.karashokleo.l2hostility.init.LHData;
import net.karashokleo.leobrary.data.AbstractDataLoader;
import net.minecraft.util.Identifier;

public class WorldDifficultyConfigLoader extends AbstractDataLoader<WorldDifficultyConfig>
{
    public WorldDifficultyConfigLoader()
    {
        super(Constants.PARENT_CONFIG_PATH + "/" + Constants.DIFFICULTY_CONFIG_PATH, WorldDifficultyConfig.class);
    }

    @Override
    protected void clear()
    {
        LHData.difficulties = new WorldDifficultyConfig();
    }

    @Override
    protected void load(Identifier id, WorldDifficultyConfig config)
    {
        LHData.difficulties.merge(config);
    }
}
