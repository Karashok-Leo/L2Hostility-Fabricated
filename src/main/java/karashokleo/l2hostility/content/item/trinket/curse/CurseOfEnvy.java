package karashokleo.l2hostility.content.item.trinket.curse;

import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfEnvy extends CurseTrinketItem
{
    public CurseOfEnvy()
    {
        super();
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.curse.envyExtraLevel;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int rate = (int) Math.round(100 * LHConfig.common().items.curse.envyDropRate);
        tooltip.add(LHTexts.ITEM_CHARM_ENVY.get(rate).formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
