package karashokleo.l2hostility.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "client")
public class ClientConfig implements ConfigData
{
    @Comment("Render Traits in name plate form")
    public boolean showTraitOverHead = true;
    @Comment("Render mob level in name plate form")
    public boolean showLevelOverHead = true;
    @Comment("Name plate render distance")
    public int overHeadRenderDistance = 32;
    @Comment("Name plate render offset in lines, upward is positive")
    public double overHeadRenderOffset = 0;
    @Comment("Overhead render text becomes full bright")
    public boolean overHeadRenderFullBright = true;
    @Comment("Overhead level color in decimal form, converted from hex form")
    public int overHeadLevelColor = 11184810;
    @Comment("Overhead level color for mobs affected by abyssal thorn")
    public int overHeadLevelColorAbyss = 16733525;
    @Comment("Show nameplate style trait and name only when hovered")
    public boolean showOnlyWhenHovered = false;
    @Comment("Detector Glasses glowing range for hidden mobs")
    public int glowingRangeHidden = 32;
    @Comment("Detector Glasses glowing range for nearby mobs")
    public int glowingRangeNear = 16;
    @Comment("Render undying particles")
    public boolean showUndyingParticles = true;
}
