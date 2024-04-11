package net.karashokleo.l2hostility.client.util.raytrace;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.client.L2HostilityClient;
import net.karashokleo.l2hostility.util.raytrace.TargetSetPacket;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EnderEntityTarget extends EntityTarget
{
    private int timeout = 0;

    public EnderEntityTarget()
    {
        super(3, Math.PI / 180 * 5, 10);
    }

    @Override
    public void onChange(@Nullable Entity entity)
    {
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null) return;
        UUID eid = entity == null ? null : entity.getUuid();
        ClientPlayNetworking.send(L2Hostility.HANDLER.getPacket(new TargetSetPacket(player.getUuid(), eid)));
        timeout = 0;
    }

    @Override
    public void tickRender()
    {
        super.tickRender();
        if (target != null)
        {
            timeout++;
            if (timeout > RayTraceUtilClient.CLIENT_TIMEOUT)
                onChange(target);
        }
    }
}
