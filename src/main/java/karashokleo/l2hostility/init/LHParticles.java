package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.particle.EmeraldParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHParticles
{
    public static DefaultParticleType EMERALD;

    public static void register()
    {
        EMERALD = Registry.register(Registries.PARTICLE_TYPE, L2Hostility.id("emerald_splash"), FabricParticleTypes.simple());
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient()
    {
        ParticleFactoryRegistry.getInstance().register(EMERALD, EmeraldParticle.Factory::new);
    }
}
