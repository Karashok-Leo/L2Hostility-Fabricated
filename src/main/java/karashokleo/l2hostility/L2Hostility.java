package karashokleo.l2hostility;

import karashokleo.l2hostility.data.generate.TraitGLMProvider;
import karashokleo.l2hostility.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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
        LHData.register();
        LHNetworking.init();
        LHItems.register();
        LHBlocks.register();
        LHTraits.register();
        LHTexts.init();
        LHAdvTexts.init();
        LHCplTexts.init();
        LHConfig.register();
        LHTags.init();
        LHEffects.register();
        LHPotions.register();
        LHEnchantments.register();
        LHDamageTypes.register();
        LHParticles.register();
        TraitGLMProvider.register();
        LHEvents.register();
        LHRecipes.register();
        LHTriggers.register();
        LHMiscs.register();
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