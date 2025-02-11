package karashokleo.l2hostility.data.config.provider;

import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.Constants;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.leobrary.data.AbstractDataProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import java.util.HashMap;
import java.util.Map;

public class TraitConfigProvider extends AbstractDataProvider
{
    private static final Map<MobTrait, TraitConfig.Config> configs = new HashMap<>();

    public TraitConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.PARENT_CONFIG_PATH + "/" + Constants.TRAIT_CONFIG_PATH);
    }

    public static void add(MobTrait trait, TraitConfig.Config config)
    {
        configs.put(trait, config);
    }

    @Override
    public String getName()
    {
        return "LH Trait Config";
    }

    @Override
    public void addAll()
    {
        for (Map.Entry<MobTrait, TraitConfig.Config> entry : configs.entrySet())
            add(entry.getKey().getNonNullId(), entry.getValue());
    }
}
