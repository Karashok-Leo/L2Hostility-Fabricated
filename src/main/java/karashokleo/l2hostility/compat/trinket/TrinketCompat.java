package karashokleo.l2hostility.compat.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.*;
import karashokleo.l2hostility.compat.trinket.slot.EntitySlotAccess;
import karashokleo.l2hostility.compat.trinket.slot.EquipmentSlotAccess;
import karashokleo.l2hostility.compat.trinket.slot.TrinketSlotAccess;
import karashokleo.l2hostility.content.item.trinket.core.EffectValidItem;
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
    /*
     * 判断在该饰品影响下效果是否不受净化影响
     * true: 保留该效果
     * false: 净化该效果
     */
    public static boolean isEffectValidInTrinket(StatusEffectInstance effectInstance, LivingEntity entity)
    {
        return TrinketsApi.getTrinketComponent(entity).map(trinketComponent ->
                trinketComponent.isEquipped(stack ->
                        stack.getItem() instanceof EffectValidItem effectValidItem &&
                        effectValidItem.isEffectValid(effectInstance, stack, entity))).orElse(false);
    }

    // 装备栏或者饰品栏是否存在item
    public static boolean hasItemEquippedOrInTrinket(LivingEntity le, Item item)
    {
        return hasItemEquipped(le, item) || hasItemInTrinket(le, item);
    }

    // 装备栏是否存在item
    public static boolean hasItemEquipped(LivingEntity le, Item item)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
            if (le.getEquippedStack(e).isOf(item))
                return true;
        return false;
    }

    // 饰品栏是否存在item
    public static boolean hasItemInTrinket(LivingEntity le, Item item)
    {
        return TrinketsApi.getTrinketComponent(le).map(trinketComponent -> trinketComponent.isEquipped(item)).orElse(false);
    }

    // 获取装备栏与饰品栏中的所有 ItemStack
    public static List<ItemStack> getItems(LivingEntity le, Predicate<ItemStack> pred)
    {
        List<ItemStack> list = new ArrayList<>();
        getItems(list, le, pred);
        return list;
    }

    // 获取装备栏中的所有 ItemStack
    public static List<ItemStack> getEquippedItems(LivingEntity le, Predicate<ItemStack> pred)
    {
        List<ItemStack> list = new ArrayList<>();
        getEquippedItems(list, le, pred);
        return list;
    }

    // 获取饰品栏中的所有 ItemStack
    public static List<ItemStack> getTrinketItems(LivingEntity le, Predicate<ItemStack> pred)
    {
        List<ItemStack> list = new ArrayList<>();
        getTrinketItems(list, le, pred);
        return list;
    }

    // 获取装备栏与饰品栏中的所有 EntitySlotAccess
    public static List<EntitySlotAccess> getItemAccess(LivingEntity le)
    {
        List<EntitySlotAccess> list = new ArrayList<>();
        getItemAccess(list, le);
        return list;
    }

    // 获取装备栏中的所有 EntitySlotAccess
    public static List<EntitySlotAccess> getItemAccessEquipped(LivingEntity le)
    {
        List<EntitySlotAccess> list = new ArrayList<>();
        getItemAccessEquipped(list, le);
        return list;
    }

    // 获取饰品栏中的所有 EntitySlotAccess
    public static List<EntitySlotAccess> getItemAccessInTrinket(LivingEntity le)
    {
        List<EntitySlotAccess> list = new ArrayList<>();
        getItemAccessInTrinket(list, le);
        return list;
    }

    private static void getItems(List<ItemStack> list, LivingEntity le, Predicate<ItemStack> pred)
    {
        getEquippedItems(list, le, pred);
        getTrinketItems(list, le, pred);
    }

    private static void getEquippedItems(List<ItemStack> list, LivingEntity le, Predicate<ItemStack> pred)
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

    public static void getItemAccess(List<EntitySlotAccess> list, LivingEntity le)
    {
        getItemAccessEquipped(list, le);
        getItemAccessInTrinket(list, le);
    }

    public static void getItemAccessEquipped(List<EntitySlotAccess> list, LivingEntity le)
    {
        for (EquipmentSlot e : EquipmentSlot.values())
            list.add(new EquipmentSlotAccess(le, e));
    }

    public static void getItemAccessInTrinket(List<EntitySlotAccess> list, LivingEntity le)
    {
        var opt = TrinketsApi.getTrinketComponent(le);
        if (opt.isEmpty()) return;
        for (Map.Entry<String, Map<String, TrinketInventory>> entry : opt.get().getInventory().entrySet())
            for (Map.Entry<String, TrinketInventory> inventoryEntry : entry.getValue().entrySet())
                for (int i = 0; i < inventoryEntry.getValue().size(); ++i)
                    list.add(new TrinketSlotAccess(le, entry.getKey(), inventoryEntry.getKey(), i));
    }

    // 反序列化 EntitySlotAccess
    @Nullable
    public static EntitySlotAccess decode(String id, LivingEntity le)
    {
        try
        {
            var strings = id.split("/");
            if (strings[0].equals("equipment"))
                return new EquipmentSlotAccess(le, EquipmentSlot.byName(strings[1]));
            else if (strings[0].equals("trinket"))
                return new TrinketSlotAccess(le, strings[1], strings[2], Integer.parseInt(strings[3]));
        } catch (Exception ignored)
        {
        }
        return null;
    }

    // 该 EntitySlotAccess 是否存在可以增加饰品槽位属性的饰品
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
