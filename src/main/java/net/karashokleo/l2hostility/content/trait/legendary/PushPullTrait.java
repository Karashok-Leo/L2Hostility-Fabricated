package net.karashokleo.l2hostility.content.trait.legendary;

import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.init.LHEnchantments;
import net.karashokleo.l2hostility.init.LHItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public abstract class PushPullTrait extends LegendaryTrait
{
    public PushPullTrait(Formatting style)
    {
        super(style);
    }

    protected abstract int getRange();

    protected abstract double getStrength(double dist);

    @Override
    public void tick(LivingEntity mob, int level)
    {
        int r = getRange();
        List<? extends LivingEntity> list;
        if (mob.getWorld().isClient())
        {
            list = mob.getWorld().getEntitiesByType(TypeFilter.instanceOf(PlayerEntity.class),
                    mob.getBoundingBox().expand(r), e -> e.isMainPlayer() && !e.getAbilities().creativeMode);
        } else
        {
            list = mob.getWorld().getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class),
                    mob.getBoundingBox().expand(r), e ->
                            e instanceof PlayerEntity pl && !pl.getAbilities().creativeMode ||
                                    e instanceof MobEntity m && m.getTarget() == mob);
        }
        for (var e : list)
        {
            double dist = mob.distanceTo(e) / r;
            if (dist > 1) return;
            if (TrinketCompat.hasItemInTrinket(e, LHItems.ABRAHADABRA)) continue;
            double strength = getStrength(dist);
            int lv = 0;
            for (var armor : e.getArmorItems())
                lv += EnchantmentHelper.getLevel(LHEnchantments.INSULATOR, armor);
            if (lv > 0)
                strength *= Math.pow(LHConfig.common().items.insulatorFactor, lv);
            Vec3d vec = e.getPos().subtract(mob.getPos()).normalize().multiply(strength);
            e.addVelocity(vec.x, vec.y, vec.z);
        }
    }
}
