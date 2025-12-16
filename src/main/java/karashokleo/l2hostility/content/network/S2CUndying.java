package karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;

@SerialClass
public class S2CUndying extends S2CEntity
{
    @Deprecated
    public S2CUndying()
    {
        super();
    }

    public S2CUndying(LivingEntity e)
    {
        super(e);
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        if (!LHConfig.client().showUndyingParticles)
        {
            return;
        }
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && id >= 0 && world.getEntityById(id) instanceof LivingEntity entity)
        {
            L2HostilityClient.getClient().particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
        }
    }
}
