package karashokleo.l2hostility.content.item.complements;

import karashokleo.l2hostility.init.LHDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class VoidEye extends ShadowSteelItem
{
    public VoidEye(Settings settings, Supplier<MutableText> textSupplier)
    {
        super(settings, textSupplier);
    }

    @Override
    public void onEntityItemUpdate(ItemEntity entity)
    {
        super.onEntityItemUpdate(entity);
        Vec3d v = entity.getVelocity();
        if (v.getY() < 0.05)
        {
            entity.setVelocity(v.add(0, 0.005, 0));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if (!world.isClient() &&
            entity instanceof LivingEntity le &&
            le.getPos().getY() < world.getBottomY())
        {
            stack.setCount(0);
            le.damage(entity.getDamageSources().create(LHDamageTypes.VOID_EYE), le.getMaxHealth());
        }
    }
}
