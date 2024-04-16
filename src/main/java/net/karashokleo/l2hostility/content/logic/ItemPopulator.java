package net.karashokleo.l2hostility.content.logic;

import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.init.LHData;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.data.config.WeaponConfig;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.init.LHTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.Set;

public class ItemPopulator
{
    // 装备盔甲
    public static void populateArmors(LivingEntity le, int lv)
    {
        int rank = Math.min(4, lv / LHConfig.common().scaling.armorFactor - 1);
        if (rank < 0) return;
        for (EquipmentSlot slot : EquipmentSlot.values())
            if (slot.getType() == EquipmentSlot.Type.ARMOR)
                if (le.getEquippedStack(slot).isEmpty())
                {
                    Item item = MobEntity.getEquipmentForSlot(slot, rank);
                    if (item != null)
                        le.equipStack(slot, new ItemStack(item));
                }
    }

    // 装备武器
    public static void populateWeapons(LivingEntity le, MobDifficulty cap, Random r)
    {
        var manager = Registries.ITEM.streamTags();
        if (manager == null) return;
        if (le instanceof DrownedEntity && le.getMainHandStack().isEmpty())
        {
            double factor = cap.getLevel() / 16d / LHConfig.common().scaling.armorFactor;
            if (factor > le.getRandom().nextDouble())
                le.setStackInHand(Hand.MAIN_HAND, new ItemStack(Items.TRIDENT));
        }
        if (le.getType().isIn(LHTags.MELEE_WEAPON_TARGET))
        {
            if (le.getMainHandStack().isEmpty())
            {
                ItemStack stack = WeaponConfig.getRandomMeleeWeapon(cap.getLevel(), r);
                if (!stack.isEmpty())
                    le.equipStack(EquipmentSlot.MAINHAND, stack);
            }
        } else if (le.getType().isIn(LHTags.RANGED_WEAPON_TARGET))
        {
            ItemStack stack = WeaponConfig.getRandomRangedWeapon(cap.getLevel(), r);
            if (!stack.isEmpty())
                le.equipStack(EquipmentSlot.MAINHAND, stack);
        }
    }

    // 装备饰品（？
    public static void generateItems(MobDifficulty cap, LivingEntity le, EntityConfig.ItemPool pool)
    {
        // ---
//        if (cap.getLevel() < pool.level()) return;
//        if (le.getRandom().nextFloat() > pool.chance()) return;
//        var slot = CurioCompat.decode(pool.slot(), le);
//        if (slot == null) return;
//        var list = pool.entries();
//        int total = 0;
//        for (var e : list) total += e.weight();
//        if (total <= 0) return;
//        total = le.getRandom().nextInt(total);
//        for (var e : list) {
//            total -= e.weight();
//            if (total <= 0) {
//                slot.set(e.stack().copy());
//                return;
//            }
//        }
    }

    // 装备附魔
    public static void fillEnch(int level, Random source, ItemStack stack, EquipmentSlot slot)
    {
        var config = LHData.weapons;
        if (slot == EquipmentSlot.OFFHAND) return;
        var list = slot == EquipmentSlot.MAINHAND ?
                config.weapon_enchantments : config.armor_enchantments;
        var map = EnchantmentHelper.get(stack);
        for (var e : list)
        {
            int elv = e.level() <= 0 ? 1 : e.level();
            if (elv > level) continue;
            for (var ench : e.enchantments())
            {
                if (e.chance() < source.nextDouble()) continue;
                if (!ench.isAvailableForRandomSelection()) continue;
                if (!isValid(map.keySet(), ench)) continue;
                int max = Math.min(level / elv, ench.getMaxLevel());
                map.put(ench, Math.max(max, map.getOrDefault(ench, 0)));
            }
        }
        EnchantmentHelper.set(map, stack);
    }

    // 允许附魔
    private static boolean isValid(Set<Enchantment> old, Enchantment ench)
    {
        for (var other : old)
            if (ench == other)
                return true;
        for (var other : old)
            if (!ench.canCombine(other))
                return false;
        return true;
    }

    // 装备物品（包括盔甲、武器、附魔、饰品）
    public static void postFill(MobDifficulty cap, LivingEntity le)
    {
        // add weapon
        Random r = le.getRandom();
        populateWeapons(le, cap, r);
        // enchant
        for (var e : EquipmentSlot.values())
        {
            ItemStack stack = le.getEquippedStack(e);
            if (!stack.isEnchantable()) continue;
            if (!stack.hasEnchantments())
            {
                float lvl = MathHelper.clamp(cap.getLevel() * 0.02f, 0, 1) *
                        r.nextInt(30) +
                        cap.getEnchantBonus();
                stack = EnchantmentHelper.enchant(r, stack, (int) lvl, false);
            }
            if (LHConfig.common().scaling.allowExtraEnchantments)
                fillEnch(cap.getLevel(), le.getRandom(), stack, e);
            le.equipStack(e, stack);
        }
        var config = LHData.entities.get(le.getType());
        if (config != null && !config.items.isEmpty())
            for (var pool : config.items)
                generateItems(cap, le, pool);
    }
}
