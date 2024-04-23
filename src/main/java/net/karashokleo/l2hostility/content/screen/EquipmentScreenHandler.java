package net.karashokleo.l2hostility.content.screen;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class EquipmentScreenHandler extends ScreenHandler
{
    @Nullable
    private MobEntity mob;

    protected EquipmentScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId)
    {
        super(type, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot)
    {
        if (mob != null) {
            ItemStack stack = this.slots.get(slot).getStack();
            if (slot >= 36) {
                this.insertItem(stack, 0, 36, true);
            } else {
                for (int i = 0; i < 6; i++) {
//                    if (SLOTS[i] == LivingEntity.getPreferredEquipmentSlot(stack)) {
//                        this.insertItem(stack, 36 + i, 37 + i, false);
//                    }
                }
            }
//            this.container.setChanged();
        }
        return ItemStack.EMPTY;
    }

    private boolean isValid(EquipmentSlot slot, ItemStack stack) {
//        if (mob == null || !canUse(inventory.player)) {
//            return false;
//        }
        EquipmentSlot exp = LivingEntity.getPreferredEquipmentSlot(stack);
        if (exp == slot) return true;
        return !exp.isArmorSlot();
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return mob != null && !mob.isRemoved();
    }
}
