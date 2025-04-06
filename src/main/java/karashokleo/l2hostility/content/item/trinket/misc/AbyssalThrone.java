package karashokleo.l2hostility.content.item.trinket.misc;

import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AbyssalThrone extends CurseTrinketItem
{
    public AbyssalThrone()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ABYSSAL_THRONE.get().formatted(Formatting.RED));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void modifyDifficulty(MobDifficultyCollector instance)
    {
        instance.traitCostFactor(0);
        instance.setNoTraitCountCap();
        instance.setFullChance();
        instance.setFullDrop();
    }
}
