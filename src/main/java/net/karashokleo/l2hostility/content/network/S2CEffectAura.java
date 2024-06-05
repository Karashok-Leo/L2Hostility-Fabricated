package net.karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.client.L2HostilityClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

@SerialClass
public class S2CEffectAura extends S2CEntity
{
    @SerialClass.SerialField
    int range;
    @SerialClass.SerialField
    int color;

    @Deprecated
    public S2CEffectAura()
    {
        super();
    }

    public S2CEffectAura(LivingEntity e, int range, int color)
    {
        super(e);
        this.range = range;
        this.color = color;
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && id >= 0 && world.getEntityById(id) instanceof LivingEntity entity)
        {
            Vec3d center = entity.getPos();
            float tpi = (float) (Math.PI * 2);
            Vec3d v0 = new Vec3d(0, range, 0);
            for (int i = 0; i < range; i++)
            {
                v0 = v0.rotateX(tpi / 4).rotateY(entity.getRandom().nextFloat() * tpi);
                entity.getWorld().addImportantParticle(
                        ParticleTypes.EFFECT,
                        center.x + v0.x,
                        center.y + v0.y + 0.5f,
                        center.z + v0.z,
                        (color >> 16 & 255) / 255.0,
                        (color >> 8 & 255) / 255.0,
                        (color & 255) / 255.0
                );
            }
        }
    }
}
