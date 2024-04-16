package net.karashokleo.l2hostility.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.config.ClientConfig;
import net.karashokleo.l2hostility.config.CommonConfig;

@Config(name = L2Hostility.MOD_ID)
public class LHConfig extends PartitioningSerializer.GlobalData
{
    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    ClientConfig client;

    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject
    CommonConfig common;

    public static ClientConfig client()
    {
        return L2Hostility.CONFIG.client;
    }

    public static CommonConfig common()
    {
        return L2Hostility.CONFIG.common;
    }

    public static LHConfig register()
    {
        AutoConfig.register(
                LHConfig.class,
                PartitioningSerializer.wrap(JanksonConfigSerializer::new)
        );
        return AutoConfig.getConfigHolder(LHConfig.class).getConfig();
    }
}

