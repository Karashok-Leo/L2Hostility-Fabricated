package net.karashokleo.l2hostility.client;

import dev.xkmc.l2serial.network.SerialPacketBase;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

@SerialClass
public class TraitEffectToClient implements SerialPacketBase
{
    @SerialClass.SerialField
    public int id;
    @SerialClass.SerialField
    public MobTrait trait;
    @SerialClass.SerialField
    public TraitEffect effect;
    @SerialClass.SerialField
    public BlockPos pos = BlockPos.ORIGIN;

    @Deprecated
    public TraitEffectToClient()
    {
    }

    public TraitEffectToClient(LivingEntity e, MobTrait trait, TraitEffect effect)
    {
        id = e.getId();
        this.trait = trait;
        this.effect = effect;
    }

    public TraitEffectToClient(BlockPos pos, TraitEffect effect)
    {
        this.pos = pos;
        this.effect = effect;
    }
}