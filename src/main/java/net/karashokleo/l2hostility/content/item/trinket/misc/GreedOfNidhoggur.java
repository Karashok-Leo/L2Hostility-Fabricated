package net.karashokleo.l2hostility.content.item.trinket.misc;

import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GreedOfNidhoggur extends CurseTrinketItem
{
    public GreedOfNidhoggur(Settings settings)
    {
        super(settings);
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.nidhoggurExtraLevel;
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return LHConfig.common().items.curse.greedDropFactor;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int nid = (int) Math.round(100 * LHConfig.common().items.nidhoggurDropFactor);
        tooltip.add(LHTexts.ITEM_CHARM_GREED.get(LHConfig.common().items.curse.greedDropFactor).formatted(Formatting.GOLD));
        tooltip.add(LHTexts.NIDHOGGUR.get(nid).formatted(Formatting.GOLD));
    }
}
