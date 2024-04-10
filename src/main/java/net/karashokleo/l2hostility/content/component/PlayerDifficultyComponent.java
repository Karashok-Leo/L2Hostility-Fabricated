package net.karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

public class PlayerDifficultyComponent implements ServerTickingComponent, AutoSyncedComponent
{
    public PlayerDifficulty diff;

    public PlayerDifficultyComponent(PlayerEntity player)
    {
        diff = new PlayerDifficulty(player);
    }

    @Override
    public void serverTick()
    {
        diff.tick();
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        diff = Objects.requireNonNull(TagCodec.fromTag(tag, PlayerDifficulty.class));
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        TagCodec.toTag(tag, diff);
    }
}
