package karashokleo.l2hostility.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "client")
public class ClientConfig implements ConfigData
{
    @Comment("Render Traits in name plate form")
    public final boolean showTraitOverHead = true;
    @Comment("Render mob level in name plate form")
    public final boolean showLevelOverHead = true;
    @Comment("Name plate render distance")
    public final int overHeadRenderDistance = 32;
    @Comment("Name plate render offset in lines, upward is positive")
    public final double overHeadRenderOffset = 0;
    @Comment("Overhead render text becomes full bright")
    public final boolean overHeadRenderFullBright = true;
    @Comment("Overhead level color in decimal form, converted from hex form")
    public final int overHeadLevelColor = 11184810;
    @Comment("Overhead level color for mobs affected by abyssal thorn")
    public final int overHeadLevelColorAbyss = 16733525;
    @Comment("Show nameplate style trait and name only when hovered")
    public final boolean showOnlyWhenHovered = false;
    @Comment("Detector Glasses glowing range for hidden mobs")
    public final int glowingRangeHidden = 32;
    @Comment("Detector Glasses glowing range for nearby mobs")
    public final int glowingRangeNear = 16;
    @Comment("Render undying particles")
    public final boolean showUndyingParticles = true;
}
