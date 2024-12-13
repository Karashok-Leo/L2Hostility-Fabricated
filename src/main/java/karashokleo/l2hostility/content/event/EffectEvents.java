package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.effect.CleanseEffect;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

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
            if (entity.hasStatusEffect(LHEffects.CURSE))
                return false;
            float amount = event.getAmount();
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

        // 禁止放置方块
        UseBlockCallback.EVENT.register(
                (player, world, hand, hitResult) ->
                        player.getAbilities().creativeMode ||
                        !player.hasStatusEffect(LHEffects.ANTI_BUILD) ||
                        player.getStackInHand(hand).isIn(LHTags.ANTIBUILD_BAN) ?
                                ActionResult.PASS :
                                ActionResult.FAIL
        );

        // 禁止破坏方块
        PlayerBlockBreakEvents.BEFORE.register(
                (world, player, pos, state, blockEntity) ->
                        player.getAbilities().creativeMode ||
                        !player.hasStatusEffect(LHEffects.ANTI_BUILD)
        );
    }
}
