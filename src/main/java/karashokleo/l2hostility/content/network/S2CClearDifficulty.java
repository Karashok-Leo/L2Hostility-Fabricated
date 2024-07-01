package karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.client.L2HostilityClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

@SerialClass
public class S2CClearDifficulty implements SerialPacketS2C
{
    @SerialClass.SerialField
    public BlockPos pos = BlockPos.ORIGIN;

    @Deprecated
    public S2CClearDifficulty()
    {
    }

    public S2CClearDifficulty(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && world.canSetBlock(pos))
        {
            int x = pos.getX() & -15;
            int y = pos.getY() & -15;
            int z = pos.getZ() & -15;
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
}
