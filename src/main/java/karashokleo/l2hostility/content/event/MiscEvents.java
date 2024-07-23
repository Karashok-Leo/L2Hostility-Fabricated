package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.enchantment.HitTargetEnchantment;
import karashokleo.l2hostility.content.item.misc.wand.IMobClickItem;
import karashokleo.l2hostility.content.network.S2CLootData;
import karashokleo.l2hostility.content.network.S2CTraitConfigData;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

public class MiscEvents
{
    public static void register()
    {
        // 附魔攻击
        LivingHurtEvent.HURT.register(event ->
        {
            if (event.getSource().getAttacker() instanceof LivingEntity attacker)
                HitTargetEnchantment.handle(attacker, event);
        });

        // 跳过实体交互
        UseEntityCallback.EVENT.register(
                (player, world, hand, entity, hitResult) ->
                        player.getStackInHand(hand).getItem() instanceof IMobClickItem && entity instanceof LivingEntity &&
                                !entity.getWorld().isClient() ?
                                ActionResult.FAIL :
                                ActionResult.PASS
        );

        // 物品实体加入世界判断是否携带 VANISH
        EntityEvents.ON_JOIN_WORLD.register((entity, world, b) ->
                !(entity instanceof ItemEntity ie && EnchantmentHelper.getLevel(LHEnchantments.VANISH, ie.getStack()) > 0));

        // 发送 LootModifier
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> LHNetworking.toClientPlayer(sender, S2CLootData.create()));
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> LHNetworking.toClientPlayer(player, S2CLootData.create()));

        // 发送 TraitConfig
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> LHNetworking.toClientPlayer(sender, S2CTraitConfigData.create()));
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> LHNetworking.toClientPlayer(player, S2CTraitConfigData.create()));
    }
}
