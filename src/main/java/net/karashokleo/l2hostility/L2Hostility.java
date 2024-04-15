package net.karashokleo.l2hostility;

import dev.xkmc.l2serial.network.PacketHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.karashokleo.l2hostility.data.DataLoader;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.karashokleo.l2hostility.init.registry.*;
import net.karashokleo.l2hostility.util.raytrace.ClientUpdatePacket;
import net.karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.karashokleo.l2hostility.util.raytrace.TargetSetPacket;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class L2Hostility implements ModInitializer
{
    public static final String MOD_ID = "l2hostility";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final PacketHandler HANDLER = new PacketHandler(MOD_ID);
    public static LHConfig CONFIG;
    private static MinecraftServer SERVER;

    @Override
    public void onInitialize()
    {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);

        AutoConfig.register(
                LHConfig.class,
                PartitioningSerializer.wrap(JanksonConfigSerializer::new)
        );
        CONFIG = AutoConfig.getConfigHolder(LHConfig.class).getConfig();

        L2Hostility.HANDLER.configure(ClientUpdatePacket.class);
        L2Hostility.HANDLER.configure(TargetSetPacket.class);

        LHItems.register();
        LHTraits.register();
        LHTags.register();
        LHEffects.register();
        LHEnchantments.register();
        LHEvents.register();
        LHTriggers.register();
        LHMiscs.register();

        ServerPlayNetworking.registerGlobalReceiver(HANDLER.getPacketType(TargetSetPacket.class), (packet, player, responseSender) -> RayTraceUtil.sync(packet.packet()));

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new DataLoader());
    }

    public static Identifier id(String path)
    {
        return new Identifier(MOD_ID, path);
    }

    public static MinecraftServer getServer()
    {
        return SERVER;
    }
}