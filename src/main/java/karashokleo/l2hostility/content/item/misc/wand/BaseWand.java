package karashokleo.l2hostility.content.item.misc.wand;

import karashokleo.l2hostility.util.raytrace.IGlowingTarget;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public abstract class BaseWand extends Item implements IGlowingTarget, IMobClickItem
{
    public BaseWand(Settings settings)
    {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        if (world.isClient() && selected && entity instanceof PlayerEntity player)
        {
            RayTraceUtil.clientUpdateTarget(player, 64);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient())
        {
            LivingEntity target = RayTraceUtil.serverGetTarget(user);
            if (target != null)
            {
                clickTarget(stack, user, target);
            } else
            {
                clickNothing(stack, user);
            }
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand)
    {
        return super.useOnEntity(stack, user, entity, hand);
    }

    public abstract void clickTarget(ItemStack stack, PlayerEntity player, LivingEntity entity);

    public void clickNothing(ItemStack stack, PlayerEntity player)
    {
    }

    @Override
    public int getDistance(ItemStack stack)
    {
        return 64;
    }
}
