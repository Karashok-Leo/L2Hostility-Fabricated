package karashokleo.l2hostility.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.xkmc.l2serial.util.Wrappers;
import karashokleo.l2hostility.content.enchantment.special.ItemSafeGuard;
import karashokleo.l2hostility.content.enchantment.special.LifeSyncEnchantment;
import karashokleo.l2hostility.content.event.GenericEvents;
import karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
    @Inject(
            method = "inventoryTick",
            at = @At("HEAD")
    )
    public void inject_inventoryTick(World world, Entity entity, int slot, boolean selected, CallbackInfo ci)
    {
        ItemStack stack = Wrappers.cast(this);
        EnchantmentDisabler.tickStack(world, entity, stack);
        ItemSafeGuard.inventoryTick(stack);
    }

    // Hardened
    @ModifyVariable(at = @At("LOAD"), method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", argsOnly = true)
    public int inject_damage_hardened(int amount)
    {
        ItemStack self = (ItemStack) (Object) this;
        if (amount > 1 && EnchantmentHelper.getLevel(LHEnchantments.HARDENED, self) > 0) return 1;
        return amount;
    }

    // Life Sync & Eternal
    @Inject(
            method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public <T extends LivingEntity> void inject_damage_lifeSync(int amount, T living, Consumer<T> onBroken, CallbackInfo ci)
    {
        ItemStack self = (ItemStack) (Object) this;
        if (living.getWorld().isClient()) return;

        if (EnchantmentHelper.getLevel(LHEnchantments.ETERNAL, self) > 0)
            ci.cancel();

        if (EnchantmentHelper.getLevel(LHEnchantments.LIFE_SYNC, self) > 0)
        {
            GenericEvents.schedule(() -> living.damage(
                    LifeSyncEnchantment.getSource(living.getWorld()),
                    (float) (amount * LHConfig.common().complements.properties.lifeSyncFactor)
            ));
            ci.cancel();
        }
    }

    // Durable Armor
    @ModifyReturnValue(at = @At("RETURN"), method = "getMaxDamage")
    public int inject_getMaxDamage_durabilityEnchantment(int max)
    {
        ItemStack self = (ItemStack) (Object) this;
        int lv = EnchantmentHelper.getLevel(LHEnchantments.DURABLE_ARMOR, self);
        return max * (1 + lv);
    }

    // Safeguard
    @WrapOperation(
            method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;setDamage(I)V"
            )
    )
    public void inject_damage_safeguard(ItemStack self, int val, Operation<Void> op)
    {
        if (ItemSafeGuard.shouldPreventBreak(self, val))
            val = self.getMaxDamage() - 1;
        op.call(self, val);
//        int max = self.getMaxDamage();
//        if (max <= val + 1 && EnchantmentHelper.getLevel(LHEnchantments.SAFEGUARD, self) > 0)
//        {
//            var opt = L2Hostility.getServer();
//            int old = self.getDamage();
//            String key = "l2hostility:safeguard";
//            long time = self.getOrCreateNbt().getLong(key);
//            long current = opt.getOverworld().getTime();
//            if (max <= val)
//            {
//                if (current == time)
//                {
//                    val = old;
//                } else if (max > old + 1)
//                {
//                    val = max - 1;
//                    self.getOrCreateNbt().putLong(key, current);
//                }
//            } else if (max == val + 1)
//            {
//                self.getOrCreateNbt().putLong(key, current);
//            }
//        }
//        op.call(self, val);
    }

    @ModifyReturnValue(
            at = @At("RETURN"),
            method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z"
    )
    public boolean inject_damage_safeguard_preventBreaking(boolean broken)
    {
        ItemStack self = (ItemStack) (Object) this;
        return broken && self.getDamage() >= self.getMaxDamage();
    }
}
