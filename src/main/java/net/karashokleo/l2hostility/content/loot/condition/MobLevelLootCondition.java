package net.karashokleo.l2hostility.content.loot.condition;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.data.generate.TraitGLMProvider;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

@SerialClass
public class MobLevelLootCondition implements LootCondition
{
    @SerialClass.SerialField
    public int minLevel;
    @SerialClass.SerialField
    public int maxLevel;

    @Deprecated
    public MobLevelLootCondition()
    {
    }

    public MobLevelLootCondition(int minLevel)
    {
        this.minLevel = minLevel;
    }

    public MobLevelLootCondition(int minLevel, int maxLevel)
    {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    public LootConditionType getType()
    {
        return TraitGLMProvider.MOB_LEVEL;
    }

    @Override
    public boolean test(LootContext context)
    {
        return MobDifficulty.get(context.get(LootContextParameters.THIS_ENTITY))
                .filter(diff ->
                {
                    int lv = diff.getLevel();
                    return (minLevel <= 0 || lv >= minLevel) &&
                            (maxLevel <= 0 || lv < maxLevel);
                }).isPresent();
    }
}
