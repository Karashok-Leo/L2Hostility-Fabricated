package karashokleo.l2hostility.content.item.complements;

import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class WindBottle extends TooltipItem
{
    public WindBottle(Settings settings, Supplier<MutableText> textSupplier)
    {
        super(settings, textSupplier);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if (!world.isClient() &&
                entity instanceof PlayerEntity player &&
                entity.getVelocity().length() >= LHConfig.common().complements.materials.windSpeed)
        {
            stack.decrement(1);
            player.getInventory().offerOrDrop(ComplementItems.CAPTURED_WIND.getDefaultStack());
        }
    }
}
