package net.karashokleo.l2hostility.content.advancement.base;

import com.google.gson.JsonObject;
import dev.xkmc.l2serial.serialization.codec.JsonCodec;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public class BaseCriterion<T extends BaseCriterionConditions<T, R>, R extends BaseCriterion<T, R>> extends AbstractCriterion<T>
{
    private final Identifier id;
    private final BiFunction<Identifier, LootContextPredicate, T> factory;
    private final Class<T> cls;

    public BaseCriterion(Identifier id, BiFunction<Identifier, LootContextPredicate, T> factory, Class<T> cls)
    {
        this.id = id;
        this.factory = factory;
        this.cls = cls;
    }

    @Override
    protected T conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer)
    {
        T ans = factory.apply(id, playerPredicate);
        JsonCodec.from(obj, cls, ans);
        return ans;
    }

    @Override
    public Identifier getId()
    {
        return id;
    }
}
