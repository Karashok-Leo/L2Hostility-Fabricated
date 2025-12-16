package karashokleo.l2hostility.content.enchantment.special;

import karashokleo.l2hostility.init.LHEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public final class ItemSafeGuard
{
    private static final String KEY_ROOT = "SafeGuard";
    private static final String KEY_PREVIOUS_DAMAGE = "PreviousDamage";
    private static final String KEY_TICK = "Tick";

    public static boolean shouldPreventBreak(ItemStack stack, int damageToSet)
    {
        if (damageToSet < stack.getMaxDamage())
        {
            return false;
        }
        if (EnchantmentHelper.getLevel(LHEnchantments.SAFEGUARD, stack) < 1)
        {
            return false;
        }
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(KEY_ROOT))
        {
            NbtCompound safeguardMark = new NbtCompound();
            safeguardMark.putInt(KEY_PREVIOUS_DAMAGE, stack.getDamage());
            safeguardMark.putInt(KEY_TICK, 0);
            nbt.put(KEY_ROOT, safeguardMark);
        }
        return true;
    }

    public static void inventoryTick(ItemStack stack)
    {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(KEY_ROOT))
        {
            return;
        }
        NbtList enchantments = stack.getEnchantments();

        // remove ticks if no safeguard enchantment
        if (enchantments.isEmpty() ||
            enchantments.stream().noneMatch(ItemSafeGuard::isSafeguard))
        {
            nbt.remove(KEY_ROOT);
            return;
        }

        NbtCompound safeguardMark = nbt.getCompound(KEY_ROOT);
        int tick = safeguardMark.getInt(KEY_TICK);

        // try to remove safeguard if tick to limit
        if (tick >= 40)
        {
            if (stack.getDamage() == 0 ||
                stack.getDamage() < safeguardMark.getInt(KEY_PREVIOUS_DAMAGE))
            {
                nbt.remove(KEY_ROOT);
            } else
            {
                enchantments.removeIf(ItemSafeGuard::isSafeguard);
            }
        }

        // increase tick
        else
        {
            safeguardMark.putInt(KEY_TICK, tick + 1);
        }
    }

    private static boolean isSafeguard(NbtElement e)
    {
        if (!(e instanceof NbtCompound c))
        {
            return false;
        }
        var id = new Identifier(c.getString("id"));
        var rg = Registries.ENCHANTMENT;
        return rg.get(id) == LHEnchantments.SAFEGUARD;
    }
}
