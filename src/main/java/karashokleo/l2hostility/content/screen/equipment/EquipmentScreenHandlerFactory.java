package karashokleo.l2hostility.content.screen.equipment;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import karashokleo.l2hostility.init.LHMiscs;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public record EquipmentScreenHandlerFactory(MobEntity mob) implements ExtendedScreenHandlerFactory
{
    @Override
    public Text getDisplayName()
    {
        return mob.getDisplayName();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new EquipmentScreenHandler(LHMiscs.EQUIPMENTS, syncId, playerInventory, mob);
    }

    public void open(ServerPlayerEntity player)
    {
        player.openHandledScreen(this);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf)
    {
        buf.writeInt(mob.getId());
    }
}
