package net.karashokleo.l2hostility.content.item.trinket.curse;

import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfLust extends CurseTrinketItem
{
    public CurseOfLust(Settings settings)
    {
        super(settings);
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.curse.lustExtraLevel;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_CHARM_LUST.get().formatted(Formatting.GOLD));
    }
}
