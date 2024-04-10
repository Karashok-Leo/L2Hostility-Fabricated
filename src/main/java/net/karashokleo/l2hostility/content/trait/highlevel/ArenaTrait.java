package net.karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.trait.common.AuraEffectTrait;
import net.karashokleo.l2hostility.init.registry.LHEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;

public class ArenaTrait extends AuraEffectTrait
{
    // 使怪物周围的玩家无法放置和破坏方块。
    // 怪物会对范围内所有生物（包括自身）施加领域压制，拥有领域压制的玩家无法放置和破坏方块，只会受到拥有领域压制实体的伤害。
    public ArenaTrait()
    {
        super(() -> LHEffects.ANTI_BUILD);
    }

    @Override
    protected boolean canApply(LivingEntity e)
    {
        return true;
    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        if (event.getSource().isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return;
        if (event.getSource().getAttacker() instanceof LivingEntity attacker)
        {
            if (attacker.hasStatusEffect(LHEffects.ANTI_BUILD))
                return;
            var diff = MobDifficulty.get(attacker);
            if (diff.isPresent() && diff.get().getTraitLevel(this) >= level)
                return;
        }
        event.setCanceled(true);
    }

    @Override
    public void onDamaged(int level, LivingEntity mob, LivingDamageEvent event)
    {
        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
                (source.getAttacker() instanceof LivingEntity le &&
                        le.hasStatusEffect(LHEffects.ANTI_BUILD)))
            return;
        event.setAmount(0);
    }
}
