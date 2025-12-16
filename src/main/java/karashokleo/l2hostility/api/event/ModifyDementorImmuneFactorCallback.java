package karashokleo.l2hostility.api.event;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public interface ModifyDementorImmuneFactorCallback
{
    Event<ModifyDementorImmuneFactorCallback> EVENT = EventFactory.createArrayBacked(ModifyDementorImmuneFactorCallback.class, listeners -> (difficulty, entity, level, source, amount, factor) ->
    {
        for (ModifyDementorImmuneFactorCallback listener : listeners)
        {
            factor = listener.modifyDementorImmuneFactor(difficulty, entity, level, source, amount, factor);
        }
        return factor;
    });

    double modifyDementorImmuneFactor(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource source, float amount, double factor);
}
