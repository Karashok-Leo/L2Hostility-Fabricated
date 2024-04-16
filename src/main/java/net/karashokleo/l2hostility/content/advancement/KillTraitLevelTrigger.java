package net.karashokleo.l2hostility.content.advancement;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.content.advancement.base.BaseCriterion;
import net.karashokleo.l2hostility.content.advancement.base.BaseCriterionConditions;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHTriggers;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class KillTraitLevelTrigger extends BaseCriterion<KillTraitLevelTrigger.Condition, KillTraitLevelTrigger>
{
    public static Condition condition(MobTrait traits, int rank) {
        var ans = new Condition(LHTriggers.TRAIT_LEVEL.getId(), LootContextPredicate.EMPTY);
        ans.trait = traits;
        ans.rank = rank;
        return ans;
    }

    public KillTraitLevelTrigger(Identifier id) {
        super(id, Condition::new, Condition.class);
    }

    public void trigger(ServerPlayerEntity player, MobDifficulty cap) {
        this.trigger(player, e -> e.matchAll(cap));
    }

    @SerialClass
    public static class Condition extends BaseCriterionConditions<Condition, KillTraitLevelTrigger>
    {
        @SerialClass.SerialField
        public MobTrait trait;

        @SerialClass.SerialField
        public int rank;

        public Condition(Identifier id, LootContextPredicate player) {
            super(id, player);
        }

        public boolean matchAll(MobDifficulty cap) {
            return trait != null && cap.getTraitLevel(trait) >= rank;
        }
    }
}
