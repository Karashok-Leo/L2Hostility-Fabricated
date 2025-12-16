package karashokleo.l2hostility.content.item.consumable.charge;

import karashokleo.l2hostility.content.entity.fireball.SoulFireballEntity;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHCplTexts;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.util.EffectHelper;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulFireChargeItem extends BaseChargeItem
{
    public SoulFireChargeItem(Settings settings)
    {
        super(settings, SoulFireballEntity::new, SoulFireballEntity::new);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(
            LHCplTexts.EFFECT_CHARGE.get(
                EffectHelper.getEffectInstanceText(
                    new StatusEffectInstance(
                        LHEffects.FLAME,
                        LHConfig.common().complements.fireCharge.soulFireChargeDuration
                    )
                )
            ).formatted(Formatting.GRAY)
        );
    }
}
