package karashokleo.l2hostility.content.trait.legendary;

import karashokleo.leobrary.effect.api.event.LivingHeal;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class UndyingTrait extends LegendaryTrait
{
    public static void undyingEvent()
    {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) ->
        {
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) return true;
            var diff = MobDifficulty.get(entity);
            if (diff.isEmpty()) return true;
            if (diff.get().hasTrait(LHTraits.SPLIT)) return true;
            if (diff.get().hasTrait(LHTraits.UNDYING) && validTarget(entity))
            {
                CallbackInfo ci = new CallbackInfo("UndyingTraitHeal", true);
                LivingHeal.EVENT.invoker().onLivingHeal(entity, entity.getMaxHealth()-entity.getHealth(), ci);
                if (ci.isCancelled()) return true;
                entity.setHealth(entity.getMaxHealth());
                LHNetworking.toTracking(entity, new S2CUndying(entity));
                return false;
            } else return true;
        });
    }

    public static boolean validTarget(LivingEntity le)
    {
        return !(le instanceof EnderDragonEntity) &&
                le.canHaveStatusEffect(new StatusEffectInstance(LHEffects.CURSE, 100));
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
