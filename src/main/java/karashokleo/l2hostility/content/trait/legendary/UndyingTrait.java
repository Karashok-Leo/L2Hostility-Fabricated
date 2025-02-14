package karashokleo.l2hostility.content.trait.legendary;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;

public class UndyingTrait extends LegendaryTrait
{
    // 传奇词条，使怪物拥有无限续杯的不死图腾。
    // 怪物死亡时会试图使用不死图腾来使自己满血复活。
    public UndyingTrait()
    {
        super(Formatting.DARK_BLUE);
    }

    public static boolean tryTriggerUndying(LivingEntity entity, DamageSource source, float amount)
    {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) return true;
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return true;
        if (diff.get().hasTrait(LHTraits.SPLIT)) return true;
        if (diff.get().hasTrait(LHTraits.UNDYING) && validTarget(entity))
        {
            float healAmount = entity.getMaxHealth() - entity.getHealth();
            LivingHealCallback.LivingHeal event = new LivingHealCallback.LivingHeal(entity, healAmount);
            boolean allow = LivingHealCallback.EVENT.invoker().onLivingHeal(event);
            if (!allow) return true;
            entity.setHealth(entity.getHealth() + event.getAmount());
            LHNetworking.toTracking(entity, new S2CUndying(entity));
            entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F);
            return false;
        } else return true;
    }

    public static boolean validTarget(LivingEntity le)
    {
        return !(le instanceof EnderDragonEntity) &&
               le.canHaveStatusEffect(new StatusEffectInstance(LHEffects.CURSE, 100));
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return validTarget(le) && super.allow(le, difficulty, maxModLv);
    }
}
