package karashokleo.l2hostility.content.item.trinket.ring;

import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinketItem;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RingOfDivinity extends DamageListenerTrinketItem
{
    private static final int TICK_REFRESH_INTERNAL = 10;

    public RingOfDivinity(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
        DamageSource source = event.getSource();
        if (!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
            !source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
            source.isIn(LHTags.MAGIC))
            event.setCanceled(true);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity.age % TICK_REFRESH_INTERNAL == 0)
            EffectUtil.forceAddEffect(entity, new StatusEffectInstance(LHEffects.CLEANSE, 40, 0, true, true), entity);
//        entity.addStatusEffect(new StatusEffectInstance(LHEffects.CLEANSE, 40, 0, true, true), entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_RING_DIVINITY.get().formatted(Formatting.GOLD));
    }
}
