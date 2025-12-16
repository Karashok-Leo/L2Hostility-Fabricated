package karashokleo.l2hostility.content.item.consumable;

import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BookEverything extends Item
{
    public BookEverything(Settings settings)
    {
        super(settings);
    }

    @Nullable
    private static Enchantment getEnch(ItemStack stack)
    {
        String name = stack.getName().getString();
        try
        {
            Identifier id = new Identifier(name.trim());
            if (!Registries.ENCHANTMENT.containsId(id))
            {
                return null;
            }
            return Registries.ENCHANTMENT.get(id);
        } catch (Exception e)
        {
            return null;
        }
    }

    public static boolean allow(Enchantment ench)
    {
        if (ench.isTreasure() ||
            !ench.isAvailableForEnchantedBookOffer() ||
            !ench.isAvailableForRandomSelection())
        {
            return false;
        }
        return ench.getMaxPower(ench.getMaxLevel()) >= ench.getMinPower(ench.getMaxLevel());
    }

    public static int cost(Enchantment ench)
    {
        return Math.max(ench.getMaxLevel(), ench.getMaxPower(ench.getMaxLevel()) / 10);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking())
        {
            if (!world.isClient())
            {
                ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
                NbtList listtag = EnchantedBookItem.getEnchantmentNbt(result);
                for (var e : Registries.ENCHANTMENT.getEntrySet())
                {
                    if (allow(e.getValue()))
                    {
                        listtag.add(EnchantmentHelper.createNbt(e.getKey().getValue(), e.getValue().getMaxLevel()));
                    }
                }
                result.getOrCreateNbt().put("StoredEnchantments", listtag);
                stack.decrement(1);
                if (stack.isEmpty())
                {
                    return TypedActionResult.success(result);
                } else
                {
                    user.getInventory().offerOrDrop(result);
                }
            }
            return TypedActionResult.consume(stack);
        } else
        {
            Enchantment ench = getEnch(stack);
            if (ench == null)
            {
                return TypedActionResult.fail(stack);
            }
            if (!allow(ench))
            {
                return TypedActionResult.fail(stack);
            }
            int cost = cost(ench);
            if (user.experienceLevel < cost)
            {
                return TypedActionResult.fail(stack);
            }
            if (!world.isClient())
            {
                user.addExperienceLevels(-cost);
                EnchantmentLevelEntry ins = new EnchantmentLevelEntry(ench, ench.getMaxLevel());
                ItemStack result = EnchantedBookItem.forEnchantment(ins);
                user.getInventory().offerOrDrop(result);
            }
            return TypedActionResult.success(stack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_BOOK_EVERYTHING_USE.get().formatted(Formatting.GRAY));
        tooltip.add(LHTexts.ITEM_BOOK_EVERYTHING_SHIFT.get().formatted(Formatting.GOLD));
        Enchantment e = getEnch(stack);
        if (e == null)
        {
            tooltip.add(LHTexts.ITEM_BOOK_EVERYTHING_INVALID.get().formatted(Formatting.GRAY));
        } else
        {
            if (allow(e))
            {
                tooltip.add(LHTexts.ITEM_BOOK_EVERYTHING_READY.get(e.getName(e.getMaxLevel()), cost(e)).formatted(Formatting.GREEN));
            } else
            {
                tooltip.add(LHTexts.ITEM_BOOK_EVERYTHING_FORBIDDEN.get(e.getName(e.getMaxLevel())).formatted(Formatting.RED));
            }
        }
    }
}
