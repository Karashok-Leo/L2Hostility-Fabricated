package karashokleo.l2hostility.content.effect;

import karashokleo.effect_overlay.api.IconEffectRenderer;
import karashokleo.effect_overlay.api.IconOverlayEffect;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;

public class CleanseEffect extends StatusEffect implements IconOverlayEffect
{
    private static int recursive = 0;

    public CleanseEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0xffff7f);
    }

    /*
     * 判断效果是否不受净化影响
     * true: 保留该效果
     * false: 净化该效果
     */
    public static boolean isInCleanseBlacklist(StatusEffectInstance effectInstance, LivingEntity entity)
    {
        StatusEffect type = effectInstance.getEffectType();
        if (Registries.STATUS_EFFECT
            .getEntry(type)
            .isIn(LHTags.CLEANSE_BLACKLIST) ||
            TrinketCompat.isEffectValidInTrinket(effectInstance, entity))
        {
            return true;
        }
        return type == LHEffects.CLEANSE ||
            !LHConfig.common().effects.cleansePredicate
                .contains(type.getCategory());
    }

    public static void clearOnEntity(LivingEntity entity)
    {
        recursive++;
        List<StatusEffectInstance> list = new ArrayList<>(entity.getStatusEffects());
        for (StatusEffectInstance ins : list)
        {
            if (isInCleanseBlacklist(ins, entity))
            {
                continue;
            }
            StatusEffect type = ins.getEffectType();
            if (recursive <= 1)
            {
                entity.removeStatusEffect(type);
            }
            if (entity.hasStatusEffect(type))
            {
                entity.getActiveStatusEffects().remove(type);
            }
        }
        recursive--;
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

    @Override
    public IconEffectRenderer getIcon(LivingEntity entity, int lv)
    {
        return IconEffectRenderer.icon(entity, L2Hostility.id("textures/effect_overlay/cleanse.png"));
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
