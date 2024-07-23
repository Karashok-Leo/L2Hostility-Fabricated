package karashokleo.l2hostility.data.config.loader;

import karashokleo.leobrary.data.AbstractDataLoader;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.data.Constants;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHData;
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
        LHData.traits.put(id, config);
    }

    @Override
    public Identifier getFabricId()
    {
        return L2Hostility.id("trait_config");
    }
}
