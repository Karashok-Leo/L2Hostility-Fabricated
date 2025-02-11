package karashokleo.l2hostility.content.advancement;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.advancement.base.BaseCriterion;
import karashokleo.l2hostility.content.advancement.base.BaseCriterionConditions;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class KillTraitEffectTrigger extends BaseCriterion<KillTraitEffectTrigger.Condition, KillTraitEffectTrigger>
{
    public KillTraitEffectTrigger(Identifier id)
    {
        super(id, Condition::new, Condition.class);
    }

    public static Condition condition(MobTrait traits, StatusEffect effect)
    {
        var ans = new Condition(LHTriggers.TRAIT_EFFECT.getId(), LootContextPredicate.EMPTY);
        ans.trait = traits;
        ans.effect = effect;
        return ans;
    }

    public void trigger(ServerPlayerEntity player, LivingEntity le, MobDifficulty cap)
    {
        this.trigger(player, e -> e.matchAll(le, cap));
    }

    @SerialClass
    public static class Condition extends BaseCriterionConditions<Condition, KillTraitEffectTrigger>
    {

        @SerialClass.SerialField
        public MobTrait trait;

        @SerialClass.SerialField
        public StatusEffect effect;

        public Condition(Identifier id, LootContextPredicate player)
        {
            super(id, player);
        }

        public boolean matchAll(LivingEntity le, MobDifficulty cap)
        {
            return cap.hasTrait(trait) && le.hasStatusEffect(effect);
        }
    }
}