package net.karashokleo.l2hostility.content.item.traits;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReprintHandler
{
    public static void reprint(ItemStack dst, ItemStack src)
    {
        if (!dst.hasEnchantments() && !dst.isEnchantable() || !src.hasEnchantments()) return;
        var selfEnch = EnchantmentHelper.get(dst);
        var targetEnch = EnchantmentHelper.get(src);
        Map<Enchantment, Integer> newEnch = new LinkedHashMap<>();
        for (var pair : targetEnch.entrySet())
        {
            Enchantment e = pair.getKey();
            if (!e.isAcceptableItem(dst)) continue;
            if (!allow(newEnch, e)) continue;
            int lv = pair.getValue();
            newEnch.compute(e, (k, v) -> v == null ? lv : Math.max(v, lv));
        }
        for (var pair : selfEnch.entrySet())
        {
            Enchantment e = pair.getKey();
            if (!e.isAcceptableItem(dst)) continue;
            if (!allow(newEnch, e)) continue;
            int lv = pair.getValue();
            newEnch.compute(e, (k, v) -> v == null ? lv : Math.max(v, lv));
        }
        EnchantmentHelper.set(newEnch, dst);
    }

    private static boolean allow(Map<Enchantment, Integer> map, Enchantment ench)
    {
        if (map.containsKey(ench)) return true;
        for (var e : map.keySet())
            if (!e.canCombine(ench))
                return false;
        return true;
    }
}
