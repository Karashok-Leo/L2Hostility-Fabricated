package net.karashokleo.l2hostility.content.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CurseEffect extends StatusEffect
{
    public CurseEffect()
    {
        super(StatusEffectCategory.HARMFUL, 0x3f3f3f);
    }
}
