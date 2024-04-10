package net.karashokleo.l2hostility.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.karashokleo.l2hostility.L2Hostility;

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
}

