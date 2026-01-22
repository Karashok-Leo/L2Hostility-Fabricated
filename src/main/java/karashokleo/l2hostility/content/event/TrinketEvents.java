package karashokleo.l2hostility.content.event;

import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.entity.living.LivingDropsCallback;
import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.misc.GreedOfNidhoggur;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.Registries;

public class TrinketEvents
{
    public static void register()
    {
        // DamageListenerTrinketItem
        DamageSourceCreateCallback.EVENT.register((type, source, attacker, position, damageSource) ->
        {
            if (attacker instanceof LivingEntity living)
            {
                for (var e : TrinketCompat.getItems(living, e -> e.getItem() instanceof DamageListenerTrinket))
                {
                    if (e.getItem() instanceof DamageListenerTrinket listener)
                    {
                        listener.onDamageSourceCreate(e, living, damageSource, type, source, position);
                    }
                }
            }
        });

        LivingAttackEvent.ATTACK.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onAttacked(e, target, event);
                }
            }
            if (!(event.getSource().getAttacker() instanceof LivingEntity attacker))
            {
                return;
            }
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onAttacking(e, attacker, event);
                }
            }
        });

        LivingHurtEvent.HURT.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onHurt(e, target, event);
                }
            }
            if (!(event.getSource().getAttacker() instanceof LivingEntity attacker))
            {
                return;
            }
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onHurting(e, attacker, event);
                }
            }
        });

        LivingDamageEvent.DAMAGE.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onDamaged(e, target, event);
                }
            }
        });

        ServerEntityCombatEvents.AFTER_KILLED_OTHER_ENTITY.register((serverWorld, entity, killed) ->
        {
            if (!(entity instanceof LivingEntity attacker))
            {
                return;
            }
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onKilled(e, attacker, killed);
                }
            }
        });

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) ->
        {
            for (var e : TrinketCompat.getItems(entity, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.onDeath(e, entity, source);
                }
            }
            if (!(source.getAttacker() instanceof LivingEntity attacker))
            {
                return;
            }
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener)
                {
                    listener.afterKilling(e, attacker, entity, source);
                }
            }
        });

        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) ->
        {
            for (var e : TrinketCompat.getItems(entity, e -> e.getItem() instanceof DamageListenerTrinket))
            {
                if (e.getItem() instanceof DamageListenerTrinket listener &&
                    !listener.allowDeath(e, entity, source, amount))
                {
                    return false;
                }
            }
            return true;
        });

        // 尼德霍格之欲掉落
        boolean registered = false;
        if (FabricLoader.getInstance().isModLoaded("puzzleslib"))
        {
            try
            {
                Class.forName("fuzs.puzzleslib.api.event.v1.entity.living.LivingDropsCallback");
                Class.forName("fuzs.puzzleslib.api.event.v1.core.EventResult");
                LivingDropsCallback.EVENT.register((entity, damageSource, drops, lootingLevel, recentlyHit) -> GreedOfNidhoggur.applyLootEffect(entity, damageSource, drops, lootingLevel, recentlyHit) ? EventResult.DENY : EventResult.PASS);
                registered = true;
            } catch (Exception ignored)
            {
            }
        }
        if (!registered)
        {
            LivingEntityEvents.DROPS.register(GreedOfNidhoggur::applyLootEffect);
        }

        // 暴怒之戒免疫
        MobEffectEvent.APPLICABLE.register(event ->
        {
            if (TrinketCompat.hasItemInTrinket(event.getEntity(), TrinketItems.CURSE_WRATH) &&
                Registries.STATUS_EFFECT.getEntry(event.getEffectInstance().getEffectType()).isIn(LHTags.WRATH_INVULNERABILITY))
            {
                event.setResult(BaseEvent.Result.DENY);
            }
        });
    }
}
