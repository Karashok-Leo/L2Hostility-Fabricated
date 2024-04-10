package net.karashokleo.l2hostility.content.item.trinket.misc;

import dev.emi.trinkets.api.SlotReference;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DivinityLight extends CurseTrinketItem
{
    public DivinityLight(Settings settings)
    {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.DIVINITY_LIGHT.get().formatted(Formatting.GOLD));
    }

    @Override
    public double getGrowFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 0;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity instanceof PlayerEntity player)
            PlayerDifficulty.get(player).getLevelEditor().setBase(0);
    }
}
