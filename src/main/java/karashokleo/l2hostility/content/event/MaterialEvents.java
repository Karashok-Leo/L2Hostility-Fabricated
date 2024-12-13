package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.PlayerInteractionEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;

public class MaterialEvents
{
    public static void register()
    {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) ->
        {
            if (entity instanceof EndermanEntity ender &&
                damageSource.getAttacker() instanceof PlayerEntity &&
                ender.isAngry() &&
                ender.getSteppingPos().getY() <= ender.getWorld().getBottomY() - LHConfig.common().complements.materials.belowVoid)
                ender.dropStack(ComplementItems.VOID_EYE.getDefaultStack());

            if (entity instanceof PhantomEntity phantom)
            {
                World level = phantom.getWorld();
                if (level.isDay() &&
                    level.isSkyVisible(phantom.getSteppingPos()) &&
                    phantom.isOnFire()
                    && phantom.getSteppingPos().getY() >= level.getTopY() + LHConfig.common().complements.materials.phantomHeight)
                    phantom.dropStack(ComplementItems.SUN_MEMBRANE.getDefaultStack());

                if (damageSource.isIn(DamageTypeTags.IS_EXPLOSION))
                    phantom.dropStack(ComplementItems.STORM_CORE.getDefaultStack());
            }

            if (entity instanceof DrownedEntity drowned
                && damageSource.isIn(DamageTypeTags.IS_FREEZING))
                drowned.dropStack(ComplementItems.HARD_ICE.getDefaultStack());

            if (entity instanceof PiglinBruteEntity brute &&
                brute.hasStatusEffect(LHEffects.STONE_CAGE))
                brute.dropStack(ComplementItems.BLACKSTONE_CORE.getDefaultStack());

            if (entity instanceof ElderGuardianEntity guardian &&
                damageSource.isIn(DamageTypeTags.IS_LIGHTNING))
                guardian.dropStack(ComplementItems.GUARDIAN_EYE.getDefaultStack());

            if (entity instanceof WitherEntity wither &&
                damageSource.isIn(DamageTypeTags.IS_PROJECTILE))
                wither.dropStack(ComplementItems.FORCE_FIELD.getDefaultStack());

            if (entity instanceof WardenEntity warden &&
                damageSource.getAttacker() instanceof PlayerEntity)
                warden.dropStack(ComplementItems.WARDEN_BONE_SHARD.getDefaultStack());

            if (entity instanceof GhastEntity ghast &&
                ghast.hasStatusEffect(LHEffects.FLAME))
                ghast.dropStack(ComplementItems.SOUL_FLAME.getDefaultStack());
        });

        LivingHurtEvent.HURT.register(event ->
        {
            if (event.getSource().isIn(DamageTypeTags.IS_PROJECTILE) &&
                event.getSource().getAttacker() instanceof PlayerEntity &&
                LHConfig.common().complements.materials.enableSpaceShard &&
                event.getAmount() >= LHConfig.common().complements.materials.spaceDamage)
                event.getEntity().dropStack(ComplementItems.SPACE_SHARD.getDefaultStack());
        });

        LivingDamageEvent.DAMAGE.register(event ->
        {
            if (event.getEntity() instanceof ChickenEntity chicken &&
                event.getSource().getName().equals("sonic_boom") &&
                event.getAmount() < chicken.getHealth() + chicken.getAbsorptionAmount())
                chicken.dropStack(ComplementItems.RESONANT_FEATHER.getDefaultStack());
        });

        PlayerInteractionEvents.INTERACT_ENTITY_GENERAL.register((player, target, hand) ->
        {
            World world = player.getWorld();
            ItemStack stack = player.getStackInHand(hand);
            if (!world.isClient() &&
                stack.isOf(ComplementItems.WIND_BOTTLE) &&
                target instanceof ShulkerBulletEntity bullet)
            {
                bullet.damage(world.getDamageSources().playerAttack(player), 1);
                stack.decrement(1);
                player.getInventory().offerOrDrop(ComplementItems.CAPTURED_BULLET.getDefaultStack());
            }
            return null;
        });
    }

    public static void dropExplosionShard(PlayerEntity player, DamageSource source, float preDamage, float finalDamage)
    {
        if (source.isIn(DamageTypeTags.IS_EXPLOSION) &&
            preDamage >= LHConfig.common().complements.materials.explosionDamage &&
            finalDamage < player.getHealth() + player.getAbsorptionAmount())
            player.getInventory().offerOrDrop(ComplementItems.EXPLOSION_SHARD.getDefaultStack());
    }
}
