package net.karashokleo.l2hostility.content.item.wand;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.util.raytrace.ClientUpdatePacket;
import net.karashokleo.l2hostility.util.raytrace.IGlowingTarget;
import net.karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
        if (!world.isClient() && selected && entity instanceof ServerPlayerEntity player)
            ServerPlayNetworking.send(player, L2Hostility.HANDLER.getPacket(new ClientUpdatePacket(64)));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient())
        {
            LivingEntity target = RayTraceUtil.serverGetTarget(user);
            if (target != null) clickTarget(stack, user, target);
            else clickNothing(stack, user);
        }
        return TypedActionResult.success(stack);
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
