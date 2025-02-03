package karashokleo.l2hostility.content.item.consumable.charge;

import karashokleo.l2hostility.content.entity.fireball.StrongFireballEntity;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHCplTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StrongFireChargeItem extends BaseChargeItem
{
    public StrongFireChargeItem(Settings settings)
    {
        super(settings, StrongFireballEntity::new, StrongFireballEntity::new);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(
                LHCplTexts.EXPLOSION_CHARGE.get(
                        LHConfig.common().complements.fireCharge.strongFireChargePower
                ).formatted(Formatting.GRAY)
        );
    }
}
