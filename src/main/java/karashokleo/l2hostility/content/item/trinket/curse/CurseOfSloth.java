package karashokleo.l2hostility.content.item.trinket.curse;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfSloth extends CurseTrinketItem
{
    public CurseOfSloth(Settings settings)
    {
        super(settings);
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 0;
    }

    @Override
    public double getGrowFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_CHARM_SLOTH.get().formatted(Formatting.GOLD));
        tooltip.add(LHTexts.ITEM_CHARM_NO_DROP.get().formatted(Formatting.RED));
    }
}
