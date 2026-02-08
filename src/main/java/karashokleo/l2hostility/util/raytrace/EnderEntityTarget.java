package karashokleo.l2hostility.util.raytrace;

import karashokleo.l2hostility.init.LHNetworking;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class EnderEntityTarget extends EntityTarget
{
    public static final int CLIENT_TIMEOUT = 100;
    public static final EntityTarget TARGET = new EnderEntityTarget();

    private int timeout = 0;

    public EnderEntityTarget()
    {
        super(3, Math.PI / 180 * 5, 10);
    }

    @Override
    public void onChange(@Nullable Entity entity)
    {
        UUID eid = entity == null ? null : entity.getUuid();
        LHNetworking.toServer(new TargetSetPacket(eid));
        timeout = 0;
    }

    @Override
    public void tickRender()
    {
        super.tickRender();
        if (target != null)
        {
            timeout++;
            if (timeout > CLIENT_TIMEOUT)
            {
                onChange(target);
            }
        }
    }
}
