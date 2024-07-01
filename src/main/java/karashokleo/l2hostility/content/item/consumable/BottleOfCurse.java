package karashokleo.l2hostility.content.item.consumable;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.logic.LevelEditor;
import karashokleo.l2hostility.init.LHTexts;
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
        if (TrinketCompat.hasItemInTrinket(user, TrinketItems.DIVINITY_LIGHT))
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
