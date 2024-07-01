package karashokleo.l2hostility.compat.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import karashokleo.l2hostility.init.LHConfig;

public class ModMenuCompat implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return LHConfig::screen;
    }
}
