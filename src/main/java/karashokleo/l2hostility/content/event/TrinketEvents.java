package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.damage.api.event.DamageSourceCreateCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.DamageTypeTags;

public class TrinketEvents
{
    public static void register()
    {
        // DamageListenerTrinketItem
        DamageSourceCreateCallback.EVENT.register((type, source, attacker, position, damageSource) ->
        {
            if (attacker instanceof LivingEntity living)
                for (var e : TrinketCompat.getItems(living, e -> e.getItem() instanceof DamageListenerTrinketItem))
                    if (e.getItem() instanceof DamageListenerTrinketItem listener)
                        listener.onDamageSourceCreate(e, living, damageSource, type, source, position);
        });

        LivingAttackEvent.ATTACK.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof DamageListenerTrinketItem))
                if (e.getItem() instanceof DamageListenerTrinketItem listener)
                    listener.onAttacked(e, target, event);
            if (!(event.getSource().getAttacker() instanceof LivingEntity attacker)) return;
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof DamageListenerTrinketItem))
                if (e.getItem() instanceof DamageListenerTrinketItem listener)
                    listener.onAttacking(e, attacker, event);
        });

        LivingHurtEvent.HURT.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof DamageListenerTrinketItem))
                if (e.getItem() instanceof DamageListenerTrinketItem listener)
                    listener.onHurt(e, target, event);
            if (!(event.getSource().getAttacker() instanceof LivingEntity attacker)) return;
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof DamageListenerTrinketItem))
                if (e.getItem() instanceof DamageListenerTrinketItem listener)
                    listener.onHurting(e, attacker, event);
        });

        LivingDamageEvent.DAMAGE.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof DamageListenerTrinketItem))
                if (e.getItem() instanceof DamageListenerTrinketItem listener)
                    listener.onDamaged(e, target, event);
        });

        // 傲慢诅咒
        LivingHurtEvent.HURT.register(event ->
        {
            DamageSource source = event.getSource();
            if (event.getEntity() instanceof PlayerEntity player &&
                !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
                !source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
                TrinketCompat.hasItemInTrinket(player, TrinketItems.CURSE_PRIDE))
            {
                int level = PlayerDifficulty.get(player).getLevel().getLevel();
                double rate = LHConfig.common().items.curse.prideHealthBonus;
                double factor = 1 + rate * level;
                event.setAmount((float) (event.getAmount() / factor));
            }
        });

        // 色欲诅咒击杀必掉装备
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) ->
        {
            if (!(entity instanceof MobEntity mob)) return;
            var credit = mob.getPrimeAdversary();
            if (credit != null && TrinketCompat.hasItemInTrinket(credit, TrinketItems.CURSE_LUST))
                for (var e : EquipmentSlot.values())
                    mob.setEquipmentDropChance(e, 1);
        });

        // 尼德霍格之欲掉落
        LivingEntityEvents.DROPS.register((target, source, drops, lootingLevel, recentlyHit) ->
        {
            var op = MobDifficulty.get(target);
            if (op.isEmpty()) return false;
            MobDifficulty diff = op.get();
            if (diff.noDrop) return false;
            LivingEntity killer = target.getPrimeAdversary();
            if (killer != null && TrinketCompat.hasItemInTrinket(killer, TrinketItems.NIDHOGGUR))
            {
                double val = LHConfig.common().items.nidhoggurDropFactor * diff.getLevel();
                int count = (int) val;
                if (target.getRandom().nextDouble() < val - count) count++;
                count++;
                for (var stack : drops)
                    stack.getStack().setCount(stack.getStack().getCount() * count);
            }
            return false;
        });

        // 暴怒之戒免疫
        MobEffectEvent.APPLICABLE.register(event ->
        {
            if (TrinketCompat.hasItemInTrinket(event.getEntity(), TrinketItems.CURSE_WRATH) &&
                Registries.STATUS_EFFECT.getEntry(event.getEffectInstance().getEffectType()).isIn(LHTags.WRATH_INVULNERABILITY))
                event.setResult(BaseEvent.Result.DENY);
        });
    }
}
