package karashokleo.l2hostility.content.trait.common;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.event.GenericEvents;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
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
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        // 距离小于3时触发
        if (event.getSource().getAttacker() instanceof LivingEntity le &&
            entity.distanceTo(le) < LHConfig.common().traits.reflectRange)
        {
            if (ReflectTrinket.canReflect(le, this)) return;
            float factor = (float) (level * LHConfig.common().traits.reflectFactor);
            GenericEvents.schedule(() -> le.damage(entity.getDamageSources().indirectMagic(entity, null), event.getAmount() * factor));
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        LHConfig.common().traits.reflectRange,
                        mapLevel(i -> Text.literal((int) Math.round(100 * (i * LHConfig.common().traits.reflectFactor)) + "")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
