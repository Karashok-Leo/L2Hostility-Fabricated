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

public abstract class IntervalTrait extends MobTrait
{
    protected final IntSupplier interval;

    public IntervalTrait(Formatting format, IntSupplier interval)
    {
        super(format);
        this.interval = interval;
    }

    @Override
    public void serverTick(LivingEntity e, int level)
    {
        var diff = MobDifficulty.get(e);
        if (diff.isEmpty()) return;
        var cap = diff.get();
        var data = getData(cap);
        if (data.tickCount++ < interval.getAsInt()) return;
        action(cap.owner, level, data);
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
        list.add(Text.translatable(getDescKey(),
                Text.literal(interval.getAsInt() / 20d + "")
                        .formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int tickCount;
    }
}
