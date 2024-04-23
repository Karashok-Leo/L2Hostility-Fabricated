package net.karashokleo.l2hostility.content.screen.base;

import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Base Class for InventoryMenu.
 * Inventorys multiple helper functions.
 */
public class BaseContainerScreenHandler<T extends BaseContainerScreenHandler<T>> extends ScreenHandler
{
    private record SlotKey(String name, int i, int j) {
        private static final Comparator<SlotKey> COMPARATOR;
        static {
            Comparator<SlotKey> comp = Comparator.comparing(SlotKey::name);
            comp = comp.thenComparingInt(SlotKey::i);
            comp = comp.thenComparingInt(SlotKey::j);
            COMPARATOR = comp;
        }
    }

    /**
     * simple inventory that prevents looping change
     */
    public static class BaseInventory<T extends BaseContainerScreenHandler<T>> extends SimpleInventory
    {
        protected final T parent;
        private boolean updating = false;
        private int max = 64;

        public BaseInventory(int size, T menu) {
            super(size);
            parent = menu;
        }


        public BaseInventory<T> setMax(int max) {
            this.max = max;
            return this;
        }

        @Override
        public int getMaxCountPerStack() {
            return Math.min(max, super.getMaxCountPerStack());
        }

        @Override
        public void markDirty()
        {
            super.markDirty();
            if (!updating) {
                updating = true;
//                parent.slotsChanged(this);
                updating = false;
            }
        }
    }

    /**
     * return items in the slot to player
     */
    public static void clearSlot(PlayerEntity pPlayer, Inventory pInventory, int index) {
//        if (!pPlayer.isAlive() || pPlayer instanceof ServerPlayerEntity && ((ServerPlayerEntity) pPlayer).hasDisconnected()) {
//            pPlayer.drop(pInventory.removeItemNoUpdate(index), false);
//        } else {
//            Inventory inventory = pPlayer.getInventory();
//            if (inventory.player instanceof ServerPlayerEntity) {
//                inventory.placeItemBackInInventory(pInventory.removeItemNoUpdate(index));
//            }
//        }
    }

    public final PlayerInventory playerInventory;
    public final Inventory inventory;
//    public final SpriteManager sprite;
    protected int added = 0;
    protected final boolean isVirtual;

    private boolean updating = false;

    private final Map<SlotKey, Slot> slotMap = new TreeMap<>(SlotKey.COMPARATOR);

    /**
     * This contructor will bind player inventory first, so player inventory has lower slot index.
     *
     * @param type      registered menu type
//     * @param wid       window id
//     * @param plInv     player inventory
//     * @param manager   sprite manager used for slot positioning and rendering
     * @param factory   inventory supplier
     * @param isVirtual true if the slots should be cleared and item returned to player on menu close.
     */
    protected BaseContainerScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, /*SpriteManager manager,*/ Function<T, SimpleInventory> factory, boolean isVirtual)
    {
        super(type, syncId);
        this.playerInventory = playerInventory;
        this.inventory = factory.apply(Wrappers.cast(this));
//        sprite = manager;
//        int x = manager.get().getPlInvX();
//        int y = manager.get().getPlInvY();
//        this.bindPlayerInventory(playerInventory, x, y);
        this.isVirtual = isVirtual;
    }

    /**
     * Binds player inventory. Should not be called by others, but permits override.
     */
    protected void bindPlayerInventory(Inventory plInv, int x, int y) {
//        for (int i = 0; i < 3; ++i)
//            for (int j = 0; j < 9; ++j)
//                addSlot(createSlot(plInv, j + i * 9 + 9, x + j * 18, y + i * 18));
//        for (int k = 0; k < 9; ++k)
//            addSlot(createSlot(plInv, k, x + k * 18, y + 58));
    }

    /**
     * Used by bindPlayerInventory only. Create slots as needed. Some slots could be locked.
     */
//    protected Slot createSlot(Inventory inv, int slot, int x, int y) {
//        return shouldLock(inv, slot) ? new SlotLocked(inv, slot, x, y) : new Slot(inv, slot, x, y);
//    }

    /**
     * Lock slots you don't want players modifying, such as the slot player is opening backpack in.
     */
    protected boolean shouldLock(Inventory inv, int slot) {
        return false;
    }

    /**
     * Add new slots, with item input predicate
     */
    protected void addSlot(String name, Predicate<ItemStack> pred) {
//        sprite.get().getSlot(name, (x, y) -> new PredSlot(playerInventory, added++, x, y, pred), this::addSlot);
    }

    /**
     * Add new slots, with index-sensitive item input predicate.
     * The index here is relative to the first slot added by this method.
     */
    protected void addSlot(String name, BiPredicate<Integer, ItemStack> pred) {
//        int current = added;
//        sprite.get().getSlot(name, (x, y) -> {
//            int i = added - current;
//            var ans = new PredSlot(playerInventory, added, x, y, e -> pred.test(i, e));
//            added++;
//            return ans;
//        }, this::addSlot);
    }

    /**
     * Add new slots, with other modifications to the slot.
     */
