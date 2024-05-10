package net.karashokleo.l2hostility.compat.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.*;
import net.karashokleo.l2hostility.compat.trinket.slot.EntitySlotAccess;
import net.karashokleo.l2hostility.compat.trinket.slot.EquipmentSlotAccess;
import net.karashokleo.l2hostility.compat.trinket.slot.TrinketSlotAccess;
import net.karashokleo.l2hostility.content.item.trinket.core.EffectValidItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class TrinketCompat
{
    public static boolean isEffectValidInTrinket(StatusEffectInstance effectInstance, LivingEntity entity)
    {
        return TrinketsApi.getTrinketComponent(entity).map(trinketComponent ->
                trinketComponent.isEquipped(stack ->
                        stack.getItem() instanceof EffectValidItem effectValidItem &&
                                effectValidItem.isEffectValid(effectInstance, stack, entity))).orElse(false);
    }

    public static boolean hasItemEquippedOrInTrinket(LivingEntity le, Item item)
    {
        return hasItemEquipped(le, item) || hasItemInTrinket(le, item);
    }

    public static boolean hasItemEquipped(LivingEntity le, Item item)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
            if (le.getEquippedStack(e).isOf(item))
                return true;
        return false;
    }

    public static boolean hasItemInTrinket(LivingEntity le, Item item)
    {
        return TrinketsApi.getTrinketComponent(le).map(trinketComponent -> trinketComponent.isEquipped(item)).orElse(false);
    }

    public static List<ItemStack> getItems(LivingEntity le, Predicate<ItemStack> pred)
    {
        List<ItemStack> ans = new ArrayList<>();
        getEquippedItems(ans, le, pred);
        getTrinketItems(ans, le, pred);
        return ans;
    }

    public static void getEquippedItems(List<ItemStack> list, LivingEntity le, Predicate<ItemStack> pred)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
        {
            ItemStack stack = le.getEquippedStack(e);
            if (!stack.isEmpty() && pred.test(stack))
                list.add(stack);
        }
    }

    private static void getTrinketItems(List<ItemStack> list, LivingEntity le, Predicate<ItemStack> pred)
    {
        var opt = TrinketsApi.getTrinketComponent(le);
        if (opt.isEmpty()) return;
        for (Map<String, TrinketInventory> map : opt.get().getInventory().values())
            for (TrinketInventory inventory : map.values())
                for (int i = 0; i < inventory.size(); ++i)
                {
                    ItemStack stack = inventory.getStack(i);
                    if (!stack.isEmpty() && pred.test(stack))
                        list.add(stack);
                }
    }

    public static List<EntitySlotAccess> getItemAccess(LivingEntity le)
    {
        List<EntitySlotAccess> ans = new ArrayList<>();
        getItemAccessEquipped(ans, le);
        getItemAccessInTrinket(ans, le);
        return ans;
    }

    public static void getItemAccessEquipped(List<EntitySlotAccess> list, LivingEntity le)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
            list.add(new EquipmentSlotAccess(le, e));
    }

    private static void getItemAccessInTrinket(List<EntitySlotAccess> list, LivingEntity le)
    {
        var opt = TrinketsApi.getTrinketComponent(le);
        if (opt.isEmpty()) return;
        for (Map.Entry<String, Map<String, TrinketInventory>> entry : opt.get().getInventory().entrySet())
            for (Map.Entry<String, TrinketInventory> inventoryEntry : entry.getValue().entrySet())
                for (int i = 0; i < inventoryEntry.getValue().size(); ++i)
                    list.add(new TrinketSlotAccess(le, entry.getKey(), inventoryEntry.getKey(), i));
    }

    @Nullable
    public static EntitySlotAccess decode(String id, LivingEntity le)
    {
        try
        {
            var strs = id.split("/");
            if (strs[0].equals("equipment"))
                return new EquipmentSlotAccess(le, EquipmentSlot.byName(strs[1]));
            else if (strs[0].equals("trinket"))
                return new TrinketSlotAccess(le, strs[1], strs[2], Integer.parseInt(strs[3]));
        } catch (Exception ignored)
        {
        }
        return null;
    }

    public static boolean isSlotAdder(EntitySlotAccess access)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> multimap = null;
        ItemStack stack = access.get();
        if (access instanceof EquipmentSlotAccess equipmentSlotAccess)
            multimap = stack.getAttributeModifiers(equipmentSlotAccess.slot());
        else if (access instanceof TrinketSlotAccess trinketSlotAccess &&
                stack.getItem() instanceof Trinket trinket)
            multimap = trinket.getModifiers(stack, new SlotReference(trinketSlotAccess.getInventory(), trinketSlotAccess.slot()), trinketSlotAccess.le(), UUID.randomUUID());
        if (multimap != null)
            for (var e : multimap.keySet())
                if (e instanceof SlotAttributes.SlotEntityAttribute)
                    return true;
        return false;
    }
}
