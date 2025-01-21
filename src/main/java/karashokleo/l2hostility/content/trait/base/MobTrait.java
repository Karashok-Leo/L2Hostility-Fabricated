package karashokleo.l2hostility.content.trait.base;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.ReflectTrinket;
import karashokleo.l2hostility.content.logic.InheritContext;
import karashokleo.l2hostility.content.logic.TraitManager;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHData;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class MobTrait implements ItemConvertible
{
    private final IntSupplier color;
    private String desc = null;

    public MobTrait(IntSupplier color)
    {
        this.color = color;
    }

    public MobTrait(Formatting formatting)
    {
        this(() -> formatting.getColorValue() == null ? 0xffffff : formatting.getColorValue());
    }

    public TraitConfig.Config getConfig()
    {
        return LHData.traits.getOrDefault(getId());
    }

    public int getColor()
    {
        return color.getAsInt();
    }

    public int getCost(double factor)
    {
        return Math.max(1, (int) Math.round(getConfig().cost * factor));
    }

    public int getMaxLevel()
    {
        return getConfig().max_rank;
    }

    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        if (isBanned()) return false;
        TraitConfig.Config config = getConfig();
        if (difficulty < config.min_level) return false;
        if (!EntityConfig.allow(le.getType(), this)) return false;
        return config.allows(le.getType());
    }

    public final boolean allow(LivingEntity le)
    {
        return allow(le, Integer.MAX_VALUE, TraitManager.getMaxLevel() + 1);
    }

    public void initialize(LivingEntity mob, int level)
    {
    }

    public void postInit(LivingEntity mob, int lv)
    {
    }

    public void serverTick(LivingEntity mob, int level)
    {
    }

    public void onDamageSourceCreate(int level, LivingEntity entity, DamageSource damageSource, RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Vec3d position)
    {
    }

    public void onAttacking(int level, LivingEntity entity, LivingAttackEvent event)
    {
    }

    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
    }

    public final void postHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        LivingEntity target = event.getEntity();
        int radius = LHConfig.common().items.reflectTrinketRadius;
        if (ReflectTrinket.canReflect(target, this))
            target.getWorld().getEntitiesByClass(
                            LivingEntity.class,
                            target.getBoundingBox().expand(radius),
                            e -> e.distanceTo(entity) < radius &&
                                    !ReflectTrinket.canReflect(e, this)
                    )
                    .forEach(le -> this.onHurtingReflectTarget(level, entity, new LivingHurtEvent(le, event.getSource(), event.getAmount())));
        else this.onHurting(level, entity, event);
    }

    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onHurtingReflectTarget(int level, LivingEntity entity, LivingHurtEvent event)
    {
        onHurting(level, entity, event);
    }

    public void onHurt(int level, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onDamaged(int level, LivingEntity entity, LivingDamageEvent event)
    {
    }

    public void onDeath(int level, LivingEntity entity, DamageSource source)
    {
    }

    @NotNull
    public String getNameKey()
    {
        if (desc == null)
            desc = getNonNullId().toTranslationKey(LHTraits.TRAIT_KEY.getValue().getPath());
        return desc;
    }

    @NotNull
    public String getDescKey()
    {
        return getNameKey() + ".desc";
    }

    public MutableText getName()
    {
        return Text.translatable(getNameKey());
    }

    public MutableText getFullName(@Nullable Integer value)
    {
        var ans = getName();
        if (value != null) ans = ans.append(ScreenTexts.SPACE)
                .append(Text.translatable("enchantment.level." + value));
        return ans.setStyle(Style.EMPTY.withColor(color.getAsInt()));
    }

    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey()).formatted(Formatting.GRAY));
    }

    protected MutableText mapLevel(Function<Integer, MutableText> func)
    {
        MutableText comp = null;
        for (int i = 1; i <= getMaxLevel(); i++)
            comp = comp == null ? func.apply(i) : comp.append(Text.literal("/").formatted(Formatting.GRAY)).append(func.apply(i));
        assert comp != null;
        return comp;
    }

    public boolean isBanned()
    {
        if (LHConfig.common().traits.traitToggle.containsKey(getNonNullId().getPath()))
            return !LHConfig.common().traits.traitToggle.get(getNonNullId().getPath());
        return false;
    }

    public int inherited(MobDifficulty diff, int rank, InheritContext ctx)
    {
        return rank;
    }

    public boolean isIn(TagKey<MobTrait> tag)
    {
        var tagList = LHTraits.TRAIT.getEntryList(tag);
        return tagList.isPresent() && tagList.get().contains(LHTraits.TRAIT.getEntry(this));
    }

    @Override
    public Item asItem()
    {
        return Registries.ITEM.get(getNonNullId());
    }

    public static MobTrait get(Identifier id)
    {
        return LHTraits.TRAIT.get(id);
    }

    public String getIdStr()
    {
        return getNonNullId().toString();
    }

    public Identifier getId()
    {
        return LHTraits.TRAIT.getId(this);
    }

    public Identifier getNonNullId()
    {
        return Objects.requireNonNull(getId());
    }
}
