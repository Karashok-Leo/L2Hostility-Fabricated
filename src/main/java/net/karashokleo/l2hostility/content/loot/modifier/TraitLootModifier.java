package net.karashokleo.l2hostility.content.loot.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.loot.condition.MobHealthLootCondition;
import net.karashokleo.l2hostility.content.loot.condition.MobLevelLootCondition;
import net.karashokleo.l2hostility.content.loot.condition.PlayerHasItemLootCondition;
import net.karashokleo.l2hostility.content.loot.condition.TraitLootCondition;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHTexts;
import net.karashokleo.l2hostility.init.LHTraits;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TraitLootModifier extends CurseLootModifier
{
    public static final Codec<TraitLootModifier> CODEC = RecordCodecBuilder.create(i -> codecStart(i).and(i.group(
                    LHTraits.TRAIT.getCodec().optionalFieldOf("trait").forGetter(e -> Optional.ofNullable(e.trait)),
                    Codec.DOUBLE.fieldOf("chance").forGetter(e -> e.chance),
                    Codec.DOUBLE.fieldOf("rankBonus").forGetter(e -> e.rankBonus),
                    ItemStack.CODEC.fieldOf("result").forGetter(e -> e.result)))
            .apply(i, (conditions, trait, chance, rankBonus, stack) -> new TraitLootModifier(trait.orElse(null), chance, rankBonus, stack, conditions)));

    @Nullable
    public final MobTrait trait;
    public final double chance, rankBonus;
    public final ItemStack result;

    public TraitLootModifier(@Nullable MobTrait trait, double chance, double rankBonus, ItemStack result, LootCondition... conditionsIn)
    {
        super(conditionsIn);
        this.trait = trait;
        this.chance = chance;
        this.rankBonus = rankBonus;
        this.result = result;
    }

    @Override
    protected void modifyLoot(ObjectArrayList<ItemStack> generatedLoot, LootContext context, MobDifficulty diff, double factor)
    {
        int lv = trait == null ? 0 : diff.getTraitLevel(trait);
        double rate = chance + lv * rankBonus;
        int count = 0;
        for (int i = 0; i < result.getCount() * factor; i++)
            if (context.getRandom().nextDouble() < rate)
                count++;
        if (count > 0)
        {
            ItemStack ans = result.copy();
            ans.setCount(count);
            generatedLoot.add(ans);
        }
    }

    @Override
    public Codec<TraitLootModifier> codec()
    {
        return CODEC;
    }

    @Override
    public ItemStack getLoot()
    {
        return result;
    }

    @Override
    public List<ItemStack> getTraits()
    {
        List<ItemStack> traits = new ArrayList<>();
        if (this.trait != null)
            traits.add(this.trait.asItem().getDefaultStack());
        traits.addAll(super.getTraits());
        return traits;
    }

    @Override
    public void addTooltip(List<Text> list)
    {
        int max = trait == null ? 0 : trait.getConfig().max_rank;
        int min = 1;
        int minLevel = 0;
        List<TraitLootCondition> other = new ArrayList<>();
        List<PlayerHasItemLootCondition> itemReq = new ArrayList<>();
        List<MobHealthLootCondition> health = new ArrayList<>();
        for (var c : getConditions())
            if (c instanceof TraitLootCondition cl)
            {
                if (cl.trait == trait)
                {
                    max = Math.min(max, cl.maxLevel);
                    min = Math.max(min, cl.minLevel);
                } else other.add(cl);
            } else if (c instanceof MobLevelLootCondition cl)
                minLevel = cl.minLevel;
            else if (c instanceof PlayerHasItemLootCondition cl)
                itemReq.add(cl);
            else if (c instanceof MobHealthLootCondition cl)
                health.add(cl);
        if (minLevel > 0)
            list.add(LHTexts.LOOT_MIN_LEVEL.get(Text.literal(minLevel + "")
                            .formatted(Formatting.AQUA))
                    .formatted(Formatting.LIGHT_PURPLE));
        for (var e : health)
            list.add(LHTexts.LOOT_MIN_HEALTH.get(Text.literal(e.minHealth + "")
                            .formatted(Formatting.AQUA))
                    .formatted(Formatting.LIGHT_PURPLE));
        if (trait != null)
            for (int lv = min; lv <= max; lv++)
                list.add(LHTexts.LOOT_CHANCE.get(
                                Text.literal(Math.round((chance + rankBonus * lv) * 100) + "%")
                                        .formatted(Formatting.AQUA),
                                trait.getName().formatted(Formatting.GOLD),
                                Text.literal(lv + "").formatted(Formatting.AQUA))
                        .formatted(Formatting.GRAY));
        else
            list.add(LHTexts.LOOT_NO_TRAIT.get(Text.literal(Math.round(chance * 100) + "%")
                            .formatted(Formatting.AQUA))
                    .formatted(Formatting.GRAY));
        for (var c : other)
        {
            int cmin = Math.max(c.minLevel, 1);
            int cmax = Math.min(c.maxLevel, c.trait.getMaxLevel());
            String str = cmax == cmin ?
                    cmin + "" :
                    cmax >= c.trait.getMaxLevel() ?
                            cmin + "+" :
                            cmin + "-" + cmax;
            list.add(LHTexts.LOOT_OTHER_TRAIT.get(c.trait.getName().formatted(Formatting.GOLD),
                            Text.literal(str).formatted(Formatting.AQUA))
                    .formatted(Formatting.RED));
        }
        for (var e : itemReq)
        {
            var name = e.item.getName().copy().formatted(Formatting.LIGHT_PURPLE);
            list.add(LHTexts.TOOLTIP_LOOT_REQUIRED.get(name).formatted(Formatting.YELLOW));
        }
    }
}
