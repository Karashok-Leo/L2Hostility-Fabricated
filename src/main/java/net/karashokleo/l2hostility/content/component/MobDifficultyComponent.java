package net.karashokleo.l2hostility.content.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.CopyableComponent;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import dev.xkmc.l2serial.serialization.codec.TagCodec;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public class MobDifficultyComponent<C extends Component> implements ServerTickingComponent, AutoSyncedComponent, CopyableComponent<C>
{
    @Nullable
    public MobDifficulty diff = null;

    public MobDifficultyComponent(LivingEntity le)
    {
        if (le instanceof MobEntity mob &&
                (mob.getType().isIn(LHTags.WHITELIST) ||
                        (mob instanceof Monster &&
                                !mob.getType().isIn(LHTags.BLACKLIST))))
            this.diff = new MobDifficulty(mob);
    }

    @Override
    public void serverTick()
    {
        if (diff != null)
            diff.tick();
    }

    @Override
    public void readFromNbt(NbtCompound tag)
    {
        this.diff = TagCodec.fromTag(tag, MobDifficulty.class);
    }

    @Override
    public void writeToNbt(NbtCompound tag)
    {
        if (diff == null) return;
        TagCodec.toTag(tag, diff);
    }

    @Override
    public void copyFrom(Component other)
    {
        NbtCompound tag = new NbtCompound();
        other.writeToNbt(tag);
        this.readFromNbt(tag);
    }
}
