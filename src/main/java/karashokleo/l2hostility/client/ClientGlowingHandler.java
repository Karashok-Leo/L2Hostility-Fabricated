package karashokleo.l2hostility.client;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ClientGlowingHandler
{
    public static boolean isGlowing(Entity entity)
    {
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return false;
        if (diff.get().summoned) return true;
        if (entity.getWorld().isClient())
            return isGlowingImpl(diff.get().owner);
        return false;
    }

    private static int cacheTick;
    private static boolean cacheGlass;

    private static boolean playerHasGlass(PlayerEntity player)
    {
        if (player.age == cacheTick) return cacheGlass;
        cacheGlass = TrinketCompat.hasItemEquippedOrInTrinket(player, MiscItems.DETECTOR_GLASSES);
        cacheTick = player.age;
        return cacheGlass;
    }

    private static boolean isGlowingImpl(LivingEntity entity)
    {
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player != null && playerHasGlass(player))
        {
            boolean glow = entity.isInvisible() || entity.isInvisibleTo(player);
            glow |= player.hasStatusEffect(StatusEffects.BLINDNESS);
            glow |= player.hasStatusEffect(StatusEffects.DARKNESS);
            float hidden = LHConfig.client().glowingRangeHidden + entity.getWidth() * 2;
            float near = LHConfig.client().glowingRangeNear + entity.getWidth() * 2;
            double distSqr = entity.squaredDistanceTo(player);
            return distSqr <= near * near || glow && distSqr <= hidden * hidden;
        }
        return false;
    }

    @Nullable
    public static Integer getColor(Entity entity)
    {
        var diff = MobDifficulty.get(entity);
        return diff.isPresent() && diff.get().summoned ? 0xff0000 : null;
    }
}
