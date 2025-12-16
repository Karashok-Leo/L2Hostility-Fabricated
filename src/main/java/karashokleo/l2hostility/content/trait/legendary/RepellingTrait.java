package karashokleo.l2hostility.content.trait.legendary;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RepellingTrait extends PushPullTrait
{
    // 传奇词条，使怪物能够排斥玩家。真正让人产生生理不适的词条。
    // 怪物会排斥周围 10 格内所有对自己有敌意的生物，并且免疫弹射物伤害，只能在远程敌人身上发现。
    public RepellingTrait()
    {
        super(Formatting.DARK_GREEN);
    }

    @Override
    protected int getRange()
    {
        return LHConfig.common().traits.repellRange;
    }

    @Override
    protected double getStrength(double dist)
    {
        return (1 - dist) * LHConfig.common().traits.repellStrength;
    }

    @Override
    public void onAttacked(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event)
    {
        DamageSource source = event.getSource();
        if (!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
            !source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
            source.isIn(DamageTypeTags.IS_PROJECTILE))
        {
            event.setCanceled(true);
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                Text.literal(LHConfig.common().traits.repellRange + "")
                    .formatted(Formatting.AQUA))
            .formatted(Formatting.GRAY));
    }
}
