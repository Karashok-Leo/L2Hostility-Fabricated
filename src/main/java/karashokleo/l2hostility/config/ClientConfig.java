package karashokleo.l2hostility.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "client")
public class ClientConfig implements ConfigData
{
    public final boolean showTraitOverHead = true;
    public final boolean showLevelOverHead = true;
    public final int overHeadRenderDistance = 32;
    public final double overHeadRenderOffset = 0;
    public final boolean overHeadRenderFullBright = true;
    public final int overHeadLevelColor = 11184810;
    public final int overHeadLevelColorAbyss = 16733525;
    public final boolean showOnlyWhenHovered = false;
    public final int glowingRangeHidden = 32;
    public final int glowingRangeNear = 16;
    public final boolean showUndyingParticles = true;
}
