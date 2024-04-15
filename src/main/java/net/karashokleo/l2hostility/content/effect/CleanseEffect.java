package net.karashokleo.l2hostility.content.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class CleanseEffect extends StatusEffect
{
    private static int recursive = 0;

    public static void clearOnEntity(LivingEntity entity)
    {
        recursive++;
        List<StatusEffectInstance> list = new ArrayList<>(entity.getStatusEffects());
        for (StatusEffectInstance ins : list)
        {
            StatusEffect type = ins.getEffectType();
            if (recursive <= 1)
                entity.removeStatusEffect(type);
            if (entity.hasStatusEffect(type))
                entity.getActiveStatusEffects().remove(type);
        }
        recursive--;
    }

    public CleanseEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0xffff7f);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 10 == 0;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        clearOnEntity(entity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier)
    {
        clearOnEntity(entity);
    }

    // NYI
    // 净化实现
//    public static boolean isSkill(MobEffectInstance ins, LivingEntity entity) {
//        if (ins.getEffect() instanceof SkillEffect)
//            return true;
//        if (EffectUtil.getReason() == EffectUtil.AddReason.SKILL)
//            return true;
//        int pred = LCConfig.COMMON.cleansePredicate.get();
//        if (ins.getEffect().isBeneficial() && pred > 0) return true;
//        if (ins.getEffect().getCategory() == MobEffectCategory.NEUTRAL && pred > 1) return true;
//        var tag = ForgeRegistries.MOB_EFFECTS.tags();
//        if (tag != null && tag.getTag(TagGen.SKILL_EFFECT).contains(ins.getEffect())) {
//            return true;
//        }
//        return CurioCompat.testEffect(ins, entity);
//    }
//
//    public static boolean testEffect(MobEffectInstance ins, LivingEntity entity) {
//        if (ModList.get().isLoaded("curios")) {
//            return testEffectImpl(ins, entity);
//        }
//        return false;
//    }
//
//    private static boolean testEffectImpl(MobEffectInstance ins, LivingEntity entity) {
//        return CuriosApi.getCuriosInventory(entity).resolve().flatMap(cap ->
//                cap.findFirstCurio(e -> e.getItem() instanceof EffectValidItem item &&
//                        item.isEffectValid(ins, e, entity))).isPresent();
//    }
//
//    @SubscribeEvent
//    public static void onPotionTest(MobEffectEvent.Applicable event) {
//        if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
//            if (isSkill(event.getEffectInstance(), event.getEntity())) return;
//            event.setResult(Event.Result.DENY);
//        }
//    }
//
//    @SubscribeEvent
//    public static void onForceAdd(ForceAddEffectEvent event) {
//        if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
//            if (isSkill(event.getEffectInstance(), event.getEntity())) return;
//            event.setResult(Event.Result.DENY);
//        }
//    }
//
//    @SubscribeEvent(priority = EventPriority.LOW)
//    public static void onPotionAdded(MobEffectEvent.Added event) {
//        if (event.getEntity().hasEffect(LCEffects.CLEANSE.get())) {
//            if (isSkill(event.getEffectInstance(), event.getEntity())) return;
//            GeneralEventHandler.schedule(() -> CleanseEffect.clearOnEntity(event.getEntity()));
//        }
//    }
}
