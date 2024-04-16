package net.karashokleo.l2hostility.content.item.trinket.misc;

import net.karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LootingCharm extends BaseTrinketItem
{
    public LootingCharm(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.LOOTING_CHARM.get().formatted(Formatting.GRAY));
    }
}
