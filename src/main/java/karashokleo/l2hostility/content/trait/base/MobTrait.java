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
    protected final IntSupplier color;
    protected String desc = null;

    public MobTrait(IntSupplier color)
    {
        this.color = color;
    }

    public MobTrait(Formatting formatting)
    {
        this(() -> formatting.getColorValue() == null ? 0xffffff : formatting.getColorValue());
    }

    public static MobTrait get(Identifier id)
    {
        return LHTraits.TRAIT.get(id);
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
        if (isBanned())
        {
            return false;
        }
        TraitConfig.Config config = getConfig();
        if (difficulty < config.min_level)
        {
            return false;
        }
        if (!EntityConfig.allow(le.getType(), this))
        {
            return false;
        }
        return config.allows(le.getType());
    }

    public final boolean allow(LivingEntity le)
    {
        return allow(le, Integer.MAX_VALUE, TraitManager.getMaxLevel() + 1);
    }

    public boolean compatibleWith(MobTrait trait, int lv)
    {
        return true;
    }

    public void initialize(MobDifficulty difficulty, LivingEntity mob, int level)
    {
    }

    public void postInit(MobDifficulty difficulty, LivingEntity mob, int lv)
    {
    }

    public void serverTick(MobDifficulty difficulty, LivingEntity mob, int level)
    {
    }

    public void onDamageSourceCreate(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource damageSource, RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Vec3d position)
    {
    }

    public void onAttacking(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event)
    {
    }

    public void onAttacked(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event)
    {
    }

    public final void postHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        LivingEntity target = event.getEntity();
        int radius = LHConfig.common().items.reflectTrinketRadius;
        if (ReflectTrinket.canReflect(target, this))
        {
            target.getWorld().getEntitiesByClass(
                    LivingEntity.class,
                    target.getBoundingBox().expand(radius),
                    e -> e.distanceTo(entity) < radius &&
                        !ReflectTrinket.canReflect(e, this)
                )
                .forEach(le -> this.onHurtingReflectTarget(difficulty, entity, level, new LivingHurtEvent(le, event.getSource(), event.getAmount())));
        } else
        {
            this.onHurting(difficulty, entity, level, event);
        }
    }

    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
    }

    public void onHurtingReflectTarget(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        onHurting(difficulty, entity, level, event);
    }

    public void onHurt(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
    }

    public void onDamaged(MobDifficulty difficulty, LivingEntity entity, int level, LivingDamageEvent event)
    {
    }

    public void onKilled(MobDifficulty difficulty, LivingEntity entity, int level, LivingEntity killed)
    {
    }

    public void afterKilling(MobDifficulty difficulty, LivingEntity entity, int level, LivingEntity killed, DamageSource source)
    {
    }

    public void onDeath(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource source)
    {
    }

    public boolean allowDeath(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource source, float amount)
    {
        return true;
    }

    @NotNull
    public String getNameKey()
    {
        if (desc == null)
        {
            desc = getNonNullId().toTranslationKey(LHTraits.TRAIT_KEY.getValue().getPath());
        }
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
        if (value != null)
        {
            ans = ans.append(ScreenTexts.SPACE)
                .append(Text.translatable("enchantment.level." + value));
        }
        return ans.setStyle(Style.EMPTY.withColor(getColor()));
    }

    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey()).formatted(Formatting.GRAY));
    }

    protected MutableText mapLevel(Function<Integer, MutableText> func)
    {
        MutableText comp = null;
        for (int i = 1; i <= getMaxLevel(); i++)
        {
            comp = comp == null ? func.apply(i) : comp.append(Text.literal("/").formatted(Formatting.GRAY)).append(func.apply(i));
        }
        assert comp != null;
        return comp;
    }

    public boolean isBanned()
    {
        if (LHConfig.common().traits.traitToggle.containsKey(getNonNullId().getPath()))
        {
            return !LHConfig.common().traits.traitToggle.get(getNonNullId().getPath());
        }
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
