package karashokleo.l2hostility.content.item.misc.wand;

import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TargetSelectWand extends BaseWand
{
    private static final String KEY = "cachedMobID";

    public TargetSelectWand(Settings settings)
    {
        super(settings);
    }

    @Override
    public void clickTarget(ItemStack stack, PlayerEntity player, LivingEntity entity)
    {
        if (stack.getOrCreateNbt().contains(KEY))
        {
            Entity other = entity.getWorld().getEntityById(stack.getOrCreateNbt().getInt(KEY));
            if (other instanceof LivingEntity le && le != entity)
            {
                boolean succeed = false;
                if (entity instanceof MobEntity mob)
                {
                    mob.setTarget(le);
                    succeed = true;
                }
                if (le instanceof MobEntity mob)
                {
                    mob.setTarget(entity);
                    succeed = true;
                }
                stack.getOrCreateNbt().remove(KEY);
                if (succeed)
                    player.sendMessage(LHTexts.MSG_SET_TARGET.get(entity.getDisplayName(), le.getDisplayName()));
                else
                    player.sendMessage(LHTexts.MSG_TARGET_FAIL.get(entity.getDisplayName(), le.getDisplayName()));
                return;
            }
        }
        stack.getOrCreateNbt().putInt(KEY, entity.getId());
        player.sendMessage(LHTexts.MSG_TARGET_RECORD.get(entity.getDisplayName()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        if (world != null &&
                stack.getOrCreateNbt().contains(KEY) &&
                world.getEntityById(stack.getOrCreateNbt().getInt(KEY)) instanceof LivingEntity le)
            tooltip.add(LHTexts.MSG_TARGET_RECORD.get(
                    le.getDisplayName().copy().formatted(Formatting.AQUA)
            ).formatted(Formatting.GRAY));
        else tooltip.add(LHTexts.ITEM_WAND_TARGET.get().formatted(Formatting.GRAY));
    }
}
