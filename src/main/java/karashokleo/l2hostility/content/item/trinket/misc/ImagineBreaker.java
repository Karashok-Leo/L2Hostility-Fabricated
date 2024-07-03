package karashokleo.l2hostility.content.item.trinket.misc;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImagineBreaker extends CurseTrinketItem
{
    public ImagineBreaker(Settings settings)
    {
        super(settings);
    }

    @Override
    public void onAttacking(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
        event.getSource().setBypassMagic();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_IMAGINE_BREAKER.get().formatted(Formatting.GOLD));
        tooltip.add(LHTexts.ITEM_CHARM_NO_DROP.get().formatted(Formatting.RED));
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 0;
    }
}
