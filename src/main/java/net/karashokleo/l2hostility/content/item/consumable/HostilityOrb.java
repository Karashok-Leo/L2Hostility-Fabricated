package net.karashokleo.l2hostility.content.item.consumable;

import net.karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HostilityOrb extends Item
{
    public HostilityOrb(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!LHConfig.common().orbAndSpawner.allowHostilityOrb)
            return TypedActionResult.pass(stack);
        if (!world.isClient())
        {
            boolean success = false;
            int r = LHConfig.common().orbAndSpawner.orbRadius;
            for (int x = -r; x <= r; x++)
                for (int y = -r; y <= r; y++)
                    for (int z = -r; z <= r; z++)
                    {
                        BlockPos pos = user.getBlockPos().add(x * 16, y * 16, z * 16);
                        if (world.isOutOfHeightLimit(pos)) continue;
                        var opt = ChunkDifficulty.at(world, pos);
                        if (opt.isPresent())
                            success = opt.get().getSection(pos.getY()).setClear(opt.get(), pos);
                    }
            if (success) stack.decrement(1);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        if (!LHConfig.common().orbAndSpawner.allowHostilityOrb)
            tooltip.add(LHTexts.BANNED.get());
        int r = LHConfig.common().orbAndSpawner.orbRadius * 2 + 1;
        tooltip.add(LHTexts.ITEM_ORB.get(r, r, r).formatted(Formatting.GRAY));
        tooltip.add(LHTexts.sectionRender().formatted(Formatting.DARK_GREEN));
    }
}
