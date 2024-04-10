package net.karashokleo.l2hostility.data.provider.cfg;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.TraitConfig;

import java.util.HashMap;
import java.util.Map;

public class TraitConfigProvider extends ConfigDataProvider
{
    private static final Map<MobTrait, TraitConfig.Config> configs = new HashMap<>();

    public static void add(MobTrait trait, TraitConfig.Config config)
    {
        configs.put(trait, config);
    }

    public TraitConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.TRAIT_CONFIG_PATH);
    }

    @Override
    public void add(Collector collector)
    {
        for (Map.Entry<MobTrait, TraitConfig.Config> entry : configs.entrySet())
            collector.add(entry.getKey().getNonNullId(), entry.getValue());
    }
}
