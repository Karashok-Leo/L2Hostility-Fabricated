package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.legendary.UndyingTrait;

public class TraitEvents
{
    public static void register()
    {
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
