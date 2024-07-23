package karashokleo.l2hostility.content.trait.base;

import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.Supplier;

public class SelfEffectTrait extends MobTrait
{
    private static final int TICK_REFRESH_INTERNAL = 10;
    private static final int EFFECT_DURATION = 40;

    public final Supplier<StatusEffect> effect;

    public SelfEffectTrait(Supplier<StatusEffect> effect)
    {
        super(() -> effect.get().getColor());
        this.effect = effect;
    }

    @Override
    public void serverTick(LivingEntity mob, int level)
    {
        if (mob.age % TICK_REFRESH_INTERNAL == 0)
            mob.addStatusEffect(new StatusEffectInstance(effect.get(), EFFECT_DURATION, level - 1), mob);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(LHTexts.TOOLTIP_SELF_EFFECT.get());
        Formatting color = effect.get().getCategory().getFormatting();
        if (getMaxLevel() == 1)
            list.add(effect.get().getName().copy().formatted(color));
        else list.add(
                mapLevel(e ->
                        Text.translatable("potion.withAmplifier", effect.get().getName(),
                                        Text.translatable("potion.potency." + (e - 1)))
                                .formatted(color))
        );
    }
}
