package net.karashokleo.l2hostility.client;

import net.karashokleo.l2hostility.init.LHConfig;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class TraitEffectHandlers
{
    public static void handleEffect(TraitEffectToClient packet)
    {
        packet.effect.func.get().accept(packet);
    }

    public static void triggerClear(TraitEffectToClient packet)
    {
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && world.canSetBlock(packet.pos))
        {
            int x = packet.pos.getX() & -15;
            int y = packet.pos.getY() & -15;
            int z = packet.pos.getZ() & -15;
            for (int i = 0; i < 128; i++)
            {
                double dx = x + world.getRandom().nextDouble() * 16;
                double dy = y + world.getRandom().nextDouble() * 16;
                double dz = z + world.getRandom().nextDouble() * 16;
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, dx, dy, dz, 0, 0, 0);
            }
            world.playSound(x + 8, y + 8, z + 8, SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
        }
    }

    public static void triggerUndying(TraitEffectToClient packet)
    {
        if (!LHConfig.client().showUndyingParticles) return;
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && packet.id >= 0 && world.getEntityById(packet.id) instanceof LivingEntity entity)
        {
            L2HostilityClient.getClient().particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
            entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F, false);
        }
    }

    public static void triggerAura(TraitEffectToClient packet)
    {
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && packet.id >= 0 && world.getEntityById(packet.id) instanceof LivingEntity entity)
        {
            double radius = LHConfig.common().traits.killerAuraRange;
            Vec3d center = entity.getPos();
            for (int i = 0; i < 100; i++)
            {
                float tpi = (float) (Math.PI * 2);
                Vec3d v0 = new Vec3d(0, radius, 0);
                v0 = v0.rotateX(tpi / 4).rotateY(world.getRandom().nextFloat() * tpi);
                world.addImportantParticle(ParticleTypes.FLAME,
                        center.x + v0.x,
                        center.y + v0.y + 0.5f,
                        center.z + v0.z, 0, 0, 0);
            }
            entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, entity.getSoundCategory(), 3, 1.0F, false);
        }
    }
}
