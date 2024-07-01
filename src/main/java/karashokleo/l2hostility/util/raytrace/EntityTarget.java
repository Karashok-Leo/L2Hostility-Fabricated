package karashokleo.l2hostility.util.raytrace;

import karashokleo.l2hostility.client.L2HostilityClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class EntityTarget
{
    public static final ArrayList<EntityTarget> LIST = new ArrayList<>();
    public final double max_distance, max_angle;
    public final int max_time;
    public int time;
    public Entity target;

    public EntityTarget(double max_distance, double max_angle, int max_time)
    {
        this.max_distance = max_distance;
        this.max_angle = max_angle;
        this.max_time = max_time;

        LIST.add(this);
    }

    public void updateTarget(@Nullable Entity entity)
    {
        if (target != entity)
            onChange(entity);
        target = entity;
        time = 0;
    }

    public void onChange(@Nullable Entity entity)
    {
    }

    public void tickRender()
    {
        if (target == null)
            return;
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null)
        {
            updateTarget(null);
            return;
        }
        ItemStack stack = player.getMainHandStack();
        int distance = 0;
        if (stack.getItem() instanceof IGlowingTarget glow)
            distance = glow.getDistance(stack);
        if (distance == 0)
        {
            updateTarget(null);
            return;
        }

        Vec3d pos_a = player.getEyePos();
        Vec3d vec = player.getRotationVec(1);
        Vec3d pos_b = target.getLerpedPos(1);
        Vec3d diff = pos_b.subtract(pos_a);
        double dot = diff.dotProduct(vec);
        double len_d = diff.length();
        double len_v = vec.length();
        double angle = Math.acos(dot / len_d / len_v);
        double dist = Math.sin(angle) * len_d;
        if (angle > max_angle && dist > max_distance)
            updateTarget(null);
        time++;
        if (time >= max_time)
            updateTarget(null);
    }
}
