package karashokleo.l2hostility.content.item.trinket.curse;

import karashokleo.l2hostility.init.LHConfig;
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

public class CurseOfGreed extends CurseTrinketItem
{
    public CurseOfGreed(Settings settings)
    {
        super(settings);
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.curse.greedExtraLevel;
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return LHConfig.common().items.curse.greedDropFactor;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_CHARM_GREED.get(LHConfig.common().items.curse.greedDropFactor).formatted(Formatting.GOLD));
    }
}
