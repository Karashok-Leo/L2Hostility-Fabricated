package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHDamageTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;

public class TrinketEvents
{
    public static void register()
    {
        // 诅咒
        LivingAttackEvent.ATTACK.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof CurseTrinketItem))
                if (e.getItem() instanceof CurseTrinketItem curse)
                    curse.onAttacked(e, target, event);
        });

        LivingHurtEvent.HURT.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof CurseTrinketItem))
                if (e.getItem() instanceof CurseTrinketItem curse)
                    curse.onHurt(e, target, event);
            if (!(event.getSource().getAttacker() instanceof LivingEntity attacker)) return;
            for (var e : TrinketCompat.getItems(attacker, e -> e.getItem() instanceof CurseTrinketItem))
                if (e.getItem() instanceof CurseTrinketItem curse)
                    curse.onHurting(e, attacker, event);
        });

        LivingDamageEvent.DAMAGE.register(event ->
        {
            LivingEntity target = event.getEntity();
            for (var e : TrinketCompat.getItems(target, e -> e.getItem() instanceof CurseTrinketItem))
                if (e.getItem() instanceof CurseTrinketItem curse)
                    curse.onDamaged(e, target, event);
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

        // 色欲诅咒
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) ->
        {
            if (!(entity instanceof MobEntity mob)) return;
            var credit = mob.getPrimeAdversary();
            if (credit != null && TrinketCompat.hasItemInTrinket(credit, TrinketItems.CURSE_LUST))
                for (var e : EquipmentSlot.values())
                    mob.setEquipmentDropChance(e, 1);
        });

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

        LivingAttackEvent.ATTACK.register(event ->
        {
            DamageSource source = event.getSource();
            if (TrinketCompat.hasItemInTrinket(event.getEntity(), TrinketItems.RING_DIVINITY) &&
                    !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
                    !source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
                    source.isIn(LHDamageTypes.MAGIC))
                event.setCanceled(true);
        });
    }

//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public static void onPotionTest(MobEffectEvent.Applicable event)
//    {
//        LivingEntity entity = event.getEntity();
//        if (CurioCompat.hasItemInCurio(entity, LHItems.CURSE_WRATH.get()))
//        {
//            var config = ArmorEffectConfig.get().getImmunity(LHItems.CURSE_WRATH.getId().toString());
//            if (config.contains(event.getEffectInstance().getEffect()))
//                event.setResult(Event.Result.DENY);
//        }
//    }
//
    //    @Override
//    public void onCreateSource(CreateSourceEvent event) {
//        LivingEntity mob = event.getAttacker();
//        if (MobTraitCap.HOLDER.isProper(mob)) {
//            MobTraitCap.HOLDER.get(mob).traitEvent((k, v) -> k.onCreateSource(v, event.getAttacker(), event));
//        }
//        var type = event.getResult();
//        if (type == null) return;
//        var root = type.toRoot();
//        if (root == L2DamageTypes.MOB_ATTACK || root == L2DamageTypes.PLAYER_ATTACK) {
//            if (CurioCompat.hasItemInCurioOrSlot(mob, LHItems.IMAGINE_BREAKER.get())) {
//                event.enable(DefaultDamageState.BYPASS_MAGIC);
//            }
//            if (CurioCompat.hasItemInCurio(mob, LHItems.PLATINUM_STAR.get())) {
//                event.enable(HostilityDamageState.BYPASS_COOLDOWN);
//            }
//        }
//    }
}
