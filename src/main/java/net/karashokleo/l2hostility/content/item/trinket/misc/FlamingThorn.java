package net.karashokleo.l2hostility.content.item.trinket.misc;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.init.LHTexts;
import net.karashokleo.l2hostility.init.LHEffects;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlamingThorn extends CurseTrinketItem
{
    public FlamingThorn(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_FLAME_THORN.get(Math.round(LHConfig.common().items.flameThornTime * 0.05f)).formatted(Formatting.GOLD));
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        int size = entity.getActiveStatusEffects().size();
        if (size == 0) return;
        event.getEntity().addStatusEffect(new StatusEffectInstance(LHEffects.FLAME, LHConfig.common().items.flameThornTime, size - 1), entity);
    }
}
