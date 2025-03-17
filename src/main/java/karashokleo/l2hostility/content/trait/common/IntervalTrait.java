package karashokleo.l2hostility.content.trait.common;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;

public abstract class IntervalTrait extends MobTrait
{
    protected final IntUnaryOperator interval;

    public IntervalTrait(IntSupplier color, IntUnaryOperator interval)
    {
        super(color);
        this.interval = interval;
    }

    public IntervalTrait(Formatting format, IntUnaryOperator interval)
    {
        super(format);
        this.interval = interval;
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity e, int level)
    {
        var data = getData(difficulty);
        if (data.tickCount++ < interval.applyAsInt(level)) return;
        action(difficulty.owner, level, data);
    }

    public Data getData(MobDifficulty diff)
    {
        return diff.getOrCreateData(getId(), Data::new);
    }

    public void action(MobEntity mob, int level, Data data)
    {
        data.tickCount = 0;
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(
                Text.translatable(
                        getDescKey(),
                        mapLevel(lv -> Text.literal(interval.applyAsInt(lv) / 20d + "").formatted(Formatting.AQUA))
                ).formatted(Formatting.GRAY)
        );
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int tickCount;
    }
}
