package karashokleo.l2hostility.content.item.trinket.core;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public interface EffectValidItem
{
    boolean isEffectValid(StatusEffectInstance ins, ItemStack stack, LivingEntity user);
}
