package karashokleo.l2hostility.util;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

import java.util.Set;

import static karashokleo.l2hostility.L2Hostility.LOGGER;

public class DebugUtil
{
    /**
     * copy from MixinTrace
     *
     * @param className the class name
     */
    public static void printMixinsForClass(String className)
    {
        try
        {
            boolean found = false;

            ClassInfo classInfo = ClassInfo.fromCache(className);
            if (classInfo != null)
            {
                // Workaround for bug in Mixin, where it adds to the wrong thing :(
                Object mixinInfoSetObject;
                var mixinsField = ClassInfo.class.getDeclaredField("mixins");
                mixinsField.setAccessible(true);
                mixinInfoSetObject = mixinsField.get(classInfo);

                @SuppressWarnings("unchecked") Set<IMixinInfo> mixinInfoSet = (Set<IMixinInfo>) mixinInfoSetObject;

                if (!mixinInfoSet.isEmpty())
                {
                    LOGGER.info("[Debug Info for Mixin] Found Mixin metadata for {}:", className);
                    for (IMixinInfo info : mixinInfoSet)
                    {
                        LOGGER.info("\t{}\t{}", info.getClassName(), info.getConfig().getName());
                        found = true;
                    }
                }
            }

            if (!found) LOGGER.info("[Debug Info for Mixin] No Mixin metadata found for {}", className);

        } catch (Exception e)
        {
            LOGGER.error("[Debug Info for Mixin] Failed to find Mixin metadata: ", e);
        }
    }
}
