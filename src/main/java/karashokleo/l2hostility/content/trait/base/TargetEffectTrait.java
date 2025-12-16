package karashokleo.l2hostility.content.trait.base;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.leobrary.effect.api.util.EffectUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Function;

public class TargetEffectTrait extends MobTrait
{
    public final Function<Integer, StatusEffectInstance> func;

    public TargetEffectTrait(Function<Integer, StatusEffectInstance> func)
    {
        super(() -> func.apply(1).getEffectType().getColor());
        this.func = func;
    }

    @Override
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        int maxEffect = LHConfig.common().traits.potionTraitMaxEffect;
        if (maxEffect <= 0)
        {
            return;
        }
        LivingEntity living = event.getEntity();
        long count = EffectUtil.streamEffects(living, StatusEffectCategory.HARMFUL).count();
        if (count >= maxEffect)
        {
            return;
        }
        living.addStatusEffect(func.apply(level), entity);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(LHTexts.TOOLTIP_TARGET_EFFECT.get());
        list.add(mapLevel(e ->
        {
            StatusEffectInstance ins = func.apply(e);
            MutableText ans = Text.translatable(ins.getTranslationKey());
            StatusEffect effect = ins.getEffectType();
            if (ins.getAmplifier() > 0)
            {
                ans = Text.translatable("potion.withAmplifier", ans,
                    Text.translatable("potion.potency." + ins.getAmplifier()));
            }
            if (!ins.isDurationBelow(20))
            {
                ans = Text.translatable("potion.withDuration", ans,
                    StatusEffectUtil.getDurationText(ins, 1));
            }
            return ans.formatted(effect.getCategory().getFormatting());
        }));
        int maxEffect = LHConfig.common().traits.potionTraitMaxEffect;
        if (maxEffect <= 0)
        {
            return;
        }
        list.add(LHTexts.TOOLTIP_TARGET_EFFECT_MAX.get(maxEffect));
    }
}
