package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.effect.CleanseEffect;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.MathHelper;

public class EffectEvents
{
    public static void register()
    {
        // CLEANSE
        MobEffectEvent.APPLICABLE.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.hasStatusEffect(LHEffects.CLEANSE) &&
                !CleanseEffect.isInCleanseBlacklist(event.getEffectInstance(), entity))
                event.setResult(BaseEvent.Result.DENY);
        });

        MobEffectEvent.ADDED.register(event ->
        {
            LivingEntity entity = event.getEntity();
            if (entity.hasStatusEffect(LHEffects.CLEANSE) &&
                !CleanseEffect.isInCleanseBlacklist(event.getEffectInstance(), entity))
                GenericEvents.schedule(() -> CleanseEffect.clearOnEntity(entity));
        });

        MobEffectEvent.REMOVE.register(event ->
        {
            if (event.getEffect() == LHEffects.ANTI_BUILD)
                event.setCanceled(true);
        });

        // CURSE / LIFE MENDING
        LivingHealCallback.EVENT.register(event ->
        {
            LivingEntity entity = event.getEntity();

            // Curse
            StatusEffectInstance curseEffect = entity.getStatusEffect(LHEffects.CURSE);
            int curseLv = curseEffect == null ? 0 : curseEffect.getAmplifier() + 1;
            float factor = (float) MathHelper.clamp(
                    1 - curseLv * LHConfig.common().complements.properties.curseFactor,
                    0, 1
            );
            float amount = event.getAmount();
            amount *= factor;

            // Life Mending
            for (ItemStack stack : TrinketCompat.getItems(entity, e -> e.hasEnchantments() && e.isDamaged()))
            {
                int lv = EnchantmentHelper.getLevel(LHEnchantments.LIFE_MENDING, stack);
                if (lv > 0)
                {
                    int damage = stack.getDamage();
                    int repair = 1 << (lv - 1);
                    int armor = EnchantmentHelper.getLevel(LHEnchantments.DURABLE_ARMOR, stack);
                    if (armor > 0) repair *= 1 + armor;
                    int recover = Math.min(damage, (int) Math.floor(amount * repair));
                    stack.setDamage(damage - recover);
                    amount -= 1f * recover / repair;
                    if (amount < 1e-3) break;
                }
            }
            event.setAmount(amount);
            return true;
        });

        // Stone Cage disable Teleport
        MobEffectEvent.TELEPORT.register(event ->
        {
            if (event.getEntity() instanceof LivingEntity living &&
                living.hasStatusEffect(LHEffects.STONE_CAGE))
                event.setCanceled(true);
        });

        // 禁止放置方块
        UseBlockCallback.EVENT.register(
                (player, world, hand, hitResult) ->
                {
                    if (!player.getAbilities().creativeMode &&
                        player.hasStatusEffect(LHEffects.ANTI_BUILD))
                    {
                        ItemStack stack = player.getStackInHand(hand);
                        if (stack.getItem() instanceof BlockItem ||
                            stack.isIn(LHTags.ANTIBUILD_BAN))
                            return ActionResult.FAIL;
                    }
                    return ActionResult.PASS;
                }
        );

        // 禁止破坏方块
        PlayerBlockBreakEvents.BEFORE.register(
                (world, player, pos, state, blockEntity) ->
                        player.getAbilities().creativeMode ||
                        !player.hasStatusEffect(LHEffects.ANTI_BUILD)
        );
    }
}
