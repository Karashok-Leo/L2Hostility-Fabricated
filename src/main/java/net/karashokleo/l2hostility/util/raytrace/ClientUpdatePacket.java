package net.karashokleo.l2hostility.util.raytrace;

import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;

@SerialClass
public class ClientUpdatePacket implements SerialPacketBase
{
    @SerialClass.SerialField
    public double range;

    public ClientUpdatePacket(double range)
    {
        this.range = range;
    }
}
