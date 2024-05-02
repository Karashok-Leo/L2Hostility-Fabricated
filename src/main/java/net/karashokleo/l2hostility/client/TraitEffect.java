package net.karashokleo.l2hostility.client;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum TraitEffect
{
    CLEAR(() -> TraitEffectHandlers::triggerClear),
    UNDYING(() -> TraitEffectHandlers::triggerUndying),
    AURA(() -> TraitEffectHandlers::triggerAura),
    ;

    public final Supplier<Consumer<TraitEffectToClient>> func;

    TraitEffect(Supplier<Consumer<TraitEffectToClient>> func)
    {
        this.func = func;
    }
}
