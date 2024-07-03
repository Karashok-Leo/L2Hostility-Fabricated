package karashokleo.l2hostility.content.item.trinket.misc;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlatinumStar extends DamageListenerTrinketItem
{
    public PlatinumStar(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onAttacking(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
        event.getSource().setBypassCooldown();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_PLATINUM_STAR.get().formatted(Formatting.GOLD));
    }
}
