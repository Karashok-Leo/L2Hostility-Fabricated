package net.karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import net.karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

public class ChunkDifficultyComponent implements AutoSyncedComponent
{
    @Nullable
    public ChunkDifficulty diff = null;

    public ChunkDifficultyComponent(Chunk chunk)
    {
        if (chunk instanceof WorldChunk worldChunk)
        {
            this.diff = new ChunkDifficulty(worldChunk);
            diff.init();
        }
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        diff = TagCodec.fromTag(tag, ChunkDifficulty.class);
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        if (diff == null) return;
        TagCodec.toTag(tag, diff);
    }
}
