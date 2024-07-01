package karashokleo.l2hostility.init;

import karashokleo.l2hostility.content.event.*;

public class LHEvents
{
    public static void register()
    {
        GenericEvents.register();
        TraitEvents.register();
        DifficultyEvents.register();
        TrinketEvents.register();
        EffectEvents.register();
        MaterialEvents.register();
        MiscEvents.register();
    }
}
