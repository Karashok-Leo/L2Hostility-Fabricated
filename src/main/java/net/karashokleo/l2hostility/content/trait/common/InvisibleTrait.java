package net.karashokleo.l2hostility.content.trait.common;

import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.trait.base.SelfEffectTrait;
import net.karashokleo.l2hostility.init.registry.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class InvisibleTrait extends SelfEffectTrait
{
    // 使怪物拥有隐身的效果。
    // 怪物常驻 隐身 效果，护甲自带 遁隐。
    public InvisibleTrait()
    {
        super(() -> StatusEffects.INVISIBILITY);
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        var diff = MobDifficulty.get(le);
        if (diff.isEmpty() || diff.get().summoned)
            return false;
        return super.allow(le, difficulty, maxModLv);
    }

    @Override
    public void postInit(LivingEntity mob, int lv)
    {
        for (var e : EquipmentSlot.values())
        {
            ItemStack stack = mob.getEquippedStack(e);
            if (!stack.isEmpty())
                if (EnchantmentHelper.getLevel(LHEnchantments.SHULKER_ARMOR, stack) == 0)
                    stack.addEnchantment(LHEnchantments.SHULKER_ARMOR, 1);
        }
    }
}
