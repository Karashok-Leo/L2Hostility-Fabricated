package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.LootModifierManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import karashokleo.l2hostility.compat.loot.ITraitLootRecipe;
import karashokleo.l2hostility.content.enchantment.HitTargetEnchantment;
import karashokleo.l2hostility.content.item.misc.wand.IMobClickItem;
import karashokleo.l2hostility.content.network.S2CLootData;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHNetworking;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.List;

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

        // 复制附魔书
//        public static void onAnvilCraft(AnvilUpdateEvent event) {
//            ItemStack copy = event.getLeft();
//            ItemStack book = event.getRight();
//            if (copy.getItem() instanceof BookCopy && book.getItem() instanceof EnchantedBookItem) {
//                var map = EnchantmentHelper.getEnchantments(book);
//                int cost = 0;
//                for (var e : map.entrySet()) {
//                    cost += BookCopy.cost(e.getKey(), e.getValue());
//                }
//                ItemStack result = book.copy();
//                result.setCount(book.getCount() + copy.getCount());
//                event.setOutput(result);
//                event.setMaterialCost(book.getCount());
//                event.setCost(cost);
//            }
//        }

        // 物品实体加入世界判断是否携带 VANISH
        EntityEvents.ON_JOIN_WORLD.register((entity, world, b) ->
                !(entity instanceof ItemEntity ie && EnchantmentHelper.getLevel(LHEnchantments.VANISH, ie.getStack()) > 0));

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) ->
        {
            List<IGlobalLootModifier> list = new ArrayList<>();
            for (var e : LootModifierManager.getLootModifierManager().getAllLootMods())
                if (e instanceof ITraitLootRecipe)
                    list.add(e);
            S2CLootData packet = new S2CLootData(list);
            LHNetworking.toClientPlayer(player, packet);
        });
    }
}
