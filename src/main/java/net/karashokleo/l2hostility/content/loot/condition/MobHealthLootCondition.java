package net.karashokleo.l2hostility.content.loot.condition;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.data.generate.TraitGLMProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

@SerialClass
public class MobHealthLootCondition implements LootCondition
{
    @SerialClass.SerialField
    public int minHealth;

    @Deprecated
    public MobHealthLootCondition()
    {
    }

    public MobHealthLootCondition(int minHealth)
    {
        this.minHealth = minHealth;
    }

    @Override
    public LootConditionType getType()
    {
        return TraitGLMProvider.MIN_HEALTH;
    }

    @Override
    public boolean test(LootContext context)
    {
        return context.get(LootContextParameters.THIS_ENTITY) instanceof LivingEntity le && le.getMaxHealth() >= minHealth;
    }
}
