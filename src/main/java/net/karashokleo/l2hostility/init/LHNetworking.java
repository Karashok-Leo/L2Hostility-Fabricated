package net.karashokleo.l2hostility.init;

import dev.xkmc.l2serial.network.PacketHandler;
import dev.xkmc.l2serial.network.SimplePacketBase;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.network.S2CAuraEffect;
import net.karashokleo.l2hostility.content.network.S2CClearDifficulty;
import net.karashokleo.l2hostility.content.network.S2CKillerAura;
import net.karashokleo.l2hostility.content.network.S2CUndying;
import net.karashokleo.l2hostility.util.raytrace.TargetSetPacket;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.WorldChunk;

public class LHNetworking
{
    public static final PacketHandler HANDLER = new PacketHandler(L2Hostility.MOD_ID);

    public static void init()
    {
        HANDLER.configure(TargetSetPacket.class, S2CClearDifficulty.class, S2CAuraEffect.class, S2CKillerAura.class, S2CUndying.class);

        HANDLER.configureC2S(TargetSetPacket.class);
    }

    public static void initClient()
    {
        HANDLER.configureS2C(S2CClearDifficulty.class, S2CAuraEffect.class, S2CKillerAura.class, S2CUndying.class);
    }

    public static <T extends SimplePacketBase> void toTracking(Entity entity, T packet)
    {
        PlayerLookup.tracking(entity).forEach(player -> ServerPlayNetworking.send(player, HANDLER.getPacket(packet)));
    }

    public static <T extends SimplePacketBase> void toTracking(WorldChunk chunk, T packet)
    {
        if (chunk.getWorld() instanceof ServerWorld world)
            PlayerLookup.tracking(world, chunk.getPos()).forEach(player -> ServerPlayNetworking.send(player, HANDLER.getPacket(packet)));
    }

    public static <T extends SimplePacketBase> void toServer(T packet)
    {
        ClientPlayNetworking.send(HANDLER.getPacket(packet));
    }
}
