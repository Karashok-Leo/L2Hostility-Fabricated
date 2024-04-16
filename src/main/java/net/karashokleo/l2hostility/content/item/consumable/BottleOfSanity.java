package net.karashokleo.l2hostility.content.item.consumable;

import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.content.logic.LevelEditor;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottleOfSanity extends DrinkableBottleItem
{
    public BottleOfSanity(Settings settings)
    {
        super(settings);
    }

    @Override
    protected void doServerLogic(ServerPlayerEntity player)
    {
        PlayerDifficulty cap = PlayerDifficulty.get(player);
        LevelEditor editor = cap.getLevelEditor();
        editor.setBase(0);
        cap.dimensions.clear();
        PlayerDifficulty.sync(player);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_BOTTLE_SANITY.get().formatted(Formatting.GRAY));
    }
}
