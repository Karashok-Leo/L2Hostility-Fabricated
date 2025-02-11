package karashokleo.l2hostility.content.event;

import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class GenericEvents
{
    private static List<BooleanSupplier> TASKS = new ArrayList<>();

    public static void register()
    {
        ServerTickEvents.END_SERVER_TICK.register(server -> execute());
        ServerTickEvents.END_SERVER_TICK.register(server -> RayTraceUtil.serverTick());
    }

    public static synchronized void schedule(Runnable runnable)
    {
        TASKS.add(() ->
        {
            runnable.run();
            return true;
        });
    }

    public static synchronized void schedulePersistent(BooleanSupplier runnable)
    {
        TASKS.add(runnable);
    }

    private static synchronized void execute()
    {
        if (TASKS.isEmpty()) return;
        var temp = TASKS;
        TASKS = new ArrayList<>();
        temp.removeIf(BooleanSupplier::getAsBoolean);
        temp.addAll(TASKS);
        TASKS = temp;
    }
}
