package net.karashokleo.l2hostility.content.item.wand;

import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AiConfigWand extends BaseWand
{
    public AiConfigWand(Settings settings)
    {
        super(settings);
    }

    @Override
    public void clickTarget(ItemStack stack, PlayerEntity player, LivingEntity entity)
    {
        if (entity instanceof MobEntity mob)
        {
            mob.setAiDisabled(!mob.isAiDisabled());
            player.sendMessage(LHTexts.MSG_AI.get(mob.getDisplayName(), mob.isAiDisabled()));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_WAND_AI.get().formatted(Formatting.GRAY));
    }
}
