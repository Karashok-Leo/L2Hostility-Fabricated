package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.legendary.UndyingTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
import karashokleo.leobrary.damage.api.modify.DamageModifier;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class TraitEvents
{
    public static void register()
    {
        // 等级伤害加成
        DamagePhase.ARMOR.registerModifier(
                1000,
                damageAccess -> MobDifficulty.get(damageAccess.getAttacker()).ifPresent(diff ->
                {
                    int level = diff.getLevel();
                    double factor = LHConfig.common().scaling.exponentialDamage ?
                            Math.pow(1 + LHConfig.common().scaling.damageFactor, level) :
                            1 + level * LHConfig.common().scaling.damageFactor;
                    damageAccess.addModifier(DamageModifier.multiply((float) factor));
                })
        );

        DamageSourceCreateCallback.EVENT.register((type, source, attacker, position, damageSource) -> {
            MobDifficulty.get(attacker).ifPresent(diff -> diff.traitEvent((k, v) -> k.onDamageSourceCreate(v, diff.owner, damageSource, type, source, position)));
        });

        // damage()头部
        LivingAttackEvent.ATTACK.register(event ->
        {
            MobDifficulty.get(event.getSource().getAttacker()).ifPresent(diff -> diff.traitEvent((k, v) -> k.onAttacking(v, diff.owner, event)));
            MobDifficulty.get(event.getEntity()).ifPresent(diff -> diff.traitEvent((k, v) -> k.onAttacked(v, diff.owner, event)));
        });

        // applyDamage()头部
        LivingHurtEvent.HURT.register(event ->
        {
            MobDifficulty.get(event.getSource().getAttacker()).ifPresent(diff -> diff.traitEvent((k, v) -> k.postHurting(v, diff.owner, event)));
            MobDifficulty.get(event.getEntity()).ifPresent(diff -> diff.traitEvent((k, v) -> k.onHurt(v, diff.owner, event)));
        });

        // applyDamage()尾部
        LivingDamageEvent.DAMAGE.register(event ->
                MobDifficulty.get(event.getEntity()).ifPresent(diff -> diff.traitEvent((k, v) -> k.onDamaged(v, diff.owner, event)))
        );

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) ->
                MobDifficulty.get(entity).ifPresent(diff -> diff.traitEvent((k, v) -> k.onDeath(v, entity, source)))
        );

        UndyingTrait.undyingEvent();
    }
}
