package net.karashokleo.l2hostility.content.trait.legendary;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.registry.LHEffects;
import net.karashokleo.l2hostility.init.registry.LHTraits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Formatting;

public class UndyingTrait extends LegendaryTrait
{
    public static final ServerLivingEntityEvents.AllowDeath allowDeath = (entity, source, amount) ->
    {
        if (entity.getWorld().isClient()) return true;
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) return true;
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return true;
        if (diff.get().hasTrait(LHTraits.SPLIT)) return true;
        if (!validTarget(entity)) return true;
        entity.heal(entity.getMaxHealth() - entity.getHealth());
        return !entity.isAlive();
    };

    public static boolean validTarget(LivingEntity le)
    {
        if (le instanceof EnderDragonEntity)
            return false;
        return le.canHaveStatusEffect(new StatusEffectInstance(LHEffects.CURSE, 100));
    }

    // 传奇词条，使怪物拥有无限续杯的不死图腾。
    // 怪物死亡时会试图使用不死图腾来使自己满血复活。
    public UndyingTrait()
    {
        super(Formatting.DARK_BLUE);
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return validTarget(le) && super.allow(le, difficulty, maxModLv);
    }
}
