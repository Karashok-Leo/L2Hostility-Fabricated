package net.karashokleo.l2hostility.content.trait.base;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.LHData;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.content.logic.InheritContext;
import net.karashokleo.l2hostility.content.logic.TraitManager;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.init.LHTraits;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class MobTrait
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

    public void tick(LivingEntity mob, int level)
    {
    }

    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
    }

    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onHurt(int level, LivingEntity entity, LivingHurtEvent event)
    {
    }

    // 受到伤害时调用
    public void onDamaged(int level, LivingEntity entity, LivingDamageEvent event)
    {
    }

    // 死亡时调用
    public void onDeath(int level, LivingEntity entity, DamageSource source)
    {
    }

//    public void onCreateSource(int level, LivingEntity attacker, CreateSourceEvent event)
//    {
//    }

    @NotNull
    public String getNameKey()
    {
        if (desc != null) return desc;
        Identifier id = getNonNullId();
        Identifier reg = LHTraits.TRAIT.getKey().getRegistry();
        desc = reg.getPath() + "." + id.getNamespace() + "." + id.getPath();
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
        if (LHConfig.common().map.containsKey(getNonNullId().getPath()))
            return !LHConfig.common().map.get(getNonNullId().getPath());
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
