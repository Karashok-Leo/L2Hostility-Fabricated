package karashokleo.l2hostility.content.item.consumable.charge;

import karashokleo.l2hostility.content.entity.fireball.WitchFireballEntity;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitchChargeItem extends BaseChargeItem
{
    public WitchChargeItem(Settings settings)
    {
        super(settings, WitchFireballEntity::new, WitchFireballEntity::new);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(
            LHTexts.TOOLTIP_WITCH_CHARGE.get(
                LHConfig.common().items.witchChargeMinDuration / 20,
                Math.round(100 * LHConfig.common().traits.drainDuration),
                LHConfig.common().traits.drainDurationMax / 20
            ).formatted(Formatting.GRAY)
        );
    }
}
