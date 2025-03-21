package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.legendary.UndyingTrait;
import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
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

        ServerLivingEntityEvents.AFTER_DEATH.register(TraitEvents::afterDeath);
        ServerLivingEntityEvents.ALLOW_DEATH.register(UndyingTrait::tryTriggerUndying);
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

    public static void afterDeath(LivingEntity entity, DamageSource source)
    {
        if (entity.getWorld().isClient()) return;
        var optional = MobDifficulty.get(entity);
        if (optional.isEmpty()) return;
        MobDifficulty difficulty = optional.get();
        difficulty.traitEvent((k, v) -> k.onDeath(difficulty, entity, v, source));
    }
}
