package net.karashokleo.l2hostility.content.item.trinket.ring;

import dev.emi.trinkets.api.SlotReference;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfHealing extends BaseTrinketItem
{
    public RingOfHealing(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_HEALING.get(Math.round(LHConfig.common().items.ringOfHealingRate * 100)).formatted(Formatting.GOLD));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity.age % 20 != 0) return;
        entity.heal((float) (LHConfig.common().items.ringOfHealingRate * entity.getMaxHealth()));
    }
}
