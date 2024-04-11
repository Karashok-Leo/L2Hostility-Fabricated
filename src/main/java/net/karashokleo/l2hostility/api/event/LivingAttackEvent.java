package net.karashokleo.l2hostility.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class LivingAttackEvent
{
    public static final Event<io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent.LivingAttackCallback> ATTACK = EventFactory.createArrayBacked(io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent.LivingAttackCallback.class, callbacks -> event ->
    {
        for (io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent.LivingAttackCallback e : callbacks)
            e.onLivingAttack(event);
    });

    private final LivingEntity entity;
    private final DamageSource source;
    private final float amount;

    public LivingAttackEvent(LivingEntity entity, DamageSource source, float amount)
    {
        this.entity = entity;
        this.source = source;
        this.amount = amount;
    }

    public DamageSource getSource()
    {
        return source;
    }

    public float getAmount()
    {
        return amount;
    }

//    @Override
//    public void sendEvent() {
//        ATTACK.invoker().onLivingAttack(this);
//    }

    @FunctionalInterface
    public interface LivingAttackCallback
    {
        void onLivingAttack(io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent event);
    }
}
