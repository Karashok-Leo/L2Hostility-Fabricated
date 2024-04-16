package net.karashokleo.l2hostility.content.trait.common;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.component.mob.CapStorageData;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdaptingTrait extends MobTrait
{
    // 使怪物获得强大的同类伤害减免的效果。
    // 每级使怪物能够记忆的伤害类型 +1，受到来自记忆类型的伤害时降低50%的该伤害（可无限叠加）。
    public AdaptingTrait()
    {
        super(Formatting.GOLD);
    }

    @Override
    public void onHurt(int level, LivingEntity entity, LivingHurtEvent event)
    {
        if (LHDamageTypes.bypasses(event.getSource())) return;
        var cap = MobDifficulty.get(entity);
        if (cap.isEmpty()) return;
        Data data = cap.get().getOrCreateData(getId(), Data::new);
        String id = event.getSource().getType().msgId();
        if (data.memory.contains(id))
        {
            data.memory.remove(id);
            data.memory.add(0, id);
            int val = data.adaption.compute(id, (k, old) -> old == null ? 1 : old + 1);
            double factor = Math.pow(LHConfig.common().traits.adaptFactor, val - 1);
            event.setAmount((float) (event.getAmount() * factor));
        } else
        {
            data.memory.add(0, id);
            data.adaption.put(id, 1);
            if (data.memory.size() > level)
            {
                String old = data.memory.remove(data.memory.size() - 1);
                data.adaption.remove(old);
            }
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        Text.literal((int) Math.round(100 * (1 - LHConfig.common().traits.adaptFactor)) + "")
                                .formatted(Formatting.AQUA),
                        mapLevel(i -> Text.literal("" + i)
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public final ArrayList<String> memory = new ArrayList<>();
        @SerialClass.SerialField
        public final HashMap<String, Integer> adaption = new HashMap<>();
    }
}
