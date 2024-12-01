package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.content.item.consumable.BookCopy;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
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
        if (left.getItem() instanceof BookCopy &&
            right.getItem() instanceof EnchantedBookItem)
        {
            var map = EnchantmentHelper.get(right);
            int cost = 0;
            for (var e : map.entrySet())
                cost += BookCopy.cost(e.getKey(), e.getValue());
            ItemStack result = right.copy();
            result.setCount(left.getCount() + right.getCount());
            this.output.setStack(0, result);
            this.levelCost.set(cost);
            this.repairItemUsage = right.getCount();
            ci.cancel();
        }
    }
}
