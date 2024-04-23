package net.karashokleo.l2hostility.data.config.loader;

import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.init.LHData;
import net.karashokleo.leobrary.data.AbstractDataLoader;
import net.minecraft.util.Identifier;

public class TraitConfigLoader extends AbstractDataLoader<TraitConfig.Config>
{
    public TraitConfigLoader()
    {
        super(Constants.PARENT_CONFIG_PATH + "/" + Constants.TRAIT_CONFIG_PATH, TraitConfig.Config.class);
    }

    @Override
    protected void clear()
    {
        LHData.traits = new TraitConfig();
    }

    @Override
    protected void load(Identifier id, TraitConfig.Config config)
    {
        LHData.traits.put(id.withPath(s ->
        {
            int li = s.lastIndexOf('/');
            int ri = s.lastIndexOf(".json");
            if (li != -1 && ri != -1)
                return s.substring(li + 1, ri);
            return s;
        }), config);
    }
}
