package karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.common.AuraEffectTrait;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
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
    public void onAttacked(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event)
    {
        if (event.getSource().isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return;
        if (event.getSource().getAttacker() instanceof LivingEntity attacker)
        {
            if (attacker.hasStatusEffect(LHEffects.ANTI_BUILD))
                return;
            if (attacker instanceof PlayerEntity player && player.getAbilities().creativeMode)
                return;
            var attackerDifficulty = MobDifficulty.get(attacker);
            if (attackerDifficulty.isPresent() &&
                attackerDifficulty.get().getTraitLevel(this) >= level)
                return;
        }
        event.setCanceled(true);
    }

    @Override
    public void onDamaged(MobDifficulty difficulty, LivingEntity mob, int level, LivingDamageEvent event)
    {
        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
            (source.getAttacker() instanceof LivingEntity le &&
             le.hasStatusEffect(LHEffects.ANTI_BUILD)) ||
            (source.getAttacker() instanceof PlayerEntity player &&
             player.getAbilities().creativeMode))
            return;
        event.setAmount(0);
    }
}
