package karashokleo.l2hostility.content.logic;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTraits;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TraitGenerator
{
    public static void generateTraits(MobDifficulty diff, LivingEntity le, int lv, HashMap<MobTrait, Integer> traits, MobDifficultyCollector ins)
    {
        new TraitGenerator(diff, le, lv, traits, ins).generate();
    }

    public Event<ModifyTraitCountCapCallback> MODIFY_TRAIT_COUNT_CAP = EventFactory.createArrayBacked(ModifyTraitCountCapCallback.class, listeners -> (cap, difficulty, entity, level) ->
    {
        for (ModifyTraitCountCapCallback listener : listeners)
            cap = listener.modifyTraitCountCap(cap, difficulty, entity, level);
        return cap;
    });

    public interface ModifyTraitCountCapCallback
    {
        int modifyTraitCountCap(int traitCountCap, MobDifficulty difficulty, LivingEntity entity, int level);
    }

    private final MobDifficulty difficulty;
    private final LivingEntity entity;
    private final int mobLevel;
    private final int traitCountCap;
    private final MobDifficultyCollector ins;
    private final HashMap<MobTrait, Integer> traits;
    private final Random rand;
    private final List<MobTrait> traitPool;
    private int level;
    private int weights;

    private TraitGenerator(MobDifficulty diff, LivingEntity entity, int mobLevel, HashMap<MobTrait, Integer> traits, MobDifficultyCollector ins)
    {
        this.difficulty = diff;
        this.entity = entity;
        this.mobLevel = mobLevel;
        this.traitCountCap = MODIFY_TRAIT_COUNT_CAP.invoker().modifyTraitCountCap(ins.traitCountCap, diff, entity, mobLevel);
        this.ins = ins;
        this.traits = traits;

        this.rand = entity.getRandom();
        this.level = mobLevel;

        var config = diff.getConfigCache();
        if (config != null)
            for (var base : config.traits)
                if (base.condition() == null ||
                    base.condition().match(entity, mobLevel, ins))
                    genBase(base);

        this.traitPool = new ArrayList<>(LHTraits.TRAIT.stream().filter(e ->
                (config == null || !config.blacklist.contains(e)) &&
                !traits.containsKey(e) &&
                e.allow(entity, mobLevel, ins.getMaxTraitLevel())).toList());
        this.weights = 0;
        for (var e : traitPool)
            this.weights += e.getConfig().weight;
    }

    // 获取词条等级
    private int getRank(MobTrait e)
    {
        return traits.getOrDefault(e, 0);
    }

    // 设置词条等级
    private void setRank(MobTrait e, int rank)
    {
        if (rank == 0)
            traits.remove(e);
        else
            traits.put(e, rank);
    }

    // 随机选择一个词条
    private MobTrait pop()
    {
        int val = rand.nextInt(weights);
        MobTrait e = traitPool.get(0);
        for (var x : traitPool)
        {
            val -= x.getConfig().weight;
            if (val <= 0)
            {
                e = x;
                break;
            }
        }
        weights -= e.getConfig().weight;
        traitPool.remove(e);
        return e;
    }

    private void genBase(EntityConfig.TraitBase base)
    {
        MobTrait e = base.trait();
        if (e == null) return;
        int maxTrait = TraitManager.getMaxLevel() + 1;
        if (!e.allow(entity, mobLevel, maxTrait)) return;
        int max = e.getMaxLevel();// config bypass player trait cap
        int cost = e.getCost(ins.traitCost);
        int old = Math.min(e.getMaxLevel(), Math.max(getRank(e), base.free()));
        int expected = Math.min(max, Math.max(old, base.min()));
        int rank = Math.min(expected, old + level / cost);
        setRank(e, Math.max(old, rank));
        if (rank > old)
            level -= (rank - old) * cost;
    }

    private void generate()
    {
        while (level > 0 && !traitPool.isEmpty())
        {
            MobTrait e = pop();
            int cost = e.getCost(ins.traitCost);
            if (cost > level)
                continue;
            int max = Math.min(ins.getMaxTraitLevel(), e.getMaxLevel());
            int old = Math.min(e.getMaxLevel(), getRank(e));
            int rank = Math.min(max, old + rand.nextInt(level / cost) + 1);
            if (rank <= old)
                continue;

            boolean compatible = traits
                    .keySet()
                    .stream()
                    .allMatch(trait ->
                    {
                        int lv = traits.get(trait);
                        return e.compatibleWith(trait, lv) && trait.compatibleWith(e, rank);
                    });
            if (!compatible)
                continue;

            setRank(e, rank);
            level -= (rank - old) * cost;

            if (traits.size() >= traitCountCap)
                break;

            if (!ins.isFullChance() &&
                rand.nextDouble() < LHConfig.common().scaling.globalTraitSuppression)
                break;
        }
        for (var e : traits.entrySet())
            e.getKey().initialize(difficulty, entity, e.getValue());
    }
}
