package karashokleo.l2hostility.content.item.trinket.ring;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.util.EffectHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfIncarceration extends SingleEpicTrinketItem
{
    public RingOfIncarceration()
    {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_INCARCERATION.get().formatted(Formatting.GOLD));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (!entity.isSneaking()) return;
        if (entity.isSpectator()) return;
        // Default Entity Interact Distance: 3.0
        double reach = ReachEntityAttributes.getAttackRange(entity, 3.0);
        for (var e : entity.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), entity.getBoundingBox().expand(reach), e -> entity.distanceTo(e) < reach))
        {
            if (e.isSpectator() ||
                e instanceof PlayerEntity player && player.isCreative()) continue;
            StatusEffectInstance newEffect = new StatusEffectInstance(LHEffects.STONE_CAGE, 40, 0, true, true);
            EffectHelper.forceAddEffectWithEvent(e, newEffect, entity);
        }
    }
}
