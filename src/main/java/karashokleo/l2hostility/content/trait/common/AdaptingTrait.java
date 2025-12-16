package karashokleo.l2hostility.content.trait.common;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AdaptingTrait extends MobTrait
{
    // 使怪物获得强大的同类伤害减免的效果。
    // 每级使怪物能够记忆的伤害类型 +1，受到来自记忆类型的伤害时降低50%的该伤害（可无限叠加）。
    public AdaptingTrait()
    {
        super(Formatting.GOLD);
    }

    @Override
    public void onHurt(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
            source.isIn(DamageTypeTags.BYPASSES_EFFECTS))
        {
            return;
        }

        Data data = difficulty.getOrCreateData(getId(), Data::new);
        data.adapt(source.getType().msgId(), level)
            .ifPresent(factor -> event.setAmount(event.getAmount() * factor));
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

        public Optional<Float> adapt(String id, int level)
        {
            if (this.memory.contains(id))
            {
                this.memory.remove(id);
                this.memory.add(0, id);
                int val = this.adaption.compute(id, (k, old) -> old == null ? 1 : old + 1);
                float factor = (float) Math.pow(LHConfig.common().traits.adaptFactor, val - 1);
                factor = Math.max(factor, (float) LHConfig.common().traits.adaptMinFactor);
                return Optional.of(factor);
            } else
            {
                this.memory.add(0, id);
                this.adaption.put(id, 1);
                if (this.memory.size() > level)
                {
                    String old = this.memory.remove(this.memory.size() - 1);
                    this.adaption.remove(old);
                }
                return Optional.empty();
            }
        }
    }
}
