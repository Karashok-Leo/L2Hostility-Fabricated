package karashokleo.l2hostility.content.trait.common;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.leobrary.damage.api.state.DamageState;
import karashokleo.leobrary.damage.api.state.DamageStateProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;
import java.util.function.Predicate;

public class AdaptingTrait extends MobTrait
{
    private static final Set<Predicate<DamageState<?>>> STATE_PREDICATES = new HashSet<>();

    public static void registerStatePredicate(final Predicate<DamageState<?>> predicate)
    {
        STATE_PREDICATES.add(predicate);
    }

    // 使怪物获得强大的同类伤害减免的效果。
    // 每级使怪物能够记忆的伤害类型 +1，受到来自记忆类型的伤害时降低50%的该伤害（可无限叠加）。
    public AdaptingTrait()
    {
        super(Formatting.GOLD);
    }

    @Override
    public void onHurt(int level, LivingEntity entity, LivingHurtEvent event)
    {
        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
                source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) return;
        var cap = MobDifficulty.get(entity);
        if (cap.isEmpty()) return;

        String id = event.getSource().getType().msgId();
        Optional<DamageState<?>> damageState = ((DamageStateProvider) source).getState(state ->
        {
            for (Predicate<DamageState<?>> predicate : STATE_PREDICATES)
                if (predicate.test(state)) return true;
            return false;
        });
        if (damageState.isPresent()) id = damageState.get().toString();

        Data data = cap.get().getOrCreateData(getId(), Data::new);
        data.adapt(id, level).ifPresent(factor -> event.setAmount(event.getAmount() * factor));
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
                return Optional.of((float) Math.pow(LHConfig.common().traits.adaptFactor, val - 1));
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
