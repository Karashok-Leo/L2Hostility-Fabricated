package karashokleo.l2hostility.content.item.misc.wand;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TraitAdderWand extends BaseWand
{
    private static final String TRAIT = "l2hostility_trait";

    public TraitAdderWand(Settings settings)
    {
        super(settings);
    }

    public static ItemStack set(ItemStack ans, MobTrait trait)
    {
        ans.getOrCreateNbt().putString(TRAIT, trait.getIdStr());
        return ans;
    }

    public static MobTrait get(ItemStack stack)
    {
        if (stack.getOrCreateNbt().contains(TRAIT, NbtElement.STRING_TYPE))
        {
            String str = stack.getOrCreateNbt().getString(TRAIT);
            Identifier id = new Identifier(str);
            MobTrait ans = LHTraits.TRAIT.get(id);
            if (ans != null)
            {
                return ans;
            }
        }
        return LHTraits.TANK;
    }

    private static List<MobTrait> values()
    {
        return new ArrayList<>(LHTraits.TRAIT.stream().toList());
    }

    private static MobTrait next(MobTrait mod)
    {
        var list = values();
        int index = list.indexOf(mod);
        if (index + 1 >= list.size())
        {
            return list.get(0);
        }
        return list.get(index + 1);
    }

    private static MobTrait prev(MobTrait mod)
    {
        var list = values();
        int index = list.indexOf(mod);
        if (index == 0)
        {
            return list.get(list.size() - 1);
        }
        return list.get(index - 1);
    }

    @Nullable
    public static Integer decrease(MobTrait k, @Nullable Integer old)
    {
        if (old == null || old == 0)
        {
            return k.getMaxLevel();
        }
        if (old == 1)
        {
            return null;
        }
        return old - 1;
    }

    @Nullable
    public static Integer increase(MobTrait k, @Nullable Integer old)
    {
        if (old == null)
        {
            return 1;
        }
        if (old == k.getMaxLevel())
        {
            return null;
        }
        return old + 1;
    }

    @Override
    public void clickTarget(ItemStack stack, PlayerEntity player, LivingEntity entity)
    {
        var opt = MobDifficulty.get(entity);
        if (opt.isEmpty())
        {
            return;
        }
        var cap = opt.get();
        MobTrait trait = get(stack);
        Integer ans;
        if (player.isSneaking())
        {
            ans = cap.traits.compute(trait, TraitAdderWand::decrease);
        } else
        {
            ans = cap.traits.compute(trait, TraitAdderWand::increase);
        }
        int val = ans == null ? 0 : ans;
        trait.initialize(cap, entity, val);
        trait.postInit(cap, entity, val);
        cap.sync();
        entity.setHealth(entity.getMaxHealth());
        player.sendMessage(LHTexts.MSG_SET_TRAIT.get(trait.getName(), entity.getDisplayName(), val));
    }

    @Override
    public void clickNothing(ItemStack stack, PlayerEntity player)
    {
        MobTrait old = get(stack);
        MobTrait next = player.isSneaking() ? prev(old) : next(old);
        set(stack, next);
        player.sendMessage(LHTexts.MSG_SELECT_TRAIT.get(next.getName()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_WAND_ADDER.get().formatted(Formatting.GRAY));
        MobTrait trait = get(stack);
        tooltip.add(LHTexts.MSG_SELECT_TRAIT.get(trait.getName().formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
    }
}
