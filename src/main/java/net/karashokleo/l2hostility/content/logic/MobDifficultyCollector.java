package net.karashokleo.l2hostility.content.logic;

import net.karashokleo.l2hostility.data.config.DifficultyConfig;
import net.karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class MobDifficultyCollector
{
    public static MobDifficultyCollector noTrait(int lv) {
        var ans = new MobDifficultyCollector();
        ans.trait_chance = 0;
        ans.base = lv;
        return ans;
    }

    public int min, base, count, difficulty, cap = Integer.MAX_VALUE, traitCap = TraitManager.getMaxLevel() + 1;
    public double scale, varSq, apply_chance, trait_chance, trait_cost, finalFactor = 1;
    private ServerPlayerEntity player;
    private boolean fullChance, fullDrop;

    public MobDifficultyCollector() {
        apply_chance = LHConfig.common().scaling.globalApplyChance;
        trait_chance = LHConfig.common().scaling.globalTraitChance;
        trait_cost = 1;
    }

    public void acceptConfig(DifficultyConfig.Config config) {
        min = Math.max(min, config.min());
        base += config.base();
        scale += config.scale();
        varSq += config.variation() * config.variation();
        count++;
        apply_chance *= config.apply_chance();
        trait_chance *= config.trait_chance();
        fullChance |= min > 0;
    }

    public void acceptBonus(DifficultyLevel difficulty) {
        this.difficulty += difficulty.getLevel();
    }

    public void acceptBonusLevel(int difficulty) {
        this.base += difficulty;
    }

    public void acceptBonusFactor(double finalFactor) {
        this.finalFactor *= finalFactor;
    }

    public void traitCostFactor(double factor) {
        trait_cost *= factor;
    }

    public void setCap(int cap) {
        if (LHConfig.common().scaling.allowBypassMinimum)
            this.min = Math.min(this.min, cap);
        else cap = Math.max(this.min, cap);
        this.cap = Math.min(this.cap, cap);
    }

    public int getDifficulty(Random random) {
        double mean = base + difficulty * scale;
        if (count > 0) mean += random.nextGaussian() * Math.sqrt(varSq);
        mean *= finalFactor;
        return (int) Math.round(MathHelper.clamp(mean, min, cap));
    }

    public void setTraitCap(int cap) {
        traitCap = Math.min(cap, traitCap);
    }

    public int getMaxTraitLevel() {
        return traitCap;
    }

    public double apply_chance() {
        return fullChance ? 1 : apply_chance;
    }

    public double trait_chance(int lv) {
        return fullChance ? 1 : trait_chance * Math.min(1, lv * LHConfig.common().scaling.initialTraitChanceSlope);
    }

    public int getBase() {
        return (int) Math.round(base + difficulty * scale);
    }

    public void setFullChance() {
        fullChance = true;
    }

    public boolean isFullChance() {
        return fullChance;
    }

    public void setFullDrop() {
        fullDrop = true;
    }

    public boolean isFullDrop() {
        return fullDrop;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player instanceof ServerPlayerEntity sp ? sp : null;
    }

    public boolean hasAdvancement(Identifier id) {
        if (player == null) return true;
        var adv = player.server.getAdvancementLoader().get(id);
        if (adv == null) return false;
        var prog = player.getAdvancementTracker().getProgress(adv);
        return prog.isDone();
    }
}
