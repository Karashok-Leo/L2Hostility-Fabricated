package net.karashokleo.l2hostility.util.raytrace;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SerialClass
public class TargetSetPacket implements SerialPacketC2S
{
    @SerialClass.SerialField
    public UUID player, target;

    @Deprecated
    public TargetSetPacket()
    {
    }

    public TargetSetPacket(UUID player, @Nullable UUID target)
    {
        this.player = player;
        this.target = target;
    }

    @Override
    public void handle(ServerPlayerEntity player)
    {
        RayTraceUtil.sync(this);
    }
}
