package net.karashokleo.l2hostility.data.config;

import dev.xkmc.l2serial.serialization.SerialClass;
import net.karashokleo.l2hostility.init.LHData;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SerialClass
public class WeaponConfig
{
    public static ItemStack getRandomMeleeWeapon(int level, Random r)
    {
        WeaponConfig config = LHData.weapons;
        return getRandomWeapon(config.melee_weapons, level, r);
    }

    public static ItemStack getRandomRangedWeapon(int level, Random r)
    {
        WeaponConfig config = LHData.weapons;
        return getRandomWeapon(config.ranged_weapons, level, r);
    }

    private static ItemStack getRandomWeapon(ArrayList<ItemConfig> entries, int level, Random r)
    {
        int total = 0;
        List<ItemConfig> list = new ArrayList<>();
        for (var e : entries)
        {
            if (e.level <= level)
            {
                list.add(e);
                total += e.weight();
            }
        }
        if (total == 0)
            return ItemStack.EMPTY;
        int val = r.nextInt(total);
        for (var e : list)
        {
            val -= e.weight();
            if (val <= 0)
            {
                return e.stack.get(r.nextInt(e.stack.size())).copy();
            }
        }
        return ItemStack.EMPTY;
    }

    @SerialClass.SerialField
    public final ArrayList<ItemConfig> melee_weapons = new ArrayList<>();
    @SerialClass.SerialField
    public final ArrayList<ItemConfig> ranged_weapons = new ArrayList<>();
    @SerialClass.SerialField
    public final ArrayList<EnchConfig> weapon_enchantments = new ArrayList<>();
    @SerialClass.SerialField
    public final ArrayList<EnchConfig> armor_enchantments = new ArrayList<>();

    public void merge(WeaponConfig config)
    {
        melee_weapons.addAll(config.melee_weapons);
        ranged_weapons.addAll(config.ranged_weapons);
        weapon_enchantments.addAll(config.weapon_enchantments);
        armor_enchantments.addAll(config.armor_enchantments);
    }

    public WeaponConfig putMeleeWeapon(int level, int weight, Item... items)
    {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (var e : items)
        {
            list.add(e.getDefaultStack());
        }
        melee_weapons.add(new ItemConfig(list, level, weight));
        return this;
    }

    public WeaponConfig putRangedWeapon(int level, int weight, Item... items)
    {
        ArrayList<ItemStack> list = new ArrayList<>();
        for (var e : items)
        {
            list.add(e.getDefaultStack());
        }
        ranged_weapons.add(new ItemConfig(list, level, weight));
        return this;
    }

    public WeaponConfig putWeaponEnch(int level, float chance, Enchantment... items)
    {
        ArrayList<Enchantment> list = new ArrayList<>(Arrays.asList(items));
        weapon_enchantments.add(new EnchConfig(list, level, chance));
        return this;
    }


    public WeaponConfig putArmorEnch(int level, float chance, Enchantment... items)
    {
        ArrayList<Enchantment> list = new ArrayList<>(Arrays.asList(items));
        armor_enchantments.add(new EnchConfig(list, level, chance));
        return this;
    }

    public record ItemConfig(ArrayList<ItemStack> stack, int level, int weight)
    {
    }

    public record EnchConfig(ArrayList<Enchantment> enchantments, int level, float chance)
    {
    }
}
