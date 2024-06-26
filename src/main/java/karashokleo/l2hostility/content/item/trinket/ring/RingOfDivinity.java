package karashokleo.l2hostility.content.item.trinket.ring;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfDivinity extends BaseTrinketItem
{
    public RingOfDivinity(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_DIVINITY.get().formatted(Formatting.GOLD));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        entity.addStatusEffect(new StatusEffectInstance(LHEffects.CLEANSE, 40, 0, true, true), entity);
    }
}
