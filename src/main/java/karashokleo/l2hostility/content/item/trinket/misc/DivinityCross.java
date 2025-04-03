package karashokleo.l2hostility.content.item.trinket.misc;

import karashokleo.l2hostility.content.item.trinket.core.EffectValidItem;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivinityCross extends SingleEpicTrinketItem implements EffectValidItem
{
    public DivinityCross()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.DIVINITY_CROSS.get().formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean isEffectValid(StatusEffectInstance ins, ItemStack stack, LivingEntity user)
    {
        return ins.getEffectType().isBeneficial() && ins.getAmplifier() == 0;
    }
}
