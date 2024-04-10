package net.karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.logic.InheritContext;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.registry.LHTraits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Formatting;

public class GrowthTrait extends MobTrait
{
    // 使怪物能够重创拥有大量附魔装备的目标。
    // 怪物会复制目标身上装备的所有附魔，目标的每级附魔提升 2% 的伤害（ x级附魔提供 2x 的附魔点数）。
    public GrowthTrait()
    {
        super(Formatting.DARK_GREEN);
    }

    @Override
    public void tick(LivingEntity mob, int level)
    {
        if (mob.getHealth() == mob.getMaxHealth() && mob instanceof SlimeEntity slime)
        {
            int size = slime.getSize();
            if (size < 1 << (level + 2))
                slime.setSize(size + 1, false);
        }
    }

    @Override
    public void postInit(LivingEntity mob, int lv)
    {
        var regen = LHTraits.REGEN;
        MobDifficulty.get(mob).ifPresent(cap ->
        {
            if (regen.allow(mob) && cap.getTraitLevel(regen) < lv)
                cap.setTrait(regen, lv);
        });
    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        if (event.getSource().isOf(DamageTypes.IN_WALL))
            event.setCanceled(true);
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return le instanceof SlimeEntity && super.allow(le, difficulty, maxModLv);
    }

    @Override
    public int inherited(MobDifficulty diff, int rank, InheritContext ctx)
    {
        return ctx.isPrimary() ? rank - 1 : 0;
    }
}
