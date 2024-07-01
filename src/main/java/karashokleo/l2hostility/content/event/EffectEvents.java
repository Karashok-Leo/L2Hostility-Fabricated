package karashokleo.l2hostility.content.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import karashokleo.l2hostility.content.effect.CleanseEffect;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.effect.api.event.EffectAdded;
import karashokleo.leobrary.effect.api.event.EffectApplicable;
import karashokleo.leobrary.effect.api.event.EffectRemove;
import karashokleo.leobrary.effect.api.event.LivingHeal;
import net.minecraft.util.ActionResult;

public class EffectEvents
{
    public static void register()
    {
        // CLEANSE
        EffectApplicable.EVENT.register((entity, effect, cir) ->
        {
            if (entity.hasStatusEffect(LHEffects.CLEANSE) && !CleanseEffect.isInCleanseBlacklist(effect, entity))
                cir.setReturnValue(false);
        });

        EffectAdded.EVENT.register((entity, newEffectInstance, oldEffectInstance, source, cir) ->
        {
            if (entity.hasStatusEffect(LHEffects.CLEANSE) && !CleanseEffect.isInCleanseBlacklist(newEffectInstance, entity))
                GenericEvents.schedule(() -> CleanseEffect.clearOnEntity(entity));
        });

        // CURSE
        LivingHeal.EVENT.register((entity, amount, ci) ->
        {
            if (entity.hasStatusEffect(LHEffects.CURSE))
                ci.cancel();
        });

        EffectRemove.EVENT.register((entity, effect, cir) ->
        {
            if (effect == LHEffects.ANTI_BUILD)
                cir.setReturnValue(false);
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
