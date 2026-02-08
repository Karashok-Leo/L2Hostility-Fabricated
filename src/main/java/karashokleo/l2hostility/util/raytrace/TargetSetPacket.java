package karashokleo.l2hostility.util.raytrace;

import dev.xkmc.l2serial.network.SerialPacketC2S;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@SerialClass
public record TargetSetPacket(@Nullable UUID target) implements SerialPacketC2S
{
    @Override
    public void handle(ServerPlayerEntity player)
    {
        RayTraceUtil.serverUpdateTarget(player, target);
    }
}
