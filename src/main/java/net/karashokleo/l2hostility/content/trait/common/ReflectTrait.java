package net.karashokleo.l2hostility.content.trait.common;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ReflectTrait extends MobTrait
{
    // 使怪物拥有反弹近战伤害的能力。
    // 怪物能够将直接的伤害以魔法伤害反射给攻击者，每级增加 30% 的反射量。
    public ReflectTrait()
    {
        super(Formatting.DARK_RED);
    }

    @Override
    public void onHurt(int level, LivingEntity entity, LivingHurtEvent event)
    {
//        if (event.getSource().getSource() instanceof LivingEntity le && event.getSource().isIn(L2DamageTypes.DIRECT)) {
//            if (CurioCompat.hasItemInCurio(le, LHItems.ABRAHADABRA.get())) return;
//            float factor = (float) (level * LHConfig.common().traits.reflectFactor);
//            GeneralEventHandler.schedule(() -> le.damage(entity.getWorld().getDamageSources().indirectMagic(entity, null), event.getAmount() * factor));
//        }
    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        float factor = (float) (level * LHConfig.common().traits.reflectFactor);
        // 距离小于3时触发
        if (event.getSource().getSource() instanceof LivingEntity le &&
                entity.distanceTo(le) < 3)
            le.damage(entity.getWorld().getDamageSources().indirectMagic(entity, null), event.getAmount() * factor);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal((int) Math.round(100 * (i * LHConfig.common().traits.reflectFactor)) + "")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
