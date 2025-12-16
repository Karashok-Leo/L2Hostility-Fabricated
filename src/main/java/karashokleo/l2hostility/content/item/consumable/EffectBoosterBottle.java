package karashokleo.l2hostility.content.item.consumable;

import karashokleo.l2hostility.content.item.traits.EffectBooster;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EffectBoosterBottle extends DrinkableBottleItem
{
    public EffectBoosterBottle(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void doServerLogic(ServerPlayerEntity player)
    {
        EffectBooster.boostBottle(player);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.TOOLTIP_WITCH_BOTTLE.get(
            LHConfig.common().items.witchChargeMinDuration / 20,
            Math.round(100 * LHConfig.common().traits.drainDuration),
            LHConfig.common().traits.drainDurationMax / 20
        ).formatted(Formatting.GRAY));
    }
}
