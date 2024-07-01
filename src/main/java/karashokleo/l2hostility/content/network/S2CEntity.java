package karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.entity.LivingEntity;

@SerialClass
public abstract class S2CEntity implements SerialPacketS2C
{
    @SerialClass.SerialField
    protected int id;

    @Deprecated
    public S2CEntity()
    {
    }

    protected S2CEntity(LivingEntity e)
    {
        id = e.getId();
    }
}
