package net.karashokleo.l2hostility.content.item.trinket.ring;

import net.karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfOcean extends BaseTrinketItem
{
    public RingOfOcean(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_OCEAN.get().formatted(Formatting.GOLD));
    }
}
