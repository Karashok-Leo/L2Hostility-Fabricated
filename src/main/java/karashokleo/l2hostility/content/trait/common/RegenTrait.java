package karashokleo.l2hostility.content.trait.common;

import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RegenTrait extends MobTrait
{
    // 使怪物拥强大的生命恢复效果。
    // 怪物每秒恢复自身最大生命值的 2% * 怪物等级。
    public RegenTrait()
    {
        super(Formatting.RED);
    }

    @Override
    public void serverTick(LivingEntity mob, int level)
    {
        if (mob.age % 20 == 0)
            mob.heal((float) (mob.getMaxHealth() * LHConfig.common().traits.regen * level));
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal((int) Math.round(LHConfig.common().traits.regen * 100 * i) + "")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return validTarget(le) && super.allow(le, difficulty, maxModLv);
    }

    public boolean validTarget(LivingEntity le)
    {
        if (le instanceof EnderDragonEntity) return false;
        // 无法施加Curse = 无法拥有该词条
        return le.canHaveStatusEffect(new StatusEffectInstance(LHEffects.CURSE, 100));
    }
}
