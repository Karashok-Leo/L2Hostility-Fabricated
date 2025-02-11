package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.config.ClientConfig;
import karashokleo.l2hostility.config.CommonConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.client.gui.screen.Screen;

public class LHConfig
{
    private static Global CONFIG;

    public static ClientConfig client()
    {
        return CONFIG.client;
    }

    public static CommonConfig common()
    {
        return CONFIG.common;
    }

    public static Screen screen(Screen parent)
    {
        return AutoConfig.getConfigScreen(Global.class, parent).get();
    }

    public static void register()
    {
        AutoConfig.register(
                Global.class,
                PartitioningSerializer.wrap(JanksonConfigSerializer::new)
        );
        CONFIG = AutoConfig.getConfigHolder(Global.class).getConfig();
    }

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
}

