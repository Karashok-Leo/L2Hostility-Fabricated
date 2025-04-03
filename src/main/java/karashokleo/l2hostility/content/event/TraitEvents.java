package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class TraitEvents
{
    public static void register()
    {
        DamageSourceCreateCallback.EVENT.register(TraitEvents::onDamageSourceCreate);

        // damage()头部
        LivingAttackEvent.ATTACK.register(TraitEvents::onAttacking);
        LivingAttackEvent.ATTACK.register(TraitEvents::onAttacked);

        // applyDamage()头部
        LivingHurtEvent.HURT.register(TraitEvents::onHurting);
        LivingHurtEvent.HURT.register(TraitEvents::onHurt);

        // applyDamage()尾部
        LivingDamageEvent.DAMAGE.register(TraitEvents::onDamaged);

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register(TraitEvents::onKilled);
        ServerLivingEntityEvents.AFTER_DEATH.register(TraitEvents::afterKilled);
        ServerLivingEntityEvents.AFTER_DEATH.register(TraitEvents::onDeath);
        ServerLivingEntityEvents.ALLOW_DEATH.register(TraitEvents::allowDeath);
    }

    public static void onDamageSourceCreate(RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, @Nullable Vec3d position, DamageSource damageSource)
    {
        var optional = MobDifficulty.get(attacker);
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onDamageSourceCreate(difficulty, difficulty.owner, v, damageSource, type, source, position));
    }

    public static void onAttacking(LivingAttackEvent event)
    {
        if (event.getEntity().getWorld().isClient()) return;
        var optional = MobDifficulty.get(event.getSource().getAttacker());
        if (optional.isEmpty()) return;
        MobDifficulty attackerDifficulty = optional.get();
        attackerDifficulty.traitEvent((k, v) -> k.onAttacking(attackerDifficulty, attackerDifficulty.owner, v, event));
    }

    public static void onAttacked(LivingAttackEvent event)
    {
        if (event.getEntity().getWorld().isClient()) return;
        var optional = MobDifficulty.get(event.getEntity());
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onAttacked(difficulty, difficulty.owner, v, event));
    }

    public static void onHurting(LivingHurtEvent event)
    {
        if (event.getEntity().getWorld().isClient()) return;
        var optional = MobDifficulty.get(event.getSource().getAttacker());
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.postHurting(difficulty, difficulty.owner, v, event));
    }

    public static void onHurt(LivingHurtEvent event)
    {
        if (event.getEntity().getWorld().isClient()) return;
        var optional = MobDifficulty.get(event.getEntity());
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onHurt(difficulty, difficulty.owner, v, event));
    }

    public static void onDamaged(LivingDamageEvent event)
    {
        if (event.getEntity().getWorld().isClient()) return;
        var optional = MobDifficulty.get(event.getEntity());
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onDamaged(difficulty, difficulty.owner, v, event));
    }

    public static void onKilled(ServerWorld world, Entity entity, LivingEntity killed)
    {
        var optional = MobDifficulty.get(entity);
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onKilled(difficulty, difficulty.owner, v, killed));
    }

    public static void afterKilled(LivingEntity entity, DamageSource source)
    {
        var optional = MobDifficulty.get(source.getAttacker());
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.afterKilling(difficulty, difficulty.owner, v, entity, source));
    }

    public static void onDeath(LivingEntity entity, DamageSource source)
    {
        var optional = MobDifficulty.get(entity);
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onDeath(difficulty, entity, v, source));
    }

    public static boolean allowDeath(LivingEntity entity, DamageSource source, float amount)
    {
        var optional = MobDifficulty.get(entity);
        if (optional.isEmpty()) return true;
        MobDifficulty difficulty = optional.get();
        for (var e : difficulty.traits.entrySet())
            if (!e.getKey().allowDeath(difficulty, entity, e.getValue(), source, amount))
                return false;
        return true;
    }
}
