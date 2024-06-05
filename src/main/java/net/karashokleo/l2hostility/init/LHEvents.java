package net.karashokleo.l2hostility.init;

import net.karashokleo.l2hostility.content.event.*;

public class LHEvents
{
    public static void register()
    {
        GenericEvents.register();
        MobEvents.register();
        TraitEvents.register();
        EffectEvents.register();
        MiscEvents.register();
    }
}
