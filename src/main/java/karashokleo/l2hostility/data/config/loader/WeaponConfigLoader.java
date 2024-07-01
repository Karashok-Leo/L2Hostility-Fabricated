package karashokleo.l2hostility.data.config.loader;

import karashokleo.leobrary.data.AbstractDataLoader;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.data.Constants;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.init.LHData;
import net.minecraft.util.Identifier;

public class WeaponConfigLoader extends AbstractDataLoader<WeaponConfig>
{
    public WeaponConfigLoader()
    {
        super(Constants.PARENT_CONFIG_PATH + "/" + Constants.WEAPON_CONFIG_PATH, WeaponConfig.class);
    }

    @Override
    protected void clear()
    {
        LHData.weapons = new WeaponConfig();
    }

    @Override
    protected void load(Identifier id, WeaponConfig config)
    {
        LHData.weapons.merge(config);
    }

    @Override
    public Identifier getFabricId()
    {
        return L2Hostility.id("weapon_config");
    }
}
