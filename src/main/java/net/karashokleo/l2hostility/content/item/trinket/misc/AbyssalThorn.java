package net.karashokleo.l2hostility.content.item.trinket.misc;

import dev.emi.trinkets.api.TrinketItem;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssalThorn extends TrinketItem
{
    public AbyssalThorn(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ABYSSAL_THORN.get().formatted(Formatting.RED));
    }
}
