package net.karashokleo.l2hostility.data.config.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.data.Constants;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.leobrary.data.AbstractDataProvider;

import java.util.HashMap;
import java.util.Map;

public class TraitConfigProvider extends AbstractDataProvider
{
    private static final Map<MobTrait, TraitConfig.Config> configs = new HashMap<>();

    public static void add(MobTrait trait, TraitConfig.Config config)
    {
        configs.put(trait, config);
    }

    public TraitConfigProvider(FabricDataOutput output)
    {
        super(output, Constants.PARENT_CONFIG_PATH + "/" + Constants.TRAIT_CONFIG_PATH);
    }

    @Override
    public String getName()
    {
        return "LH Trait Config";
    }

    @Override
    public void add()
    {
        for (Map.Entry<MobTrait, TraitConfig.Config> entry : configs.entrySet())
            add(entry.getKey().getNonNullId(), entry.getValue());
    }
}
