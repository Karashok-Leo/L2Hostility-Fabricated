package net.karashokleo.l2hostility.content.advancement;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.content.advancement.base.BaseCriterion;
import net.karashokleo.l2hostility.content.advancement.base.BaseCriterionConditions;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.registry.LHTriggers;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class KillTraitsTrigger extends BaseCriterion<KillTraitsTrigger.Condition, KillTraitsTrigger>
{
    public static Condition condition(MobTrait... traits)
    {
        var ans = new Condition(LHTriggers.KILL_TRAITS.getId(), LootContextPredicate.EMPTY);
        ans.traits = traits;
        return ans;
    }

    public KillTraitsTrigger(Identifier id)
    {
        super(id, Condition::new, Condition.class);
    }

    public void trigger(ServerPlayerEntity player, MobDifficulty cap)
    {
        this.trigger(player, e -> e.matchAll(cap));
    }

    @SerialClass
    public static class Condition extends BaseCriterionConditions<Condition, KillTraitsTrigger>
    {
        @SerialClass.SerialField
        public MobTrait[] traits;

        public Condition(Identifier id, LootContextPredicate player)
        {
            super(id, player);
        }

        public boolean matchAll(MobDifficulty cap)
        {
            if (cap.traits.isEmpty()) return false;
            for (var e : traits)
            {
                if (!cap.hasTrait(e))
                {
                    return false;
                }
            }
            return true;
        }
    }
}