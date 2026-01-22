package karashokleo.l2hostility.content.item.trinket.misc;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class GreedOfNidhoggur extends CurseTrinketItem
{
    public GreedOfNidhoggur()
    {
        super();
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.nidhoggurExtraLevel;
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return LHConfig.common().items.curse.greedDropFactor;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int nid = (int) Math.round(100 * LHConfig.common().items.nidhoggurDropFactor);
        tooltip.add(LHTexts.ITEM_CHARM_GREED.get(LHConfig.common().items.curse.greedDropFactor).formatted(Formatting.GOLD));
        tooltip.add(LHTexts.NIDHOGGUR.get(nid).formatted(Formatting.GOLD));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static boolean applyLootEffect(LivingEntity target, DamageSource source, Collection<ItemEntity> drops, int lootingLevel, boolean recentlyHit)
    {
        var op = MobDifficulty.get(target);
        if (op.isEmpty())
        {
            return false;
        }
        MobDifficulty diff = op.get();
        // return true means cancel the drop
        if (diff.noDrop)
        {
            return true;
        }
        LivingEntity killer = target.getPrimeAdversary();
        if (killer != null && TrinketCompat.hasItemInTrinket(killer, TrinketItems.NIDHOGGUR))
        {
            double val = LHConfig.common().items.nidhoggurDropFactor * diff.getLevel();
            int count = (int) val;
            if (target.getRandom().nextDouble() < val - count)
            {
                count++;
            }
            count++;
            for (var stack : drops)
            {
                ItemStack itemStack = stack.getStack();
                int ans = itemStack.getCount() * count;
                if (LHConfig.common().items.nidhoggurCapAtItemMaxStack)
                {
                    ans = Math.min(itemStack.getMaxCount(), ans);
                }
                itemStack.setCount(ans);
            }
        }
        return false;

    }
}
