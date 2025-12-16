package karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

@SerialClass
public class S2CKillerAura extends S2CEntity
{
    @Deprecated
    public S2CKillerAura()
    {
        super();
    }

    public S2CKillerAura(LivingEntity e)
    {
        super(e);
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && id >= 0 && world.getEntityById(id) instanceof LivingEntity entity)
        {
            double radius = LHConfig.common().traits.killerAuraRange;
            Vec3d center = entity.getPos();
            for (int i = 0; i < 30 * radius; i++)
            {
                float tpi = (float) (Math.PI * 2);
                Vec3d v0 = new Vec3d(0, radius, 0).rotateX(tpi / 4).rotateY(world.getRandom().nextFloat() * tpi);
                world.addImportantParticle(ParticleTypes.FLAME,
                    center.x + v0.x,
                    center.y + v0.y + 0.5f,
                    center.z + v0.z, 0, 0, 0);
            }
            entity.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, entity.getSoundCategory(), 3, 1.0F, false);
        }
    }
}
