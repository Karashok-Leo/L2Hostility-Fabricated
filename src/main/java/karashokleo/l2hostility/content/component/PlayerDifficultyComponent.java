package karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class PlayerDifficultyComponent implements ServerTickingComponent, AutoSyncedComponent
{
    public PlayerEntity player;
    public PlayerDifficulty diff;

    public PlayerDifficultyComponent(PlayerEntity player)
    {
        this.player = player;
        this.diff = new PlayerDifficulty(player);
    }

    @Override
    public void serverTick()
    {
        this.diff.tick();
    }


    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.diff = TagCodec.fromTag(tag, PlayerDifficulty.class, new PlayerDifficulty(this.player), serialField -> true);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        TagCodec.toTag(tag, this.diff);
    }
}
