package karashokleo.l2hostility.content.loot.condition;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.generate.TraitGLMProvider;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

@SerialClass
public class TraitLootCondition implements LootCondition
{
    @SerialClass.SerialField
    public MobTrait trait;
    @SerialClass.SerialField
    public int minLevel;
    @SerialClass.SerialField
    public int maxLevel;

    @Deprecated
    public TraitLootCondition()
    {
    }

    public TraitLootCondition(MobTrait trait, int minLevel, int maxLevel)
    {
        this.trait = trait;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    @Override
    public LootConditionType getType()
    {
        return TraitGLMProvider.TRAIT_AND_LEVEL;
    }

    @Override
    public boolean test(LootContext context)
    {
        return MobDifficulty.get(context.get(LootContextParameters.THIS_ENTITY))
                .filter(diff ->{
                    int lv=diff.getTraitLevel(trait);
                    return lv>0&&lv >= minLevel && lv <= maxLevel;
                }).isPresent();
    }
}
