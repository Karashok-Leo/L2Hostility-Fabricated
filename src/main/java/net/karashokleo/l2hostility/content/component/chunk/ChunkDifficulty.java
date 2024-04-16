package net.karashokleo.l2hostility.content.component.chunk;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.init.LHComponents;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.WrapperProtoChunk;

import java.util.Optional;

@SerialClass
public class ChunkDifficulty implements RegionalDifficultyModifier
{
    public enum ChunkStage
    {
        PRE_INIT, INIT
    }

    // 从WorldChunk获取ChunkDifficulty
    public static Optional<ChunkDifficulty> get(WorldChunk chunk)
    {
        return chunk.isEmpty() ? Optional.empty() : Optional.ofNullable(chunk.getComponent(LHComponents.CHUNK_DIFFICULTY).diff);
    }

    // 同步客户端
    public void sync()
    {
        LHComponents.CHUNK_DIFFICULTY.sync(owner);
    }

    // 获取特定位置的ChunkDifficulty
    public static Optional<ChunkDifficulty> at(World world, BlockPos pos)
    {
        return at(world, pos.getX() >> 4, pos.getZ() >> 4);
    }

    // 获取特定位置的ChunkDifficulty
    public static Optional<ChunkDifficulty> at(World world, int x, int z)
    {
        Chunk chunk = world.getChunk(x, z, ChunkStatus.CARVERS, false);
        if (chunk instanceof WrapperProtoChunk im)
            chunk = im.getWrappedChunk();
        if (chunk instanceof WorldChunk c)
            return get(c);
        return Optional.empty();
    }

    public final WorldChunk owner;
    @SerialClass.SerialField
    private ChunkStage stage = ChunkStage.PRE_INIT;
    @SerialClass.SerialField
    private SectionDifficulty[] sections;

    public ChunkDifficulty(WorldChunk owner)
    {
        this.owner = owner;
    }

    // 检查是否已经初始化
    private void check()
    {
        if (stage == ChunkStage.PRE_INIT)
        {
            init();
            stage = ChunkStage.INIT;
        }
    }

    // 获得特定Section分区的SectionDifficulty
    public SectionDifficulty getSection(int y)
    {
        check();
        int index = (y >> 4) - owner.getBottomSectionCoord();
        index = MathHelper.clamp(index, 0, sections.length - 1);
        return sections[index];
    }

    // 修改MobDifficultyCollector
    public void modifyInstance(BlockPos pos, MobDifficultyCollector instance)
    {
        check();
        getSection(pos.getY()).modifyInstance(owner.getWorld(), pos, instance);
    }

    // 增加击杀记录
    public void addKillHistory(PlayerEntity player, LivingEntity mob, MobDifficulty diff)
    {
        check();
        BlockPos pos = mob.getBlockPos();
        int index = -owner.getBottomSectionCoord() + (pos.getY() >> 4);
        if (index >= 0 && index < sections.length)
            sections[index].addKillHistory(this, player, mob, diff);
    }

    // 初始化SectionDifficulty数组
    @SerialClass.OnInject
    public void init()
    {
        int size = owner.getWorld().countVerticalSections();
        if (sections == null || sections.length != size)
        {
            sections = new SectionDifficulty[size];
            for (int i = 0; i < size; i++)
            {
                sections[i] = new SectionDifficulty();
                sections[i].index = owner.getBottomSectionCoord() + i;
            }
        }
        for (int i = 0; i < size; i++)
            sections[i].section = owner.getSection(i);
        sync();
    }
}
