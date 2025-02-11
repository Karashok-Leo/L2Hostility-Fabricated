package karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.traits.EffectBooster;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class DrainTrait extends MobTrait
{
    // 使怪物能够移除正面效果并使你拥有更严重的负面效果。
    // 怪物获得相同等级的随机药水词条，击中目标时会移除目标【词条等级】个正面效果，目标每有一个负面效果使伤害提升【词条等级】* 10%，负面效果延长【词条等级】* 50%，最多延长至【词条等级】* 60秒。
    public DrainTrait()
    {
        super(Formatting.LIGHT_PURPLE);
    }

    @Override
    public void postInit(LivingEntity mob, int lv)
    {
        var diff = MobDifficulty.get(mob);
        if (diff.isEmpty()) return;
        var entryList = LHTraits.TRAIT.getEntryList(LHTags.POTION);
        if (entryList.isEmpty()) return;
        entryList.get().getRandom(mob.getRandom());
        for (int i = 0; i < 4; i++)
        {
            var entry = entryList.get().getRandom(mob.getRandom());
            if (entry.isEmpty()) continue;
            var trait = entry.get().value();
            if (trait.allow(mob) && !diff.get().hasTrait(trait))
            {
                diff.get().setTrait(trait, lv);
                return;
            }
        }
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        removeEffects(level, entity, event.getEntity());
        var neg = event
                .getEntity()
                .getStatusEffects()
                .stream()
                .filter(e -> e.getEffectType().getCategory() == StatusEffectCategory.HARMFUL)
                .count();
        event.setAmount(event.getAmount() * (float) (1 + LHConfig.common().traits.drainDamage * level * neg));
    }

    public void removeEffects(int level, LivingEntity attacker, LivingEntity target)
    {
        var pos = new ArrayList<>(target.getStatusEffects().stream()
                .filter(e -> e.getEffectType().getCategory() == StatusEffectCategory.BENEFICIAL)
                .toList());
        for (int i = 0; i < level; i++)
        {
            if (pos.isEmpty()) continue;
            var ins = pos.remove(attacker.getRandom().nextInt(pos.size()));
            target.removeStatusEffect(ins.getEffectType());
        }
        double factor = 1 + LHConfig.common().traits.drainDuration * level;
        int maxTime = level * LHConfig.common().traits.drainDurationMax;
        EffectBooster.boostTrait(target, factor, maxTime);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                mapLevel(i -> Text.literal(i + "")
                        .formatted(Formatting.AQUA)),
                mapLevel(i -> Text.literal(Math.round(i * LHConfig.common().traits.drainDamage * 100) + "%")
                        .formatted(Formatting.AQUA)),
                mapLevel(i -> Text.literal(Math.round(i * LHConfig.common().traits.drainDuration * 100) + "%")
                        .formatted(Formatting.AQUA)),
                mapLevel(i -> Text.literal(Math.round(i * LHConfig.common().traits.drainDurationMax / 20f) + "")
                        .formatted(Formatting.AQUA))
        ).formatted(Formatting.GRAY));
    }
}
