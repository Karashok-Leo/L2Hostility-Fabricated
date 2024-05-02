package net.karashokleo.l2hostility;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.init.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class L2Hostility implements ModInitializer
{
    public static final String MOD_ID = "l2hostility";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static MinecraftServer SERVER;

    @Override
    public void onInitialize()
    {
        // Current Commit - bdae4c76d3265658cb227fde3f22a226ea3cc4a0
        // ————NYI————
        // LCItems
        // Compat
        // Hostility Charge Item
        // Advancements Generation ————————————————————————————————————————√
        // Recipes Generation —————————————————————————————————————————————√
        // Patchouli Generation ———————————————————————————————————————————√
        // Potion Events / EffectValidItem / SkillEffect
        // Commands
        // Hostility Spawner
        // Ring Of Reflection
        // EntityTrinketsMenu
        // EquipmentsMenu —————————————————————————————————————————————————√
        // Difficulty Tab —————————————————————————————————————————————————√
        // Difficulty Overlay
        // Grenade MobTrait / Grenade Bullet Entity
        // Trait Effect ———————————————————————————————————————————————————√
        // Killer Aura Damage Type ————————————————————————————————————————√

        LHNetworking.init();
        LHItems.register();
        LHBlocks.register();
        LHTraits.register();
        LHTexts.init();
        LHAdvTexts.init();
        LHConfig.register();
        LHTags.init();
        LHEffects.register();
        LHEnchantments.register();
        LHDamageTypes.register();
        LHEvents.register();
        LHTriggers.register();
        LHMiscs.register();
        LHData.register();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);
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