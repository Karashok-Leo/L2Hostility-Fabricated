package net.karashokleo.l2hostility.content.component.chunk;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.data.LHData;
import net.karashokleo.l2hostility.data.config.WorldDifficultyConfig;
import net.karashokleo.l2hostility.content.logic.DifficultyLevel;
import net.karashokleo.l2hostility.content.logic.LevelEditor;
import net.karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;

import java.util.List;
import java.util.Optional;

@SerialClass
public class SectionDifficulty
{
    public enum SectionStage
    {
        INIT, CLEARED
    }

    // 获取特定位置的SectionDifficulty
    public static Optional<SectionDifficulty> sectionAt(World world, BlockPos pos)
    {
        return ChunkDifficulty.at(world, pos).map(e -> e.getSection(pos.getY()));
    }

    ChunkSection section;
    @SerialClass.SerialField
    int index;
    @SerialClass.SerialField
    public BlockPos activePos = null;
    @SerialClass.SerialField
    private SectionStage stage = SectionStage.INIT;
    @SerialClass.SerialField
    private final DifficultyLevel difficulty = new DifficultyLevel();

    // 修改MobDifficultyCollector
    public void modifyInstance(World world, BlockPos pos, MobDifficultyCollector instance)
    {
        modifyInstanceInternal(world, pos, instance);
        if (LHConfig.common().scaling.allowSectionDifficulty)
            instance.acceptBonusLevel(difficulty.getLevel());
        if (stage == SectionStage.CLEARED)
            instance.setCap(0);
    }

    private void modifyInstanceInternal(World world, BlockPos pos, MobDifficultyCollector instance)
    {
        var levelDiff = LHData.difficulties.levelMap.get(world.getDimensionKey().getValue());
        if (levelDiff == null)
            levelDiff = WorldDifficultyConfig.defaultLevel();
        instance.acceptConfig(levelDiff);
        world.getBiome(pos).getKey().map(e -> LHData.difficulties.biomeMap.get(e.getValue())).ifPresent(instance::acceptConfig);
        instance.acceptBonusLevel((int) Math.round(LHConfig.common().scaling.distanceFactor *
                Math.sqrt(pos.getX() * pos.getX() + pos.getZ() * pos.getZ())));
    }

    // 获取SectionDifficulty文本
    public List<Text> getSectionDifficultyDetail(PlayerEntity player)
    {
        if (isCleared()) return List.of();
        var levelDiff = LHData.difficulties.levelMap.get(player.getWorld().getDimensionKey().getValue());
        int dim = levelDiff == null ? WorldDifficultyConfig.defaultLevel().base() : levelDiff.base();
        BlockPos pos = player.getBlockPos();
        RegistryEntry<Biome> biome = player.getWorld().getBiome(pos);
        int bio = biome.getKey().map(e -> LHData.difficulties.biomeMap.get(e.getRegistry()))
                .map(WorldDifficultyConfig.DifficultyConfig::base).orElse(0);
        int dist = (int) Math.round(LHConfig.common().scaling.distanceFactor *
                Math.sqrt(pos.getX() * pos.getX() + pos.getZ() * pos.getZ()));
        int adaptive = difficulty.getLevel();
        return List.of(
                LHTexts.INFO_SECTION_DIM_LEVEL.get(dim).formatted(Formatting.GRAY),
                LHTexts.INFO_SECTION_BIOME_LEVEL.get(bio).formatted(Formatting.GRAY),
                LHTexts.INFO_SECTION_DISTANCE_LEVEL.get(dist).formatted(Formatting.GRAY),
                LHTexts.INFO_SECTION_ADAPTIVE_LEVEL.get(adaptive).formatted(Formatting.GRAY)
        );
    }

    public boolean isCleared()
    {
        return stage == SectionStage.CLEARED;
    }

    public boolean setClear(ChunkDifficulty chunk, BlockPos pos)
    {
        if (stage == SectionStage.CLEARED) return false;
        stage = SectionStage.CLEARED;
        chunk.owner.setNeedsSaving(true);
        chunk.sync();
        return true;
    }

    public boolean setUnclear(ChunkDifficulty chunk, BlockPos pos)
    {
        if (stage == SectionStage.INIT) return false;
        stage = SectionStage.INIT;
        chunk.owner.setNeedsSaving(true);
        chunk.sync();
        return true;
    }

    // 增加击杀记录
    public void addKillHistory(ChunkDifficulty chunk, PlayerEntity player, LivingEntity mob, MobDifficulty diff)
    {
        difficulty.grow(1, diff);
        chunk.owner.setNeedsSaving(true);
        chunk.sync();
    }

    public LevelEditor getLevelEditor(World world, BlockPos pos)
    {
        MobDifficultyCollector col = new MobDifficultyCollector();
        modifyInstanceInternal(world, pos, col);
        var diff = LHConfig.common().scaling.allowSectionDifficulty ?
                difficulty : new DifficultyLevel();
        return new LevelEditor(diff, col.getBase());
    }

    public double getScale(World world, BlockPos pos)
    {
        MobDifficultyCollector col = new MobDifficultyCollector();
        modifyInstanceInternal(world, pos, col);
        return col.scale;
    }
}
