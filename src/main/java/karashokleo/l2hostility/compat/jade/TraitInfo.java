package karashokleo.l2hostility.compat.jade;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class TraitInfo implements IEntityComponentProvider
{
    public static final Identifier ID = L2Hostility.id("mob");

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig)
    {
        MobDifficulty.get(entityAccessor.getEntity()).ifPresent(mobDifficulty -> iTooltip.addAll(mobDifficulty.getTitle(true, true)));
    }

    @Override
    public Identifier getUid()
    {
        return ID;
    }
}
