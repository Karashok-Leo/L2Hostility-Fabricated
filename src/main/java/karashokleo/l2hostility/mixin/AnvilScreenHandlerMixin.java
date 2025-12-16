package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.content.item.consumable.BookCopy;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler
{
    @Shadow
    @Final
    private Property levelCost;

    @Shadow
    private int repairItemUsage;

    private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(type, syncId, playerInventory, context);
    }

    @Inject(
        method = "updateResult",
        at = @At("HEAD"),
        cancellable = true
    )
    private void inject_updateResult(CallbackInfo ci)
    {
        ItemStack left = this.input.getStack(0);
        ItemStack right = this.input.getStack(1);
        MutableInt mutRepairItemUsage = new MutableInt(this.repairItemUsage);
        if (BookCopy.onAnvilUpdate(left, right, this.output, this.levelCost, mutRepairItemUsage))
        {
            this.repairItemUsage = mutRepairItemUsage.getValue();
            ci.cancel();
        }
    }

    @Inject(
        method = "onTakeOutput",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V",
            ordinal = 0
        )
    )
    private void inject_onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfo ci)
    {
        ItemStack left = this.input.getStack(0);
        ItemStack right = this.input.getStack(1);
        BookCopy.onTakeOutput(left, right, player);
    }
}
