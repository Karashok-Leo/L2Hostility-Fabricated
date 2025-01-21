package karashokleo.l2hostility.content.item.traits;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SealedItem extends TrinketItem
{
    public static final String TIME = "sealTime", DATA = "sealedItem";

    public static ItemStack sealItem(ItemStack stack, int time)
    {
        if (stack.isOf(MiscItems.SEAL))
        {
            stack.getOrCreateNbt().putInt(TIME, Math.max(stack.getOrCreateNbt().getInt(TIME), time));
            return stack;
        }
        ItemStack ans = MiscItems.SEAL.getDefaultStack();
        ans.getOrCreateNbt().putInt(TIME, time);
        ans.getOrCreateNbt().put(DATA, stack.writeNbt(new NbtCompound()));
//        if (EnchantmentHelper.getLevel(LHEnchantments.SOUL_BOUND, stack) > 0)
//            ans.addEnchantment(LHEnchantments.SOUL_BOUND, 1);
        if (EnchantmentHelper.getLevel(LHEnchantments.VANISH, stack) > 0)
            ans.addEnchantment(LHEnchantments.VANISH, 1);
        return ans;
    }

    public SealedItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (!stack.hasNbt() || stack.getSubNbt(SealedItem.DATA) == null) return false;
        var ctag = stack.getOrCreateSubNbt(SealedItem.DATA);
        ItemStack content = ItemStack.fromNbt(ctag);
        return content.getItem() instanceof Trinket trinket && trinket.canEquip(content, slot, entity);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        ItemStack stack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
    {
        return ItemStack.fromNbt(stack.getOrCreateNbt().getCompound(DATA));
    }

    @Override
    public int getMaxUseTime(ItemStack stack)
    {
        return stack.getOrCreateNbt().getInt(TIME);
    }

    @Override
    public UseAction getUseAction(ItemStack stack)
    {
        return UseAction.EAT;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.TOOLTIP_SEAL_DATA.get().formatted(Formatting.GRAY));
        tooltip.add(ItemStack.fromNbt(stack.getOrCreateNbt().getCompound(DATA)).getName());
        int time = stack.getOrCreateNbt().getInt(TIME);
        tooltip.add(LHTexts.TOOLTIP_SEAL_TIME.get(
                Text.literal(time / 20 + "").formatted(Formatting.AQUA)
        ).formatted(Formatting.RED));
    }
}
