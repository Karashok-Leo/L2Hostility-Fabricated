package net.karashokleo.l2hostility.content.item.consumable;

import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.content.logic.LevelEditor;
import net.karashokleo.l2hostility.init.LHTexts;
import net.karashokleo.l2hostility.init.LHItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottleOfCurse extends DrinkableBottleItem
{
    public BottleOfCurse(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (TrinketCompat.hasItemInTrinket(user, LHItems.DIVINITY_LIGHT))
            return TypedActionResult.fail(stack);
        return super.use(world, user, hand);
    }

    @Override
    protected void doServerLogic(ServerPlayerEntity player)
    {
        PlayerDifficulty cap = PlayerDifficulty.get(player);
        LevelEditor editor = cap.getLevelEditor();
        editor.addBase(LHConfig.common().items.bottleOfCurseLevel);
        PlayerDifficulty.sync(player);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_BOTTLE_CURSE.get(LHConfig.common().items.bottleOfCurseLevel).formatted(Formatting.GRAY));
    }
}
