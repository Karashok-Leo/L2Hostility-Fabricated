package karashokleo.l2hostility.api.event;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

public interface ModifyTraitCountCapCallback
{
    Event<ModifyTraitCountCapCallback> EVENT = EventFactory.createArrayBacked(ModifyTraitCountCapCallback.class, listeners -> (cap, difficulty, entity, level) ->
    {
        for (ModifyTraitCountCapCallback listener : listeners)
        {
            cap = listener.modifyTraitCountCap(cap, difficulty, entity, level);
        }
        return cap;
    });

    int modifyTraitCountCap(int traitCountCap, MobDifficulty difficulty, LivingEntity entity, int level);
}
