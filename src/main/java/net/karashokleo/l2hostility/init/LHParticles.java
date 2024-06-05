package net.karashokleo.l2hostility.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.karashokleo.l2hostility.content.particle.EmeraldParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHParticles
{
    public static DefaultParticleType EMERALD;

    public static void register()
    {
        EMERALD = Registry.register(Registries.PARTICLE_TYPE, "emerald_splash", FabricParticleTypes.simple(false));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient()
    {
        ParticleFactoryRegistry.getInstance().register(EMERALD, new EmeraldParticle.Factory());
    }
}
