package karashokleo.l2hostility.content.trait.highlevel;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.logic.InheritContext;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.Formatting;

public class SplitTrait extends MobTrait
{
    // 使怪物能够像史莱姆一样分裂。
    // 怪物在死亡时分裂，分裂的怪物会继承所有词条并且词条等级 -1，拥有装备的怪物分裂出来的装备会降级 。
    public SplitTrait()
    {
        super(Formatting.GREEN);
    }

    public static void copyComponents(LivingEntity entity, LivingEntity added)
    {
        var originDiff = entity.getComponent(LHComponents.MOB_DIFFICULTY);
        var addedDiff = added.getComponent(LHComponents.MOB_DIFFICULTY);
        addedDiff.copyFrom(originDiff);
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return !(le instanceof SlimeEntity) && super.allow(le, difficulty, maxModLv);
    }

    @Override
    public void onDeath(int level, LivingEntity entity, DamageSource source)
    {
        if (entity.getWorld().isClient() ||
            source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return;
        add(entity);
        add(entity);
    }

    private void add(LivingEntity entity)
    {
        var level = entity.getWorld();
        LivingEntity added = (LivingEntity) entity.getType().create(level);
        assert added != null;
        copyComponents(entity, added);
        added.copyPositionAndRotation(entity);
        level.spawnEntity(added);
    }

    @Override
    public int inherited(MobDifficulty cap, int rank, InheritContext ctx)
    {
        cap.lv /= 2;
        cap.noDrop = true;
        return rank - 1;
    }
}
