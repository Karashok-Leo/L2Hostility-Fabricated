package net.karashokleo.l2hostility.content.trait.base;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
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

//    @Override
//    public void postHurtImpl(int level, LivingEntity attacker, LivingEntity target)
//    {
//        if (CurioCompat.hasItem(target, LHItems.RING_REFLECTION.get())) {
//            int radius = LHConfig.COMMON.ringOfReflectionRadius.get();
//            for (var e : target.level().getEntities(target, target.getBoundingBox().inflate(radius))) {
//                if (!(e instanceof Mob mob)) continue;
//                if (mob.distanceTo(target) > radius) continue;
//                EffectUtil.addEffect(mob, func.apply(level), EffectUtil.AddReason.NONE, attacker);
//            }
//        } else {
//            EffectUtil.addEffect(target, func.apply(level), EffectUtil.AddReason.NONE, attacker);
//        }
//    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        // 若目标佩戴反射戒指，则转到周围 // NYI
        event.getEntity().addStatusEffect(func.apply(level));
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(LHTexts.TOOLTIP_TARGET_EFFECT.get());
        list.add(mapLevel(e -> {
            StatusEffectInstance ins = func.apply(e);
            MutableText ans = Text.translatable(ins.getTranslationKey());
            StatusEffect effect = ins.getEffectType();
            if (ins.getAmplifier() > 0)
                ans = Text.translatable("potion.withAmplifier", ans,
                        Text.translatable("potion.potency." + ins.getAmplifier()));
            if (!ins.isDurationBelow(20))
                ans = Text.translatable("potion.withDuration", ans,
                        StatusEffectUtil.getDurationText(ins, 1));
            return ans.formatted(effect.getCategory().getFormatting());
        }));
    }
}
