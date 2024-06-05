package net.karashokleo.l2hostility.content.item.complements;

import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;

import java.util.function.Supplier;

public class RefinedRadianceItem extends NoGravMagicalItem
{
    public RefinedRadianceItem(Settings settings, Supplier<MutableText> textSupplier)
    {
        super(settings, textSupplier);
    }

    @Override
    protected void onCreated(ItemEntity entity, NbtCompound persistentData)
    {
        super.onCreated(entity, persistentData);
        entity.setVelocity(entity.getVelocity().add(0, .25f, 0));
    }
}
