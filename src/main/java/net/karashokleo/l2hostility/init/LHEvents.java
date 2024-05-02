package net.karashokleo.l2hostility.init;

import net.karashokleo.l2hostility.content.event.ComponentEvents;
import net.karashokleo.l2hostility.content.event.GenericEvents;
import net.karashokleo.l2hostility.content.event.MiscEvents;
import net.karashokleo.l2hostility.content.event.MobEvents;

public class LHEvents
{
    public static void register()
    {
        GenericEvents.register();
        ComponentEvents.register();
        MobEvents.register();
        MiscEvents.register();
    }
}
