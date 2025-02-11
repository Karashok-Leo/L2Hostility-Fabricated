package karashokleo.l2hostility.content.trait.legendary;

import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.function.IntSupplier;

public abstract class PushPullTrait extends LegendaryTrait
{
    public PushPullTrait(IntSupplier color)
    {
        super(color);
    }

    public PushPullTrait(Formatting style)
    {
        super(style);
    }

    protected abstract int getRange();

    protected abstract double getStrength(double dist);

    protected double getProcessedStrength(LivingEntity target, double dist)
    {
        if (ReflectTrinket.canReflect(target, this))
            return 0;
        double strength = getStrength(dist);
        int lv = 0;
        for (var armor : target.getArmorItems())
            lv += EnchantmentHelper.getLevel(LHEnchantments.INSULATOR, armor);
        if (lv > 0)
            strength *= Math.pow(LHConfig.common().items.insulatorFactor, lv);
        return strength;
    }

    @Override
    public void serverTick(LivingEntity mob, int level)
    {
        int r = getRange();
        List<? extends LivingEntity> list = mob.getWorld()
                .getEntitiesByType(
                        TypeFilter.instanceOf(LivingEntity.class),
                        mob.getBoundingBox().expand(r),
                        e -> e instanceof PlayerEntity pl && !pl.getAbilities().creativeMode &&
                             !pl.isSpectator() ||
                             e instanceof MobEntity m && m.getTarget() == mob
                );
        for (var e : list)
        {
            double dist = mob.distanceTo(e) / r;
            if (dist > 1) return; // 范围形状从立方体变为球
            double strength = getProcessedStrength(e, dist);
            if (strength == 0) continue;
            Vec3d vec = e.getPos().subtract(mob.getPos()).normalize().multiply(strength);
            e.addVelocity(vec.x, vec.y, vec.z);
        }
    }
}
