package net.karashokleo.l2hostility.init;

import net.karashokleo.l2hostility.content.event.*;

public class LHEvents
{
    public static void register()
    {
        GenericEvents.register();
        ComponentEvents.register();
        MobEvents.register();
        EffectEvents.register();
        MiscEvents.register();
    }
}
