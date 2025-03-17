package karashokleo.l2hostility.content.item.traits;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.misc.wand.TraitAdderWand;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.content.trait.legendary.LegendaryTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TraitSymbol extends Item
{
    public TraitSymbol(Settings settings)
    {
        super(settings);
    }

    private static boolean allow(PlayerEntity player, MobTrait trait, LivingEntity target)
    {
        if (!LHConfig.common().scaling.allowPlayerAllies && target.isTeammate(player))
            return false;
        if (!LHConfig.common().scaling.allowTraitOnOwnable && target instanceof Ownable own &&
            own.getOwner() instanceof PlayerEntity)
            return false;
        return trait.allow(target);
    }

    public MobTrait get()
    {
        var ans = LHTraits.TRAIT.get(Registries.ITEM.getId(this));
        if (ans == null)
        {
            L2Hostility.LOGGER.error("Trait {} is not registered. Why?", Registries.ITEM.getId(this));
            var set = LHTraits.TRAIT.getIds();
            L2Hostility.LOGGER.error("List of all ids registered: ");
            for (var e : set)
                L2Hostility.LOGGER.error(e.toString());
        }
        assert ans != null;
        return ans;
    }

    @Override
    protected String getOrCreateTranslationKey()
    {
        return this.get().getNameKey();
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand)
    {
        var opt = MobDifficulty.get(entity);
        if (opt.isPresent())
        {
            MobDifficulty cap = opt.get();
            MobTrait trait = get();
            if (!allow(user, trait, entity))
            {
                if (user instanceof ServerPlayerEntity sp)
                    sp.sendMessage(LHTexts.MSG_ERR_DISALLOW.get().formatted(Formatting.RED), true);
                return ActionResult.FAIL;
            }

            Integer ans;
            if (user.getAbilities().creativeMode &&
                user.isSneaking())
            {
                if (cap.getTraitLevel(trait) <= 0)
                    return ActionResult.FAIL;
                if (user.getWorld().isClient())
                    return ActionResult.SUCCESS;
                ans = cap.traits.compute(trait, TraitAdderWand::decrease);
            } else
            {
                if (cap.getTraitLevel(trait) >= trait.getMaxLevel())
                {
                    if (user instanceof ServerPlayerEntity sp)
                        sp.sendMessage(LHTexts.MSG_ERR_MAX.get().formatted(Formatting.RED), true);
                    return ActionResult.FAIL;
                }
                if (user.getWorld().isClient())
                    return ActionResult.SUCCESS;
                ans = cap.traits.compute(trait, TraitAdderWand::increase);
            }
            int val = ans == null ? 0 : ans;
            trait.initialize(cap, entity, val);
            trait.postInit(cap, entity, val);
            cap.sync();
            entity.setHealth(entity.getMaxHealth());
            if (user instanceof ServerPlayerEntity sp)
            {
                sp.sendMessage(LHTexts.MSG_SET_TRAIT.get(trait.getName(), entity.getDisplayName(), val), true);
                Criteria.CONSUME_ITEM.trigger(sp, stack);
            }
            if (!user.getAbilities().creativeMode)
                stack.decrement(1);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(get().getFullName(null));
        if (get().isBanned())
        {
            tooltip.add(LHTexts.TOOLTIP_BANNED.get().formatted(Formatting.RED));
            return;
        }
        if (get() instanceof LegendaryTrait)
            tooltip.add(LHTexts.TOOLTIP_LEGENDARY.get().formatted(Formatting.GOLD));

        if (Screen.hasShiftDown())
        {
            var config = get().getConfig();
            tooltip.add(LHTexts.TOOLTIP_MIN_LEVEL.get(Text.literal(config.min_level + "").formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
            tooltip.add(LHTexts.TOOLTIP_LEVEL_COST.get(Text.literal(config.cost + "").formatted(Formatting.AQUA)).formatted(Formatting.GRAY));
            tooltip.add(LHTexts.TOOLTIP_WEIGHT.get(Text.literal(config.weight + "").formatted(Formatting.DARK_AQUA)).formatted(Formatting.DARK_GRAY));
        } else
        {
            get().addDetail(tooltip);
            // \n
            tooltip.add(Text.empty());
            tooltip.add(LHTexts.SHIFT.get().formatted(Formatting.GRAY));
        }
    }
}
