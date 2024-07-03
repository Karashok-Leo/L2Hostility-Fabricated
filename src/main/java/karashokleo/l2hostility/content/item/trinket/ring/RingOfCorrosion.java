package karashokleo.l2hostility.content.item.trinket.ring;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.content.item.traits.DurabilityEater;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfCorrosion extends DamageListenerTrinketItem
{
    public RingOfCorrosion(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
            DurabilityEater.flat(event.getEntity(), e, LHConfig.common().items.ringOfCorrosionFactor);
    }

    @Override
    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
            DurabilityEater.flat(entity, e, LHConfig.common().items.ringOfCorrosionPenalty);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_CORROSION.get(Math.round(LHConfig.common().items.ringOfCorrosionFactor * 100)).formatted(Formatting.GOLD));
        tooltip.add(LHTexts.ITEM_RING_CORROSION_NEG.get(Math.round(LHConfig.common().items.ringOfCorrosionFactor * 100)).formatted(Formatting.RED));
    }
}
