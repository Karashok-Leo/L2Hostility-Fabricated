package net.karashokleo.l2hostility.content.network;

import dev.xkmc.l2serial.network.SerialPacketS2C;
import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import net.karashokleo.l2hostility.compat.loot.ITraitLootRecipe;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class S2CLootData implements SerialPacketS2C
{
    @SerialClass.SerialField
    public ArrayList<NbtCompound> list = new ArrayList<>();

    @Deprecated
    public S2CLootData()
    {
    }

    public S2CLootData(List<IGlobalLootModifier> list)
    {
        for (var e : list)
        {
            var res = IGlobalLootModifier.DIRECT_CODEC.encodeStart(NbtOps.INSTANCE, e).result();
            if (res.isPresent() && res.get() instanceof NbtCompound ct)
                this.list.add(ct);
        }
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        ITraitLootRecipe.LIST_CACHE.clear();
        for (var ct : list)
        {
            var ans = IGlobalLootModifier.DIRECT_CODEC.decode(NbtOps.INSTANCE, ct).result();
            if (ans.isEmpty()) continue;
            if (ans.get().getFirst() instanceof ITraitLootRecipe mod)
                ITraitLootRecipe.LIST_CACHE.add(mod);
        }
    }
}
