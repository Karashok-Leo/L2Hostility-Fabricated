package karashokleo.l2hostility.api.event;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

/**
 * This is called when a mob is about to apply an active trait effect to a target.
 */
public interface AllowTraitEffectCallback
{
    Event<AllowTraitEffectCallback> EVENT = EventFactory.createArrayBacked(AllowTraitEffectCallback.class, listeners -> (difficulty, mob, target, trait, level) ->
    {
        for (AllowTraitEffectCallback listener : listeners)
        {
            if (!listener.allowTraitEffect(difficulty, mob, target, trait, level))
            {
                return false;
            }
        }
        return true;
    });

    boolean allowTraitEffect(MobDifficulty difficulty, LivingEntity mob, LivingEntity target, MobTrait trait, int level);
}
