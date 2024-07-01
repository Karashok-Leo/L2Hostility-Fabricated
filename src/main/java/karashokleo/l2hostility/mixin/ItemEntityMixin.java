package karashokleo.l2hostility.mixin;

import karashokleo.l2hostility.content.item.complements.NoGravMagicalItem;
import karashokleo.l2hostility.content.recipe.BurntRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity
{
    @Shadow
    public abstract ItemStack getStack();

    private ItemEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void inject_tick(CallbackInfo ci)
    {
        if (this.getStack().getItem() instanceof NoGravMagicalItem item &&
                item.onEntityItemUpdate((ItemEntity) (Object) this))
            ci.cancel();
    }

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;onItemEntityDestroyed(Lnet/minecraft/entity/ItemEntity;)V"
            )
    )
    private void inject_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir)
    {
        if (source.isIn(DamageTypeTags.IS_FIRE))
            BurntRecipe.onItemKill(getWorld(), this, getStack());
    }
}
