package karashokleo.l2hostility.content.logic;

import karashokleo.leobrary.damage.api.state.DamageState;

import java.util.function.Predicate;

public class ReflectState implements DamageState
{
    public static Predicate<DamageState> PREDICATE = state -> state instanceof ReflectState;

    @Override
    public String toString()
    {
        return "DamageState$Reflect";
    }
}
