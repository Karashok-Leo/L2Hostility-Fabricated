package net.karashokleo.l2hostility.content.item.trinket.ring;

import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHTags;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfReflection extends CurseTrinketItem
{
    public RingOfReflection(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_REFLECTION.get().formatted(Formatting.GOLD));
    }

    @Override
    public boolean reflectTrait(MobTrait trait) {
        return trait.isIn(LHTags.POTION);
    }
}
