package karashokleo.l2hostility.content.item.consumable.charge;

import karashokleo.l2hostility.content.entity.fireball.BaseFireballEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

public class FireChargeBehavior extends ItemDispenserBehavior
{
    protected final BaseChargeItem.BlockFire<?> blockFire;

    public FireChargeBehavior(BaseChargeItem.BlockFire<?> blockFire)
    {
        this.blockFire = blockFire;
    }

    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack)
    {
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        Position position = DispenserBlock.getOutputLocation(pointer);
        double d0 = position.getX() + (double) ((float) direction.getOffsetX() * 0.3F);
        double d1 = position.getY() + (double) ((float) direction.getOffsetY() * 0.3F);
        double d2 = position.getZ() + (double) ((float) direction.getOffsetZ() * 0.3F);
        World world = pointer.getWorld();
        Random random = world.random;
        double d3 = random.nextTriangular(direction.getOffsetX(), 0.11485D);
        double d4 = random.nextTriangular(direction.getOffsetY(), 0.11485D);
        double d5 = random.nextTriangular(direction.getOffsetZ(), 0.11485D);
        BaseFireballEntity fireBall = blockFire.create(d0, d1, d2, 0, 0, 0, world);
        fireBall.setItem(stack);
        fireBall.setVelocity(new Vec3d(d3, d4, d5).normalize());
        world.spawnEntity(fireBall);
        stack.decrement(1);
        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer)
    {
        pointer.getWorld().syncWorldEvent(WorldEvents.BLAZE_SHOOTS, pointer.getPos(), 0);
    }
}
