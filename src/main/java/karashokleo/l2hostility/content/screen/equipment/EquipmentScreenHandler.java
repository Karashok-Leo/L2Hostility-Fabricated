package karashokleo.l2hostility.content.screen.equipment;

import dev.xkmc.l2tabs.lib.menu.BaseInventoryScreenHandler;
import dev.xkmc.l2tabs.lib.menu.SpriteManager;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.init.LHMiscs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class EquipmentScreenHandler extends BaseInventoryScreenHandler<EquipmentScreenHandler>
{
    public static final SpriteManager MANAGER = new SpriteManager(L2Hostility.MOD_ID, "equipments");
    public static EquipmentSlot[] SLOTS = {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    @Nullable
    protected MobEntity mob;

    public EquipmentScreenHandler(ScreenHandlerType<?> type, int wid, PlayerInventory plInv, @Nullable MobEntity mob)
    {
        super(type, wid, plInv, MANAGER, EquipmentInventory::new, false);
        this.mob = mob;
        addSlot("hand", (i, e) -> isValid(SLOTS[i], e));
        addSlot("armor", (i, e) -> isValid(SLOTS[i + 2], e));
    }

    public static EquipmentScreenHandler fromNetwork(int wid, PlayerInventory plInv, PacketByteBuf buf)
    {
        Entity entity = null;
        if (L2HostilityClient.getClientWorld() != null)
        {
            entity = L2HostilityClient.getClientWorld().getEntityById(buf.readInt());
        }
        return new EquipmentScreenHandler(LHMiscs.EQUIPMENTS, wid, plInv, entity instanceof MobEntity golem ? golem : null);
    }

    private boolean isValid(EquipmentSlot slot, ItemStack stack)
    {
        if (mob == null || !canUse(playerInventory.player))
        {
            return false;
        }
        EquipmentSlot exp = LivingEntity.getPreferredEquipmentSlot(stack);
        if (exp == slot)
        {
            return true;
        }
        return !exp.isArmorSlot();
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return mob != null && !mob.isRemoved();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot)
    {
        if (mob != null)
        {
            ItemStack stack = this.slots.get(slot).getStack();
            if (slot >= 36)
            {
                this.insertItem(stack, 0, 36, true);
            } else
            {
                for (int i = 0; i < 6; i++)
                {
                    if (SLOTS[i] == LivingEntity.getPreferredEquipmentSlot(stack))
                    {
                        this.insertItem(stack, 36 + i, 37 + i, false);
                    }
                }
            }
            this.inventory.markDirty();
        }
        return ItemStack.EMPTY;
    }
}
