package karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
            this.diff.serverTick();
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag)
    {
        this.diff = null;
        if (validate())
        {
            this.diff = TagCodec.fromTag(tag, MobDifficulty.class, new MobDifficulty(this.mob), serialField -> true);
            if (this.diff != null)
            {
                this.diff.traits.keySet().removeIf(Objects::isNull);
                this.diff.traits.keySet().removeIf(MobTrait::isBanned);
            }
        }
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag)
    {
        if (this.diff == null) return;
        TagCodec.toTag(tag, this.diff);
    }

    @Override
    public void copyFrom(@NotNull MobDifficultyComponent other)
    {
        if (this.diff == null || other.diff == null) return;
        this.diff.copyFrom(other.mob, this.mob, other.diff);
    }
}
