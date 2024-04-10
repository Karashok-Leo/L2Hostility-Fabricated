package net.karashokleo.l2hostility.util;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class EffectBuilder
{
    private StatusEffect type;
    private int amplifier;
    private int duration;
    private boolean ambient;
    private boolean showParticles;
    private boolean showIcon;

    public EffectBuilder(StatusEffectInstance ins)
    {
        this(
                ins.getEffectType(),
                ins.getAmplifier(),
                ins.getDuration(),
                ins.isAmbient(),
                ins.shouldShowParticles(),
                ins.shouldShowIcon()
        );
    }

    public EffectBuilder(StatusEffect type, int amplifier, int duration, boolean ambient, boolean showParticles, boolean showIcon)
    {
        this.type = type;
        this.amplifier = amplifier;
        this.duration = duration;
        this.ambient = ambient;
        this.showParticles = showParticles;
        this.showIcon = showIcon;
    }

    public EffectBuilder setEffectType(StatusEffect type)
    {
        this.type = type;
        return this;
    }

    public EffectBuilder setAmplifier(int amplifier)
    {
        this.amplifier = amplifier;
        return this;
    }

    public EffectBuilder setDuration(int duration)
    {
        this.duration = duration;
        return this;
    }

    public EffectBuilder setAmbient(boolean ambient)
    {
        this.ambient = ambient;
        return this;
    }

    public EffectBuilder setShowParticles(boolean showParticles)
    {
        this.showParticles = showParticles;
        return this;
    }

    public EffectBuilder setShowIcon(boolean showIcon)
    {
        this.showIcon = showIcon;
        return this;
    }

    public StatusEffectInstance build()
    {
        return new StatusEffectInstance(type, duration, amplifier, ambient, showParticles, showIcon);
    }
}