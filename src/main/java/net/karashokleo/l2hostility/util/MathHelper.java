package net.karashokleo.l2hostility.util;

import net.karashokleo.l2hostility.L2Hostility;

import java.util.Random;
import java.util.UUID;

public class MathHelper
{
    public static UUID getUUIDFromString(String str)
    {
        int hash = str.hashCode();
        Random r = new Random(hash);
        long l0 = r.nextLong();
        long l1 = r.nextLong();
        return new UUID(l0, l1);
    }

    public static UUID getUUIDFromIdentifier(String path)
    {
        return getUUIDFromString(L2Hostility.MOD_ID + ":" + path);
    }
}
