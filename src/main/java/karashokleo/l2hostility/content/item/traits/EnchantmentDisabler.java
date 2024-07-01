package karashokleo.l2hostility.content.item.traits;

import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentDisabler
{
    private static final String ENCH = "Enchantments", ROOT = "l2hostility_enchantment", OLD = "originalEnchantments", TIME = "startTime";

    public static void disableEnchantment(World world, ItemStack stack, int duration)
    {
        NbtCompound root = stack.getOrCreateNbt();
        if (!root.contains(ENCH, NbtElement.LIST_TYPE)) return;
        double durability = stack.getMaxDamage() == 0 ? 0 : 1d * stack.getDamage() / stack.getMaxDamage();
        NbtCompound tag = stack.getOrCreateSubNbt(ROOT);
        var list = root.getList(ENCH, NbtElement.COMPOUND_TYPE);
        var cache = new NbtList();
        list.removeIf(e ->
        {
            if (noDispell(e)) return false;
            cache.add(e);
            return true;
        });
        tag.put(OLD, cache);
        tag.putLong(TIME, world.getTime() + duration);
        if (stack.isDamageable())
            stack.setDamage(MathHelper.clamp((int) Math.floor(durability * stack.getMaxDamage()), 0, stack.getMaxDamage() - 1));
    }

    private static boolean noDispell(NbtElement e)
    {
        if (!(e instanceof NbtCompound c)) return false;
        var id = new Identifier(c.getString("id"));
        var rg = Registries.ENCHANTMENT;
        return rg.getEntry(rg.get(id)).isIn(LHTags.NO_DISPELL);
    }

    public static void tickStack(World world, Entity user, ItemStack stack)
    {
        if (world.isClient()) return;
        if (user instanceof PlayerEntity player &&
                !player.getAbilities().creativeMode &&
                stack.hasEnchantments() &&
                EnchantmentHelper.getLevel(LHEnchantments.VANISH, stack) > 0)
        {
            stack.setCount(0);
            return;
        }
        if (stack.getNbt() == null || !stack.getNbt().contains(ROOT, NbtElement.COMPOUND_TYPE)) return;
        NbtCompound root = stack.getOrCreateNbt();
        NbtCompound tag = stack.getOrCreateSubNbt(ROOT);
        long time = tag.getLong(TIME);
        if (world.getTime() >= time)
        {
            stack.getNbt().remove(ROOT);
            var list = root.getList(ENCH, NbtElement.COMPOUND_TYPE);
            var cache = tag.getList(OLD, NbtElement.COMPOUND_TYPE);
            cache.addAll(list);
            stack.getNbt().put(ENCH, cache);
        }
    }

    public static void modifyTooltip(ItemStack stack, List<Text> tooltip, World level)
    {
        if (stack.getNbt() == null || !stack.getNbt().contains(ROOT, NbtElement.COMPOUND_TYPE)) return;
        NbtCompound tag = stack.getOrCreateSubNbt(ROOT);
        long time = Math.max(0, tag.getLong(TIME) - level.getTime());
        NbtList list = tag.getList(OLD, NbtElement.COMPOUND_TYPE);
        tooltip.add(LHTexts.TOOLTIP_DISABLE.get(
                        Text.literal(list.size() + "")
                                .formatted(Formatting.LIGHT_PURPLE),
                        Text.literal(time / 20 + "")
                                .formatted(Formatting.AQUA))
                .formatted(Formatting.RED));
        List<Text> disabled = new ArrayList<>();
        ItemStack.appendEnchantments(disabled, list);
        for (var e : disabled)
            tooltip.add(e.copy().formatted(Formatting.DARK_GRAY));
    }
}
