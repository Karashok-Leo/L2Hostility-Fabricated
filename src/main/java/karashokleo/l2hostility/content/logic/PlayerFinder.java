package karashokleo.l2hostility.content.logic;

import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PlayerFinder
{
    @Nullable
    public static PlayerEntity getNearestPlayer(World world, LivingEntity le)
    {
        int safeZone = LHConfig.common().difficulty.newPlayerProtectRange;
        int sr = safeZone * safeZone;

        int lowLv = 0;
        PlayerEntity lowPl = null;
        double nearDist = 0;
        PlayerEntity nearPl = null;
        for (var pl : world.getPlayers())
        {
            double dist = pl.squaredDistanceTo(le);
            if (dist > 128 * 128)
            {
                continue;
            }
            if (!pl.isAlive())
            {
                continue;
            }
            var plOpt = PlayerDifficulty.get(pl);
            int lv = plOpt.getLevel().getLevel();
            if (dist < sr)
            {
                if (lowPl == null || lv < lowLv)
                {
                    lowPl = pl;
                    lowLv = lv;
                }
            } else
            {
                if (nearPl == null || dist < nearDist)
                {
                    nearPl = pl;
                    nearDist = dist;
                }
            }
        }
        if (lowPl != null)
        {
            return lowPl;
        } else
        {
            return nearPl;
        }
    }
}
