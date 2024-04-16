package net.karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.content.trait.legendary.UndyingTrait;
import net.karashokleo.l2hostility.init.LHDamageTypes;
import net.karashokleo.l2hostility.init.LHItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

public class MobEvents
{
    private static final LivingAttackEvent.LivingAttackCallback onAttacked = event ->
    {
        DamageSource source = event.getSource();
        if (LHDamageTypes.isMagic(source) && !LHDamageTypes.bypasses(source))
            if (TrinketCompat.hasItemInTrinket(event.getEntity(), LHItems.RING_DIVINITY))
            {
                event.setCanceled(true);
                return;
            }
        LivingEntity entity = event.getEntity();
        var diff = MobDifficulty.get(entity);
        if (diff.isEmpty()) return;
        diff.get().traitEvent((k, v) -> k.onAttacked(v, entity, event));
    };
    private static final LivingHurtEvent.HurtCallback onHurting = event ->
    {
        Entity attacker = event.getSource().getAttacker();
        var op = MobDifficulty.get(attacker);
        if (op.isEmpty()) return;
        op.get().traitEvent((k, v) -> k.onHurting(v, op.get().owner, event));
    };
    private static final LivingHurtEvent.HurtCallback onHurt = event ->
    {
        if (!(event.getEntity() instanceof MobEntity mob)) return;
        var diff = MobDifficulty.get(mob);
        if (diff.isPresent())
            diff.get().traitEvent((k, v) -> k.onHurt(v, event.getEntity(), event));
        else if (event.getEntity() instanceof PlayerEntity player &&
                !LHDamageTypes.bypasses(event.getSource()) &&
                TrinketCompat.hasItemInTrinket(player, LHItems.CURSE_PRIDE))
        {
            int level = PlayerDifficulty.get(player).getLevel().getLevel();
            double rate = LHConfig.common().items.curse.prideHealthBonus;
            double factor = 1 + rate * level;
            event.setAmount((float) (event.getAmount() / factor));
        }
    };
    private static final LivingDamageEvent.DamageCallback onDamaged = event ->
    {
        for (var e : TrinketCompat.getItems(event.getEntity(), e -> e.getItem() instanceof CurseTrinketItem))
            if (e.getItem() instanceof CurseTrinketItem curse)
                curse.onDamaged(e, event.getEntity(), event);
    };
    private static final ServerLivingEntityEvents.AfterDeath onDeath = (entity, source) ->
    {
        if (!(entity instanceof MobEntity mob)) return;
        var credit = mob.getPrimeAdversary();
        if (credit != null && TrinketCompat.hasItemInTrinket(credit, LHItems.CURSE_LUST))
            for (var e : EquipmentSlot.values())
                mob.setEquipmentDropChance(e, 1);
        var diff = MobDifficulty.get(mob);
        if (diff.isEmpty()) return;
        diff.get().traitEvent((k, v) -> k.onDeath(v, entity, source));
    };

    public static void register()
    {
        /**
         * LivingAttackEvent is fired when a living Entity is attacked. <br>
         * This event is fired whenever an Entity is attacked in
         * {@link LivingEntity#hurt(DamageSource, float)} and
         * {@link Player#hurt(DamageSource, float)}. <br>
         * <br>
         * {@link #source} contains the DamageSource of the attack. <br>
         * {@link #amount} contains the amount of damage dealt to the entity. <br>
         * <br>
         * This event is cancelable.<br>
         * If this event is canceled, the Entity does not take attack damage.<br>
         * <br>
         * This event does not have a result.<br>
         * <br>
         **/
        LivingAttackEvent.ATTACK.register(onAttacked);

        /**
         * LivingHurtEvent is fired when an Entity is set to be hurt. <br>
         * This event is fired whenever an Entity is hurt in
         * {@code LivingEntity#actuallyHurt(DamageSource, float)} and
         * {@code Player#actuallyHurt(DamageSource, float)}.<br>
         * <br>
         * {@link #source} contains the DamageSource that caused this Entity to be hurt. <br>
         * {@link #amount} contains the amount of damage dealt to the Entity that was hurt. <br>
         * <br>
         * This event is cancelable.<br>
         * If this event is canceled, the Entity is not hurt.<br>
         * <br>
         * This event does not have a result.<br>
         * <br>
         *
         * @see LivingDamageEvent
         **/
        LivingHurtEvent.HURT.register(onHurting);
        LivingHurtEvent.HURT.register(onHurt);

        /**
         * LivingDamageEvent is fired just before damage is applied to entity.<br>
         * At this point armor, potion and absorption modifiers have already been applied to damage - this is FINAL value.<br>
         * Also note that appropriate resources (like armor durability and absorption extra hearths) have already been consumed.<br>
         * This event is fired whenever an Entity is damaged in
         * {@code LivingEntity#actuallyHurt(DamageSource, float)} and
         * {@code Player#actuallyHurt(DamageSource, float)}.<br>
         * <br>
         * {@link #source} contains the DamageSource that caused this Entity to be hurt. <br>
         * {@link #amount} contains the final amount of damage that will be dealt to entity. <br>
         * <br>
         * This event is cancelable.<br>
         * If this event is canceled, the Entity is not hurt. Used resources WILL NOT be restored.<br>
         * <br>
         * This event does not have a result.<br>
         *
         * @see LivingHurtEvent
         **/
        LivingDamageEvent.DAMAGE.register(onDamaged);

        ServerLivingEntityEvents.AFTER_DEATH.register(onDeath);

        ServerLivingEntityEvents.ALLOW_DEATH.register(UndyingTrait.allowDeath);

        LivingEntityEvents.DROPS.register((target, source, drops, lootingLevel, recentlyHit) ->
        {
            var op = MobDifficulty.get(target);
            if (op.isEmpty()) return true;
            MobDifficulty diff = op.get();
            if (diff.noDrop) return true;
            LivingEntity killer = target.getPrimeAdversary();
            if (killer != null && TrinketCompat.hasItemInTrinket(killer, LHItems.NIDHOGGUR))
            {
                double val = LHConfig.common().items.nidhoggurDropFactor * diff.getLevel();
                int count = (int) val;
                if (target.getRandom().nextDouble() < val - count) count++;
                count++;
                for (var stack : drops)
                    stack.getStack().setCount(stack.getStack().getCount() * count);
            }
            return true;
        });

        LivingEntityEvents.EXPERIENCE_DROP.register((i, playerEntity, livingEntity) ->
        {
            var op = MobDifficulty.get(livingEntity);
            if (op.isEmpty()) return i;
            MobDifficulty diff = op.get();
            if (diff.noDrop) return i;
            int exp = i;
            int level = diff.getLevel();
            exp = (int) (exp * (1 + LHConfig.common().scaling.expDropFactor * level));
            return exp;
        });
    }
//        OnDatapackSyncCallback.EVENT.register((playerManager, serverPlayerEntity) ->
//        {
//            List<TraitLootModifier> list = new ArrayList<>();
//            for (var e : ForgeInternalHandlerAccessor.callGetLootModifierManager().getAllLootMods())
//                if (e instanceof TraitLootModifier loot)
//                    list.add(loot);
//            LootDataToClient packet = new LootDataToClient(list);
//            if (event.getPlayer() == null)
//                L2Hostility.HANDLER.toAllClient(packet);
//            else
//                L2Hostility.HANDLER.toClientPlayer(packet, event.getPlayer());
//        });
//
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
//
//    }
}
