package karashokleo.l2hostility.content.logic;

import karashokleo.l2hostility.init.LHConfig;

public record LevelEditor(DifficultyLevel difficulty, int extra)
{
    public boolean setBase(int level)
    {
        int old = difficulty().level;
        difficulty().level = level;
        difficulty().experience = 0;
        return level != old;
    }

    public boolean addBase(int level)
    {
        int old = difficulty().level;
        difficulty().level = Math.min(LHConfig.common().difficulty.maxPlayerLevel, Math.max(0, difficulty().level + level));
        if (level < 0)
        {
            difficulty().experience = 0;
        }
        return difficulty().level != old;
    }

    public int getBase()
    {
        return difficulty().level;
    }

    public boolean setTotal(int level)
    {
        return addTotal(level - getTotal());
    }

    public boolean addTotal(int level)
    {
        int old = difficulty().extraLevel;
        difficulty().extraLevel += level;
        return old != difficulty().extraLevel;
    }

    public int getTotal()
    {
        return difficulty().getLevel() + extra;
    }
}
