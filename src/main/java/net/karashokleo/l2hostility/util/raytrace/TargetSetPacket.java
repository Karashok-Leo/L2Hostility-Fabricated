package net.karashokleo.l2hostility.util.raytrace;

import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SerialClass
public class TargetSetPacket implements SerialPacketBase
{
    @SerialClass.SerialField
    public UUID player, target;

    public TargetSetPacket(UUID player, @Nullable UUID target)
    {
        this.player = player;
        this.target = target;
    }
}
