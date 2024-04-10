package net.karashokleo.l2hostility.content.logic;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.config.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

@SerialClass
public class DifficultyLevel
{
    protected long experience;
    @SerialClass.SerialField
    public int level;
    @SerialClass.SerialField
    public int extraLevel;

    public static DifficultyLevel merge(DifficultyLevel difficulty, int extraLevel)
    {
        DifficultyLevel ans = new DifficultyLevel();
        ans.level = difficulty.level;
        ans.experience = difficulty.experience;
        ans.extraLevel = difficulty.extraLevel + extraLevel;
        return ans;
    }

    public static int ofAny(LivingEntity entity)
    {
        if (entity instanceof PlayerEntity player)
            return PlayerDifficulty.get(player).getLevel().getLevel();
        if (entity instanceof MobEntity mob)
        {
            var diff = MobDifficulty.get(mob);
            if (diff.isPresent()) return diff.get().getLevel();
        }
        return 0;
    }

    // 难度增长
    public void grow(double growFactor, MobDifficulty cap)
    {
        if (level >= LHConfig.common().difficulty.maxPlayerLevel)
        {
            level = LHConfig.common().difficulty.maxPlayerLevel;
            experience = 0;
            return;
        }
        experience += (int) (growFactor * cap.getLevel() * cap.getLevel());
        int factor = LHConfig.common().difficulty.killsPerLevel;
        while (experience >= (long) level * level * factor)
        {
            experience -= (long) level * level * factor;
            level++;
        }
        if (level >= LHConfig.common().difficulty.maxPlayerLevel)
        {
            level = LHConfig.common().difficulty.maxPlayerLevel;
            experience = 0;
        }
    }

    public void decay()
    {
        double rate = LHConfig.common().difficulty.playerDeathDecay;
        if (rate < 1)
            level = Math.max(0, level - Math.max(1, (int) Math.ceil(level * (1 - rate))));
        experience = 0;
    }

    public long getMaxExp()
    {
        int factor = LHConfig.common().difficulty.killsPerLevel;
        return Math.max(1L, (long) level * level * factor);
    }

    public int getLevel()
    {
        return Math.max(0, level + extraLevel);
    }

    public long getExp()
    {
        return experience;
    }

    public String getStr()
    {
        return extraLevel == 0 ? "" + level : extraLevel > 0 ? level + "+" + extraLevel : level + "" + extraLevel;
    }
}
