package karashokleo.l2hostility.content.trait.legendary;

import karashokleo.l2hostility.content.logic.TraitManager;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;

import java.util.function.IntSupplier;

public class LegendaryTrait extends MobTrait
{
    public LegendaryTrait(IntSupplier color)
    {
        super(color);
    }

    public LegendaryTrait(Formatting format)
    {
        super(format);
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return maxModLv > TraitManager.getMaxLevel() && super.allow(le, difficulty, maxModLv);
    }

    @Override
    public boolean isBanned()
    {
        return !LHConfig.common().scaling.allowLegendary || super.isBanned();
    }
}
