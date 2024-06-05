package net.karashokleo.l2hostility.mixin;

import dev.xkmc.l2serial.util.Wrappers;
import net.karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Inject(
            method = "inventoryTick",
            at = @At("HEAD")
    )
    public void inject_inventoryTick(World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        EnchantmentDisabler.tickStack(world, entity, Wrappers.cast(this));
    }
}
