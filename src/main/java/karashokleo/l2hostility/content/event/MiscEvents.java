package karashokleo.l2hostility.content.event;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.enchantment.core.AttributeEnchantment;
import karashokleo.l2hostility.content.enchantment.core.HitTargetEnchantment;
import karashokleo.l2hostility.content.feature.EntityFeature;
import karashokleo.l2hostility.content.item.misc.wand.IMobClickItem;
import karashokleo.l2hostility.content.network.S2CConfigData;
import karashokleo.l2hostility.content.network.S2CLootData;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.l2hostility.init.LHTags;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class MiscEvents
{
    public static void register()
    {
        // 属性附魔
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) ->
        {
            if (!stack.hasEnchantments()) return;
            EnchantmentHelper.get(stack).forEach((enchantment, level) ->
            {
                if (enchantment instanceof AttributeEnchantment ins)
                    ins.addAttributes(level, stack, slot, attributeModifiers);
            });
        });

        // 免疫附魔
        LivingAttackEvent.ATTACK.register(event ->
        {
            if (!LHConfig.common().complements.enableImmunityEnchantments)
                return;

            if (EntityFeature.OWNER_PROTECTION.test(event.getEntity()))
            {
                if (event.getSource().getAttacker() instanceof Ownable ownable && ownable.getOwner() == event.getEntity())
                    event.setCanceled(true);
            }
            if (EntityFeature.INVINCIBLE.test(event.getEntity()))
                event.setCanceled(true);

            if (event.getSource().isIn(DamageTypeTags.BYPASSES_EFFECTS) ||
                event.getSource().isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
                return;

            if (EntityFeature.ENVIRONMENTAL_REJECT.test(event.getEntity()) &&
                event.getSource().getAttacker() == null)
                event.setCanceled(true);

            if (EntityFeature.MAGIC_REJECT.test(event.getEntity()) &&
                event.getSource().isIn(LHTags.MAGIC))
                event.setCanceled(true);

            if (event.getSource().isIn(DamageTypeTags.BYPASSES_ENCHANTMENTS))
                return;

            if (EntityFeature.PROJECTILE_REJECT.test(event.getEntity()) &&
                event.getSource().isIn(DamageTypeTags.IS_PROJECTILE))
                event.setCanceled(true);

            if (EntityFeature.FIRE_REJECT.test(event.getEntity()) &&
                event.getSource().isIn(DamageTypeTags.IS_FIRE))
                event.setCanceled(true);

            if (EntityFeature.EXPLOSION_REJECT.test(event.getEntity()) &&
                event.getSource().isIn(DamageTypeTags.IS_EXPLOSION))
                event.setCanceled(true);
        });
        LivingEntityEvents.LivingTickEvent.TICK.register(event ->
        {
            if (event.getEntity().isOnFire() &&
                EntityFeature.FIRE_REJECT.test(event.getEntity()))
                event.getEntity().extinguish();
        });

        // 虚空之触
        Identifier voidTouch = L2Hostility.id("void_touch_handler");
        LivingAttackEvent.ATTACK.addPhaseOrdering(voidTouch, Event.DEFAULT_PHASE);
        LivingAttackEvent.ATTACK.register(voidTouch, event ->
        {
            DamageSource source = event.getSource();
            if (source.getAttacker() instanceof LivingEntity living &&
                LHEnchantments.VOID_TOUCH.allow(living, source))
            {
                source.setBypassArmor();
                source.setBypassMagic();
                source.setBypassEffects();
                source.setBypassResistance();
                source.setBypassEnchantments();
                source.setBypassShield();
                source.setBypassCooldown();
                source.setBypassInvulnerability();
            }
        });

        // 命中目标附魔
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
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> LHNetworking.toClientPlayer(sender, S2CConfigData.create()));
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> LHNetworking.toClientPlayer(player, S2CConfigData.create()));
    }

    public static boolean canSee(Entity instance, Operation<Boolean> original)
    {
        boolean ans = original.call(instance);
        if (ans) return true;
        if (instance instanceof LivingEntity le)
        {
            if (le.isInLava())
            {
                if (EntityFeature.FIRE_REJECT.test(le) ||
                    EntityFeature.ENVIRONMENTAL_REJECT.test(le) /*||
                    EntityFeature.LAVA_WALKER.test(le)*/)
                {
                    return true;
                }
            }
            if (le.inPowderSnow)
            {
                if (PowderSnowBlock.canWalkOnPowderSnow(instance) ||
                    EntityFeature.ENVIRONMENTAL_REJECT.test(le))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
