package karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.logic.InheritContext;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.Formatting;

public class GrowthTrait extends MobTrait
{
    // 使史莱姆类的怪物能够变得更大。
    // 史莱姆获得再生的词条，会在满血后变大，词条等级决定了其最大的体积，分裂的史莱姆只有一只会继承该词条并且词条等级 -1 。
    public GrowthTrait()
    {
        super(Formatting.DARK_GREEN);
    }

    @Override
    public void serverTick(LivingEntity mob, int level)
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
