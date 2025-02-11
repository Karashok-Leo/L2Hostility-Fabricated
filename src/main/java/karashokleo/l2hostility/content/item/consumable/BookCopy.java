package karashokleo.l2hostility.content.item.consumable;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;
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

    /**
     * @return true to cancel the original method
     */
    public static boolean onAnvilUpdate(ItemStack left, ItemStack right, CraftingResultInventory output, Property levelCost, MutableInt repairItemUsage)
    {
        if (left.getItem() instanceof BookCopy &&
            right.getItem() instanceof EnchantedBookItem)
        {
            var map = EnchantmentHelper.get(right);
            int cost = 0;
            for (var e : map.entrySet())
                cost += BookCopy.cost(e.getKey(), e.getValue());
            ItemStack result = right.copy();
            if (!LHConfig.common().items.bookOfReprintSpread)
                result.setCount(left.getCount() + right.getCount());
            output.setStack(0, result);
            levelCost.set(cost);
            repairItemUsage.setValue(right.getCount());
            return true;
        }
        return false;
    }

    public static void onTakeOutput(ItemStack left, ItemStack right, PlayerEntity player)
    {
        if (left.getItem() instanceof BookCopy &&
            right.getItem() instanceof EnchantedBookItem)
        {
            if (LHConfig.common().items.bookOfReprintSpread)
            {
                for (int i = 0; i < left.getCount(); i++)
                {
                    ItemStack result = right.copy();
                    result.setCount(1);
                    player.getInventory().offerOrDrop(result);
                }
            }
        }
    }
}
