package karashokleo.l2hostility.content.item.trinket.misc;

import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.compat.trinket.slot.EntitySlotAccess;
import karashokleo.l2hostility.content.item.traits.SealedItem;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PocketOfRestoration extends SingleEpicTrinketItem
{
    public static final String ROOT = "UnsealRoot", KEY = "SealedSlotKey", START = "UnsealStartTime";

    public PocketOfRestoration(Settings settings, int durability)
    {
        super(settings, durability);
    }

    public static void setData(ItemStack stack, ItemStack sealed, String id, long time)
    {
        var data = sealed.getOrCreateNbt().get(SealedItem.DATA);
        if (data == null) return;
        var tag = stack.getOrCreateSubNbt(ROOT);
        tag.putInt(SealedItem.TIME, sealed.getOrCreateNbt().getInt(SealedItem.TIME));
        tag.put(SealedItem.DATA, data);
        tag.putString(KEY, id);
        tag.putLong(START, time);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        if (entity.getWorld().isClient()) return;
        if (!entity.isAlive()) return;
        var list = TrinketCompat.getItemAccess(entity);
        if (stack.getNbt() != null && stack.getNbt().contains(ROOT))
        {
            var tag = stack.getOrCreateSubNbt(ROOT);
            long time = tag.getLong(START);
            int dur = tag.getInt(SealedItem.TIME);
            if (entity.getWorld().getTime() >= time + dur)
            {
                ItemStack result = ItemStack.fromNbt(tag.getCompound(SealedItem.DATA));
                EntitySlotAccess access = TrinketCompat.decode(tag.getString(KEY), entity);
                if (access != null && access.get().isEmpty())
                {
                    access.set(result);
                    stack.getNbt().remove(ROOT);
                } else if (entity instanceof PlayerEntity player &&
                           player.giveItemStack(result))
                {
                    stack.getNbt().remove(ROOT);
                }
            }
            return;
        }
        if (stack.getDamage() + 1 >= stack.getMaxDamage())
            return;
        for (var e : list)
            if (e.get().getItem() instanceof SealedItem)
            {
                ItemStack sealed = e.get();
                e.set(ItemStack.EMPTY);
                String id = e.getID();
                long time = entity.getWorld().getTime();
                stack.damage(1, entity, x ->
                {
                });
                setData(stack, sealed, id, time);
                return;
            }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.POCKET_OF_RESTORATION.get().formatted(Formatting.GOLD));
        if (stack.getNbt() != null && stack.getNbt().contains(ROOT))
        {
            tooltip.add(LHTexts.TOOLTIP_SEAL_DATA.get().formatted(Formatting.GRAY));
            tooltip.add(ItemStack.fromNbt(stack.getOrCreateSubNbt(ROOT).getCompound(SealedItem.DATA)).getName());
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
