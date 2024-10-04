package karashokleo.l2hostility.content.item.trinket.misc;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHDamageTypes;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FlamingThorn extends DamageListenerTrinketItem
{
    public FlamingThorn(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        if (event.getSource().isOf(LHDamageTypes.SOUL_FLAME)) return;
        int size = event.getEntity().getActiveStatusEffects().size();
        if (size == 0) return;
        EffectUtil.forceAddEffect(event.getEntity(), new StatusEffectInstance(LHEffects.FLAME, LHConfig.common().items.flameThornTime, size - 1), entity);
//        event.getEntity().addStatusEffect(new StatusEffectInstance(LHEffects.FLAME, LHConfig.common().items.flameThornTime, size - 1), entity);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_FLAME_THORN.get(Math.round(LHConfig.common().items.flameThornTime * 0.05f)).formatted(Formatting.GOLD));
    }
}
