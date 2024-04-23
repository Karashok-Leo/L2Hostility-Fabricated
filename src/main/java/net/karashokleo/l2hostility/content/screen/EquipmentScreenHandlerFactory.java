package net.karashokleo.l2hostility.content.screen;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public record EquipmentScreenHandlerFactory(MobEntity mob) implements NamedScreenHandlerFactory
{
    @Override
    public Text getDisplayName()
    {
        return mob.getDisplayName();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player)
    {
//        return new EquipmentScreenHandler(LHMiscs.EQUIPMENTS.get(), wid, inv, e);
        return null;
    }

    public void writeBuffer(PacketByteBuf buf) {
        buf.writeInt(mob.getId());
    }

    public void open(ServerPlayerEntity player) {
        player.openHandledScreen(this);
    }
}
