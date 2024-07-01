package karashokleo.l2hostility.content.item.consumable;

import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BookCopy extends Item
{
    public BookCopy(Settings settings)
    {
        super(settings);
    }

    public static int cost(Enchantment key, int value)
    {
        if (key.getMaxLevel() >= value) return 1;
        return 1 << Math.min(10, value - key.getMaxLevel());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_BOOK_COPY.get().formatted(Formatting.GRAY));
    }
}
