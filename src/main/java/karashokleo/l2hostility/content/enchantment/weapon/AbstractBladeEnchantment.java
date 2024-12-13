package karashokleo.l2hostility.content.enchantment.weapon;

import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.content.enchantment.core.BattleEnchantment;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public abstract class AbstractBladeEnchantment extends BattleEnchantment
{
    public AbstractBladeEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level)
    {
        var le = getTarget(target);
        if (le != null && le != user && !user.getWorld().isClient())
        {
            StatusEffectInstance newEffect = getEffect(level);
            if (EffectUtil.forceAddEffect(le, newEffect, user))
            {
                StatusEffectInstance oldEffect = le.getActiveStatusEffects().get(newEffect.getEffectType());
                new MobEffectEvent.Added(le, oldEffect, newEffect, user).sendEvent();
            }
        }
    }

    protected abstract StatusEffectInstance getEffect(int level);
}
