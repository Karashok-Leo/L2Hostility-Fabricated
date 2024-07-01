package karashokleo.l2hostility.content.item.complements;

import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.MathHelper;

import java.util.function.Supplier;

public class ShadowSteelItem extends NoGravMagicalItem
{
    public ShadowSteelItem(Settings settings, Supplier<MutableText> textSupplier)
    {
        super(settings, textSupplier);
    }

    @Override
    protected void onCreated(ItemEntity entity, NbtCompound persistentData)
    {
        super.onCreated(entity, persistentData);
        float yMotion = (entity.fallDistance + 3) / 50f;
        entity.setVelocity(0, yMotion, 0);
    }

    @Override
    protected float getIdleParticleChance(ItemEntity entity)
    {
        return (float) (MathHelper.clamp(entity.getStack().getCount() - 10, MathHelper.clamp(entity.getVelocity().y * 20, 5, 20), 100) / 64f);
    }
}
