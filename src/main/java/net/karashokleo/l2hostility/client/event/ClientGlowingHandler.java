package net.karashokleo.l2hostility.client.event;

import net.karashokleo.l2hostility.client.L2HostilityClient;
import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.registry.LHItems;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
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

    private static boolean isGlowingImpl(LivingEntity entity)
    {
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player != null && TrinketCompat.hasItemEquippedOrInTrinket(player, LHItems.DETECTOR_GLASSES))
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
