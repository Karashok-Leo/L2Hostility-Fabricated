package karashokleo.l2hostility.content.item.complements;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class TooltipItem extends Item
{
    private final Supplier<MutableText> textSupplier;

    public TooltipItem(Settings settings, Supplier<MutableText> textSupplier)
    {
        super(settings);
        this.textSupplier = textSupplier;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(textSupplier.get().formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
