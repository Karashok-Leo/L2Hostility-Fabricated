package karashokleo.l2hostility.content.item.trinket.curse;

import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseOfLust extends CurseTrinketItem
{
    public CurseOfLust()
    {
        super();
    }

    @Override
    public void onKilled(ItemStack stack, LivingEntity entity, LivingEntity killed)
    {
        if (!(killed instanceof MobEntity mob)) return;
        for (var e : EquipmentSlot.values())
            mob.setEquipmentDropChance(e, 1);
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.curse.lustExtraLevel;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_CHARM_LUST.get().formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
