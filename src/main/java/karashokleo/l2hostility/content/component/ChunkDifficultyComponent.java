package karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkDifficultyComponent implements AutoSyncedComponent
{
    public final Chunk chunk;
    @Nullable
    public ChunkDifficulty diff;

    public ChunkDifficultyComponent(Chunk chunk)
    {
        this.chunk = chunk;
        this.diff = null;
        if (chunk instanceof WorldChunk worldChunk)
        {
            this.diff = new ChunkDifficulty(worldChunk);
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.diff = null;
        if (this.chunk instanceof WorldChunk worldChunk)
        {
            this.diff = TagCodec.fromTag(tag, ChunkDifficulty.class, new ChunkDifficulty(worldChunk), serialField -> true);
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (this.diff == null)
        {
            return;
        }
        TagCodec.toTag(tag, this.diff);
    }
}
