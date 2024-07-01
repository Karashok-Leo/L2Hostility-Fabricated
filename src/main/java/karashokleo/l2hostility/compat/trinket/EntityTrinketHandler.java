package karashokleo.l2hostility.compat.trinket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public record EntityTrinketHandler(LivingEntity entity,int page) implements NamedScreenHandlerFactory
{
    @Override
    public Text getDisplayName()
    {
        return entity.getDisplayName();
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return null;
    }
}
