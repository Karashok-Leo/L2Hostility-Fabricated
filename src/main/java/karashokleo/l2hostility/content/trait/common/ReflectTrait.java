package karashokleo.l2hostility.content.trait.common;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.api.event.AllowTraitEffectCallback;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.event.GenericEvents;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.damage.api.state.DamageState;
import karashokleo.leobrary.damage.api.state.IdentifierDamageState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ReflectTrait extends MobTrait
{
    public static final DamageState REFLECT_STATE = new IdentifierDamageState(L2Hostility.id("reflect"));

    // 使怪物拥有反弹近战伤害的能力。
    // 怪物能够将直接的伤害以魔法伤害反射给攻击者，每级增加 30% 的反射量。
    public ReflectTrait()
    {
        super(Formatting.DARK_RED);
    }

    private void doReflect(LivingEntity target, LivingEntity attacker, float amount)
    {
        DamageSource source = attacker.getDamageSources().indirectMagic(null, attacker);
        source.addState(REFLECT_STATE);
        target.damage(source, amount);
    }

    @Override
    public void onAttacked(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event)
    {
        // 距离小于reflectRange时触发
        DamageSource source = event.getSource();
        if (!(source.getAttacker() instanceof LivingEntity le))
        {
            return;
        }
        if (entity.distanceTo(le) >= LHConfig.common().traits.reflectRange)
        {
            return;
        }
        // avoid recursive damage
        if (source.hasState(REFLECT_STATE))
        {
            return;
        }
        if (source.isIn(LHTags.MAGIC) &&
            !LHConfig.common().traits.reflectMagic)
        {
            return;
        }
        if (!AllowTraitEffectCallback.EVENT.invoker().allowTraitEffect(difficulty, entity, le, this, level))
        {
            return;
        }
        if (ReflectTrinket.canReflect(le, this))
        {
            return;
        }
        float factor = (float) (level * LHConfig.common().traits.reflectFactor);

        float reflectAmount = event.getAmount() * factor;
        float reflectLimit = (float) LHConfig.common().traits.reflectLimit;
        if (reflectLimit >= 0)
        {
            reflectAmount = Math.min(reflectAmount, le.getMaxHealth() * reflectLimit);
        }
        float finalAmount = reflectAmount;

        GenericEvents.schedule(() -> doReflect(le, entity, finalAmount));
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
            LHConfig.common().traits.reflectRange,
            mapLevel(i -> Text.literal((int) Math.round(100 * (i * LHConfig.common().traits.reflectFactor)) + "").formatted(Formatting.AQUA)),
            (int) Math.round(100 * LHConfig.common().traits.reflectLimit)
        ).formatted(Formatting.GRAY));
    }
}
