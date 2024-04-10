package net.karashokleo.l2hostility.content.item.trinket.ring;

import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.attributes.PortingLibAttributes;
import net.karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.karashokleo.l2hostility.init.registry.LHEffects;
import net.karashokleo.l2hostility.util.EffectUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfIncarceration extends BaseTrinketItem
{
    public RingOfIncarceration(Settings settings)
    {
        super(settings);
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
        var attr = PortingLibAttributes.ENTITY_REACH;
        var ins = entity.getAttributeInstance(attr);
        var reach = ins == null ? attr.getDefaultValue() : ins.getValue();
        for (var e : entity.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), entity.getBoundingBox().expand(reach), e -> entity.distanceTo(e) < reach))
            EffectUtil.refreshEffect(e, new StatusEffectInstance(LHEffects.STONE_CAGE, 40, 0, true, true), entity);
    }
}
