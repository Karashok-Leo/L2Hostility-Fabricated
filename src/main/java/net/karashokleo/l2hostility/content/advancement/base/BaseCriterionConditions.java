package net.karashokleo.l2hostility.content.advancement.base;

import com.google.gson.JsonObject;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.util.Identifier;

@SerialClass
public class BaseCriterionConditions<T extends BaseCriterionConditions<T, R>, R extends BaseCriterion<T, R>> extends AbstractCriterionConditions
{
    public BaseCriterionConditions(Identifier id, LootContextPredicate player)
    {
        super(id, player);
    }

    @Override
    public JsonObject toJson(AdvancementEntityPredicateSerializer serializer)
    {
        JsonObject obj = super.toJson(serializer);
        JsonCodec.toJsonObject(this, obj);
        return obj;
    }
}