//    protected void addSlot(String name, Predicate<ItemStack> pred, Consumer<PredSlot> modifier) {
//        sprite.get().getSlot(name, (x, y) -> {
//            PredSlot s = new PredSlot(playerInventory, added++, x, y, pred);
//            modifier.accept(s);
//            return s;
//        }, this::addSlot);
//    }

    /**
     * Add new slots, with index-sensitive modifications to the slot.
     * The index here is relative to the first slot added by this method.
     */
//    protected void addSlot(String name, BiPredicate<Integer, ItemStack> pred, BiConsumer<Integer, PredSlot> modifier) {
//        int current = added;
//        sprite.get().getSlot(name, (x, y) -> {
//            int i = added - current;
//            var ans = new PredSlot(playerInventory, added, x, y, e -> pred.test(i, e));
//            modifier.accept(i, ans);
//            added++;
//            return ans;
//        }, this::addSlot);
//    }

    /**
     * internal add slot
     */
    protected void addSlot(String name, int i, int j, Slot slot) {
        slotMap.put(new SlotKey(name, i, j), slot);
        this.addSlot(slot);
    }

    /**
     * get the slot by name and id in the grid
     */
    protected Slot getSlot(String name, int i, int j) {
        return slotMap.get(new SlotKey(name, i, j));
    }

    /**
     * get a slot as PredSlot, as most slots should be
     */
//    public PredSlot getAsPredSlot(String name, int i, int j) {
//        return (PredSlot) getSlot(name, i, j);
//    }

    /**
     * get a slot as PredSlot, as most slots should be
     */
//    public PredSlot getAsPredSlot(String name) {
//        return (PredSlot) getSlot(name, 0, 0);
//    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot)
    {
//        ItemStack stack = slots.get(id).getItem();
//        int n = playerInventory.getInventorySize();
//        boolean moved;
//        if (id >= 36) {
//            moved = moveItemStackTo(stack, 0, 36, true);
//        } else {
//            moved = moveItemStackTo(stack, 36, 36 + n, false);
//        }
//        if (moved) slots.get(id).setChanged();
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return player.isAlive();
    }

    @Override
    public void onClosed(PlayerEntity player)
    {
//        if (isVirtual && !player.getWorld().isClient())
//            clearInventoryFiltered(player, playerInventory);
        super.onClosed(player);
    }

    /**
     * return true (and when isVirtual is true), clear the corresponding slot on menu close.
     */
    protected boolean shouldClear(Inventory inventory, int slot) {
        return isVirtual;
    }

    /**
     * clear slots using shouldClear
     */
//    protected void clearInventoryFiltered(Player player, Inventory inventory) {
//        if (!player.isAlive() || player instanceof ServerPlayer && ((ServerPlayer) player).hasDisconnected()) {
//            for (int j = 0; j < inventory.getInventorySize(); ++j) {
//                if (shouldClear(inventory, j)) {
//                    player.drop(inventory.removeItemNoUpdate(j), false);
//                }
//            }
//        } else {
//            Inventory inventory = player.getInventory();
//            for (int i = 0; i < inventory.getInventorySize(); ++i) {
//                if (shouldClear(inventory, i)) {
//                    if (inventory.player instanceof ServerPlayer) {
//                        inventory.placeItemBackInInventory(inventory.removeItemNoUpdate(i));
//                    }
//                }
//            }
//
//        }
//    }

    @Override
    public void onContentChanged(Inventory inventory)
    {
        super.onContentChanged(inventory);
        if (playerInventory.player.getWorld().isClient()) {
            super.onContentChanged(inventory);
        } else {
            if (!updating) {
                updating = true;
                securedServerSlotChange(inventory);
                updating = false;
            }
            super.onContentChanged(inventory);
        }
    }

    /**
     * server side only slot change detector that will not be called recursively.
     */
    protected void securedServerSlotChange(Inventory cont) {

    }
}
