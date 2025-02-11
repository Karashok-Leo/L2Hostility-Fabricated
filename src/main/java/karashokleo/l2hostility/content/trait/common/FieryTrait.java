package karashokleo.l2hostility.content.trait.common;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.trait.base.SelfEffectTrait;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class FieryTrait extends SelfEffectTrait
{
    // 使怪物免疫火焰并灼烧攻击者。
    // 点燃攻击者和被攻击者 5 秒，怪物免疫火焰伤害，常驻获得 抗火 效果。
    public FieryTrait()
    {
        super(() -> StatusEffects.FIRE_RESISTANCE);
    }

//    @Override
//    public void onHurtTarget(int level, LivingEntity attacker, AttackCache cache, TraitEffectCache traitCache) {
//        assert cache.getLivingHurtEvent() != null;
//        if (cache.getLivingHurtEvent().getAmount() > 0) {
//            if (cache.getLivingHurtEvent().getSource().getDirectEntity() instanceof LivingEntity le) {
//                le.setSecondsOnFire(LHConfig.COMMON.fieryTime.get());
//            }
//        }
//    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        DamageSource source = event.getSource();
        if (source.getSource() instanceof LivingEntity le)
            le.setOnFireFor(LHConfig.common().traits.fieryTime);
        if (source.isIn(DamageTypeTags.IS_FIRE))
            event.setCanceled(true);
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        event.getEntity().setOnFireFor(LHConfig.common().traits.fieryTime);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        Text.literal(LHConfig.common().traits.fieryTime + "")
                                .formatted(Formatting.AQUA))
                .formatted(Formatting.GRAY));
        super.addDetail(list);
    }
}
