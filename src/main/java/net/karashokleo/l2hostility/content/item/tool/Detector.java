package net.karashokleo.l2hostility.content.item.tool;

import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Detector extends Item
{
    public Detector(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_DETECTOR.get().formatted(Formatting.GRAY));
        tooltip.add(LHTexts.sectionRender().formatted(Formatting.DARK_GREEN));
    }
}
