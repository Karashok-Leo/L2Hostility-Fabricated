package karashokleo.l2hostility.content.advancement;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.advancement.base.BaseCriterion;
import karashokleo.l2hostility.content.advancement.base.BaseCriterionConditions;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public class KillTraitFlameTrigger extends BaseCriterion<KillTraitFlameTrigger.Condition, KillTraitFlameTrigger>
{
    public enum Type
    {
        FLAME(Entity::isOnFire);

        private final Predicate<LivingEntity> pred;

        Type(Predicate<LivingEntity> pred)
        {
            this.pred = pred;
        }

        public boolean match(LivingEntity le)
        {
            return pred.test(le);
        }
    }

    public static Condition condition(MobTrait traits, Type effect)
    {
        var ans = new Condition(LHTriggers.TRAIT_FLAME.getId(), LootContextPredicate.EMPTY);
        ans.trait = traits;
        ans.effect = effect;
        return ans;
    }

    public KillTraitFlameTrigger(Identifier id)
    {
        super(id, Condition::new, Condition.class);
    }

    public void trigger(ServerPlayerEntity player, LivingEntity le, MobDifficulty cap)
    {
        this.trigger(player, e -> e.matchAll(le, cap));
    }

    @SerialClass
    public static class Condition extends BaseCriterionConditions<Condition, KillTraitFlameTrigger>
    {
        @SerialClass.SerialField
        public MobTrait trait;

        @SerialClass.SerialField
        public Type effect;

        public Condition(Identifier id, LootContextPredicate player)
        {
            super(id, player);
        }

        public boolean matchAll(LivingEntity le, MobDifficulty cap)
        {
            return cap.hasTrait(trait) && effect.match(le);
        }
    }
}
