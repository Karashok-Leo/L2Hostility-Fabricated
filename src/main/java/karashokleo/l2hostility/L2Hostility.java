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
        LHEntities.register();
        LHTraits.register();
        LHTexts.init();
        LHAdvTexts.init();
        LHCplTexts.init();
        LHConfig.register();
        LHEffects.register();
        LHPotions.register();
        LHEnchantments.register();
        LHDamageTypes.register();
        LHMiscs.register();
        LHTags.init();
        LHParticles.register();
        TraitGLMProvider.register();
        LHEvents.register();
        LHRecipes.register();
        LHTriggers.register();
        LHCommands.register();
        ServerLifecycleEvents.SERVER_STARTED.register(server -> SERVER = server);
        LOGGER.info("Hello L2Hostility Fabricated!");
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