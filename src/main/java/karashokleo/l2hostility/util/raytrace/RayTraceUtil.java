package karashokleo.l2hostility.util.raytrace;

import com.google.common.collect.Maps;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.client.L2HostilityClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

public class RayTraceUtil
{
    public static final int SERVER_TIMEOUT = 200;
    public static final ConcurrentMap<UUID, ServerTarget> TARGET_MAP = Maps.newConcurrentMap();


    @Nullable
    public static EntityHitResult rayTraceEntity(PlayerEntity player, double reach, Predicate<Entity> pred)
    {
        World world = player.getWorld();
        Vec3d pos = new Vec3d(player.getX(), player.getEyeY(), player.getZ());
        Vec3d end = getRayTerm(pos, player.getPitch(), player.getYaw(), reach);
        Box box = new Box(pos, end).expand(1);
        return ProjectileUtil.getEntityCollision(world, player, pos, end, box, pred);
    }

    public static BlockHitResult rayTraceBlock(World worldIn, PlayerEntity player, double reach)
    {
        float xRot = player.getPitch();
        float yRot = player.getYaw();
        Vec3d Vector3d = new Vec3d(player.getX(), player.getEyeY(), player.getZ());
        Vec3d Vector3d1 = getRayTerm(Vector3d, xRot, yRot, reach);
        return worldIn.raycast(new RaycastContext(Vector3d, Vector3d1, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player));
    }

    public static Vec3d getRayTerm(Vec3d pos, float xRot, float yRot, double reach)
    {
        float f2 = MathHelper.cos(-yRot * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = MathHelper.sin(-yRot * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -MathHelper.cos(-xRot * ((float) Math.PI / 180F));
        float f5 = MathHelper.sin(-xRot * ((float) Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        return pos.add(f6 * reach, f5 * reach, f7 * reach);
    }

    public static void serverTick()
    {
        TARGET_MAP.entrySet().removeIf(e ->
        {
            ServerPlayerEntity player = L2Hostility.getServer().getPlayerManager().getPlayer(e.getKey());
            if (player == null)
            {
                return true;
            }
            ServerTarget target = e.getValue();
            Entity entity = ((ServerWorld) (player.getWorld())).getEntity(target.target);
            if (entity == null || entity.isRemoved() || !entity.isAlive())
            {
                return true;
            }
            target.time++;
            return target.time >= SERVER_TIMEOUT;
        });
    }

    public static void serverUpdateTarget(PlayerEntity player, @Nullable UUID targetUuid)
    {
        UUID playerUuid = player.getUuid();
        if (targetUuid == null)
        {
            TARGET_MAP.remove(playerUuid);
        } else if (TARGET_MAP.containsKey(playerUuid))
        {
            ServerTarget target = TARGET_MAP.get(playerUuid);
            target.target = targetUuid;
            target.time = 0;
        } else
        {
            TARGET_MAP.put(playerUuid, new ServerTarget(targetUuid));
        }
    }

    @Environment(EnvType.CLIENT)
    public static void clientUpdateTarget(PlayerEntity player, double range)
    {
        ClientPlayerEntity clientPlayer = L2HostilityClient.getClientPlayer();
        if (player != clientPlayer ||
            clientPlayer == null)
        {
            return;
        }
        Vec3d vec3 = clientPlayer.getEyePos();
        Vec3d vec31 = clientPlayer.getRotationVec(1.0F).multiply(range);
        Vec3d vec32 = vec3.add(vec31);
        Box aabb = clientPlayer.getBoundingBox().stretch(vec31).expand(1.0D);
        double sq = range * range;
        Predicate<Entity> predicate = (e) -> (e instanceof LivingEntity) && !e.isSpectator();
        EntityHitResult result = ProjectileUtil.raycast(clientPlayer, vec3, vec32, aabb, predicate, sq);
        if (result != null && vec3.squaredDistanceTo(result.getPos()) < sq)
        {
            EnderEntityTarget.TARGET.updateTarget(result.getEntity());
        }
    }

    @Nullable
    public static LivingEntity serverGetTarget(PlayerEntity player)
    {
        if (player.getWorld().isClient())
        {
            return player.getAttacking();
        }
        UUID id = player.getUuid();
        if (!RayTraceUtil.TARGET_MAP.containsKey(id))
        {
            return null;
        }
        UUID tid = RayTraceUtil.TARGET_MAP.get(id).target;
        if (tid == null)
        {
            return null;
        }
        return (LivingEntity) (((ServerWorld) player.getWorld()).getEntity(tid));
    }

    public static class ServerTarget
    {
        public UUID target;
        public int time;

        public ServerTarget(UUID target)
        {
            this.target = target;
            time = 0;
        }
    }
}
