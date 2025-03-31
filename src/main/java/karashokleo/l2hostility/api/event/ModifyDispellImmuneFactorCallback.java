package karashokleo.l2hostility.api.event;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface ModifyDispellImmuneFactorCallback
{
    Event<ModifyDispellImmuneFactorCallback> EVENT = EventFactory.createArrayBacked(ModifyDispellImmuneFactorCallback.class, listeners -> (difficulty, entity, level, source, amount, factor) ->
    {
        for (ModifyDispellImmuneFactorCallback listener : listeners)
            factor = listener.modifyDispellImmuneFactor(difficulty, entity, level, source, amount, factor);
        return factor;
    });

    double modifyDispellImmuneFactor(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource source, float amount, double factor);
}
