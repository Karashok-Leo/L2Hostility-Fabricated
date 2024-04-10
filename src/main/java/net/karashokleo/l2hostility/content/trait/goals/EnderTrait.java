package net.karashokleo.l2hostility.content.trait.goals;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.trait.legendary.LegendaryTrait;
import net.karashokleo.l2hostility.init.data.LHDamageTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public class EnderTrait extends LegendaryTrait
{
    public EnderTrait()
    {
        super(Formatting.DARK_PURPLE);
    }

    @Override
    public void tick(LivingEntity mob, int level)
    {
        if (mob.getWorld().isClient()) return;
        int duration = LHConfig.common().traits.teleportDuration;
        int r = LHConfig.common().traits.teleportRange;
        if (mob.age % duration == 0 && mob instanceof MobEntity m && m.getTarget() != null)
        {
            Vec3d old = mob.getPos();
            Vec3d target = m.getTarget().getPos();
            if (target.distanceTo(old) > r)
                target = target.subtract(old).normalize().multiply(r).add(old);
            var event = new EntityEvents.Teleport.EntityTeleportEvent(m, target.getX(), target.getY(), target.getZ());
            event.sendEvent();
            if (event.isCanceled()) return;
            mob.teleport(target.getX(), target.getY(), target.getZ());
            if (!mob.getWorld().isSpaceEmpty(mob))
            {
                mob.teleport(old.getX(), old.getY(), old.getZ());
                return;
            }
            mob.getWorld().emitGameEvent(GameEvent.TELEPORT, m.getPos(), GameEvent.Emitter.of(mob));
            if (!mob.isSilent())
            {
                mob.getWorld().playSound(null, mob.prevX, mob.prevY, mob.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, mob.getSoundCategory(), 1.0F, 1.0F);
                mob.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        DamageSource source = event.getSource();
        if (LHDamageTypes.isMagic(source) ||
                LHDamageTypes.bypasses(source)) return;
        if (teleport(entity) || source.isIn(DamageTypeTags.IS_PROJECTILE))
            event.setCanceled(true);
    }

    private static boolean teleport(LivingEntity entity)
    {
        int r = LHConfig.common().traits.teleportRange;
        if (!entity.getWorld().isClient() && entity.isAlive() && r > 0)
        {
            double d0 = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * r * 2;
            double d1 = entity.getY() + (double) (entity.getRandom().nextInt(r * 2) - r);
            double d2 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * r * 2;
            return teleport(entity, d0, d1, d2);
        } else return false;
    }

    private static boolean teleport(LivingEntity entity, double pX, double pY, double pZ)
    {
        BlockPos.Mutable ipos = new BlockPos.Mutable(pX, pY, pZ);
        while (ipos.getY() > entity.getWorld().getBottomY() && !entity.getWorld().getBlockState(ipos).blocksMovement())
            ipos.move(Direction.DOWN);

        BlockState blockstate = entity.getWorld().getBlockState(ipos);
        boolean flag = blockstate.blocksMovement();
        boolean flag1 = blockstate.getFluidState().isIn(FluidTags.WATER);
        if (flag && !flag1)
        {
            var event = new EntityEvents.Teleport.EntityTeleportEvent(entity, pX, pY, pZ);
            event.sendEvent();
            if (event.isCanceled()) return false;
            Vec3d vec3 = entity.getPos();
            boolean flag2 = entity.teleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2)
            {
                entity.getWorld().emitGameEvent(GameEvent.TELEPORT, vec3, GameEvent.Emitter.of(entity));
                if (!entity.isSilent())
                {
                    entity.getWorld().playSound(null, entity.prevX, entity.prevY, entity.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, entity.getSoundCategory(), 1.0F, 1.0F);
                    entity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }
            return flag2;
        } else return false;
    }
}
