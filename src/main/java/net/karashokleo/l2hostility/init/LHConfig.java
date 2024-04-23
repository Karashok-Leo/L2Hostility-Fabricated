package net.karashokleo.l2hostility.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.config.ClientConfig;
import net.karashokleo.l2hostility.config.CommonConfig;

public class LHConfig
{
    private static Global CONFIG;

    @Config(name = L2Hostility.MOD_ID)
    static class Global extends PartitioningSerializer.GlobalData
    {
        @ConfigEntry.Category("client")
        @ConfigEntry.Gui.TransitiveObject
        ClientConfig client;

        @ConfigEntry.Category("common")
        @ConfigEntry.Gui.TransitiveObject
        CommonConfig common;
    }

    public static ClientConfig client()
    {
        return CONFIG.client;
    }

    public static CommonConfig common()
    {
        return CONFIG.common;
    }

    public static void register()
    {
        AutoConfig.register(
                Global.class,
                PartitioningSerializer.wrap(JanksonConfigSerializer::new)
        );
        CONFIG = AutoConfig.getConfigHolder(Global.class).getConfig();
    }
}

