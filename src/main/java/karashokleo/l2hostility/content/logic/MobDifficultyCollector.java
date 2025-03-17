package karashokleo.l2hostility.content.logic;

import karashokleo.l2hostility.data.config.DifficultyConfig;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class MobDifficultyCollector
{
    public int min;
    public int base;
    public int count;
    public int difficulty;
    public int cap = Integer.MAX_VALUE;
    public int traitCap = TraitManager.getMaxLevel() + 1;
    public int traitCountCap;
    public double scale;
    public double varSq;
    public double applyChance;
    public double traitChance;
    public double traitCost;
    public double finalFactor = 1;
    private ServerPlayerEntity player;
    private boolean fullChance;
    private boolean fullDrop;

    public MobDifficultyCollector()
    {
        traitCountCap = LHConfig.common().scaling.defaultTraitCountCap;
        traitCountCap = traitCountCap > 0 ? traitCountCap : Integer.MAX_VALUE;
        applyChance = LHConfig.common().scaling.globalApplyChance;
        traitChance = LHConfig.common().scaling.globalTraitChance;
        traitCost = 1;
    }

    public static MobDifficultyCollector noTrait(int lv)
    {
        var ans = new MobDifficultyCollector();
        ans.traitChance = 0;
        ans.base = lv;
        return ans;
    }

    public void acceptConfig(DifficultyConfig.Config config)
    {
        min = Math.max(min, config.min());
        base += config.base();
        traitCountCap = Math.min(traitCountCap, config.trait_count_cap() <= 0 ? Integer.MAX_VALUE : config.trait_count_cap());
        scale += config.scale();
        varSq += config.variation() * config.variation();
        count++;
        applyChance *= config.apply_chance();
        traitChance *= config.trait_chance();
        fullChance |= min > 0;
    }

    public void acceptBonus(DifficultyLevel difficulty)
    {
        this.difficulty += difficulty.getLevel();
    }

    public void acceptBonusLevel(int difficulty)
    {
        this.base += difficulty;
    }

    public void acceptBonusFactor(double finalFactor)
    {
        this.finalFactor *= finalFactor;
    }

    public void traitCostFactor(double factor)
    {
        traitCost *= factor;
    }

    public void setCap(int cap)
    {
        if (LHConfig.common().scaling.allowBypassMinimum)
            this.min = Math.min(this.min, cap);
        else cap = Math.max(this.min, cap);
        this.cap = Math.min(this.cap, cap);
    }

    public int getDifficulty(Random random)
    {
        double mean = base + difficulty * scale;
        if (count > 0) mean += random.nextGaussian() * Math.sqrt(varSq);
        mean *= finalFactor;
        return (int) Math.round(MathHelper.clamp(mean, min, cap));
    }

    public void setTraitCap(int cap)
    {
        traitCap = Math.min(cap, traitCap);
    }

    public int getMaxTraitLevel()
    {
        return traitCap;
    }

    public double getApplyChance()
    {
        return fullChance ? 1 : applyChance;
    }

    public double getTraitChance(int lv)
    {
        return fullChance ? 1 : traitChance * Math.min(1, lv * LHConfig.common().scaling.initialTraitChanceSlope);
    }

    public int getBase()
    {
        return (int) Math.round(base + difficulty * scale);
    }

    public void setNoTraitCountCap()
    {
        traitCountCap = Integer.MAX_VALUE;
    }

    public void setFullChance()
    {
        fullChance = true;
    }

    public boolean isFullChance()
    {
        return fullChance;
    }

    public void setFullDrop()
    {
        fullDrop = true;
    }

    public boolean isFullDrop()
    {
        return fullDrop;
    }

    public void setPlayer(PlayerEntity player)
    {
        this.player = player instanceof ServerPlayerEntity sp ? sp : null;
    }

    public boolean hasAdvancement(Identifier id)
    {
        if (player == null) return true;
        var adv = player.server.getAdvancementLoader().get(id);
        if (adv == null) return false;
        var prog = player.getAdvancementTracker().getProgress(adv);
        return prog.isDone();
    }
}
