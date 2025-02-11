package karashokleo.l2hostility.content.item.misc.tool;

import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.trait.base.TargetEffectTrait;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.effect.api.util.StatusEffectInstanceBuilder;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WitchWand extends Item
{
    public WitchWand(Settings settings)
    {
        super(settings);
    }

    private static StatusEffectInstance getRandom(int maxRank, Random random)
    {
        var list = LHTraits.TRAIT.stream().filter(e -> e instanceof TargetEffectTrait).toList();
        TargetEffectTrait trait = (TargetEffectTrait) list.get(random.nextInt(list.size()));
        int rank = Math.min(maxRank, trait.getConfig().max_rank);
        var ans = trait.func.apply(rank);
        return new StatusEffectInstanceBuilder(ans).setDuration(ans.getDuration() * LHConfig.common().items.witchWandFactor).build();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        world.playSound(user, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_LINGERING_POTION_THROW, SoundCategory.NEUTRAL,
                0.5F, 0.4F / (user.getRandom().nextFloat() * 0.4F + 0.8F));
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient())
        {
            PotionEntity entity = new PotionEntity(world, user);
            ItemStack potion = new ItemStack(Items.SPLASH_POTION);
            int maxRank = PlayerDifficulty.get(user).maxRankKilled;
            StatusEffectInstance ins = getRandom(maxRank, user.getRandom());
            PotionUtil.setCustomPotionEffects(potion, List.of(ins));
            potion.getOrCreateNbt().putInt("CustomPotionColor", ins.getEffectType().getColor());
            entity.setItem(potion);
            entity.setVelocity(user, user.getPitch(), user.getYaw(), -20.0F, 0.5F, 1.0F);
            world.spawnEntity(entity);
            if (!user.getAbilities().creativeMode)
                stack.damage(1, user, e -> e.sendToolBreakStatus(hand));
        }
        user.getItemCooldownManager().set(this, 60);
        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_WITCH_WAND.get().formatted(Formatting.GOLD));
    }
}
