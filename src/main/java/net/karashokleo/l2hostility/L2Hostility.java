package net.karashokleo.l2hostility;

import dev.xkmc.l2serial.network.PacketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.karashokleo.l2hostility.data.config.loader.EntityConfigLoader;
import net.karashokleo.l2hostility.data.config.loader.TraitConfigLoader;
import net.karashokleo.l2hostility.data.config.loader.WeaponConfigLoader;
import net.karashokleo.l2hostility.data.config.loader.WorldDifficultyConfigLoader;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.init.LHData;
import net.karashokleo.l2hostility.init.*;
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
    private static MinecraftServer SERVER;

    @Override
    public void onInitialize()
    {
        // ————NYI————
        // Advancements Generation
        // Recipes Generation
        // Patchouli Generation
        // Potion Events / EffectValidItem / SkillEffect
        // Commands
        // Hostility Spawner
        // EquipmentWand / EntityCuriosMenuPvd / EquipmentsMenuPvd
        // Difficulty Tab
        // Grenade MobTrait / Grenade Bullet Entity
        // Killer Aura Damage Type ————————————————————————————————————————√

        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);

        L2Hostility.HANDLER.configure(TargetSetPacket.class);

        LHItems.register();
        LHTraits.register();
        LHTexts.register();
        LHConfig.register();
        LHTags.register();
        LHEffects.register();
        LHEnchantments.register();
        LHDamageTypes.register();
        LHEvents.register();
        LHTriggers.register();
        LHMiscs.register();

        ServerPlayNetworking.registerGlobalReceiver(HANDLER.getPacketType(TargetSetPacket.class), (packet, player, responseSender) -> RayTraceUtil.sync(packet.packet()));

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new EntityConfigLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new TraitConfigLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WeaponConfigLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WorldDifficultyConfigLoader());
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