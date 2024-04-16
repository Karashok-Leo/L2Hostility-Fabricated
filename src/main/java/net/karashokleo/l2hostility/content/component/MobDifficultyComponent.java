package net.karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MobDifficultyComponent implements ServerTickingComponent, AutoSyncedComponent, CopyableComponent<MobDifficultyComponent>
{
    public MobEntity mob;
    @Nullable
    public MobDifficulty diff;

    public MobDifficultyComponent(MobEntity mob)
    {
        this.mob = mob;
        this.diff = null;
        if (validate())
            this.diff = new MobDifficulty(mob);
    }

    private boolean validate()
    {
        return (this.mob.getType().isIn(LHTags.WHITELIST) ||
                (this.mob instanceof Monster &&
                        !this.mob.getType().isIn(LHTags.BLACKLIST)));
    }

    @Override
    public void serverTick()
    {
        if (this.diff != null)
            this.diff.tick();
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.diff = null;
        if (validate())
            this.diff = TagCodec.fromTag(tag, MobDifficulty.class, new MobDifficulty(this.mob), serialField -> true);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (this.diff == null) return;
        TagCodec.toTag(tag, this.diff);
    }

    @Override
    public void copyFrom(MobDifficultyComponent other)
    {
        NbtCompound tag = new NbtCompound();
        other.writeToNbt(tag);
        this.readFromNbt(tag);
    }
}
