package net.karashokleo.l2hostility.client.util.raytrace;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public class RayTraceUtilClient
{
    public static final int CLIENT_TIMEOUT = 100;
    public static final EntityTarget TARGET = new EnderEntityTarget();

    public static void clientUpdateTarget(PlayerEntity player, double range)
    {
        if (!player.getWorld().isClient()) return;
        Vec3d vec3 = player.getEyePos();
        Vec3d vec31 = player.getRotationVec(1.0F).multiply(range);
        Vec3d vec32 = vec3.add(vec31);
        Box aabb = player.getBoundingBox().stretch(vec31).expand(1.0D);
        double sq = range * range;
        Predicate<Entity> predicate = (e) -> (e instanceof LivingEntity) && !e.isSpectator();
        EntityHitResult result = ProjectileUtil.raycast(player, vec3, vec32, aabb, predicate, sq);
        if (result != null && vec3.squaredDistanceTo(result.getPos()) < sq)
            TARGET.updateTarget(result.getEntity());
    }
}
