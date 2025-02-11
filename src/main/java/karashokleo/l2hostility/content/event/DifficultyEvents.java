package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.leobrary.damage.api.modify.DamageAccess;
import karashokleo.leobrary.damage.api.modify.DamageModifier;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.chunk.WorldChunk;

public class DifficultyEvents
{
    public static void register()
    {
        // 等级伤害加成
        DamagePhase.ARMOR.registerModifier(1000, DifficultyEvents::onDamageArmor);

        // 等级经验加成
        LivingEntityEvents.EXPERIENCE_DROP.register(DifficultyEvents::onDropExperience);

        // 死亡后降低难度
        ServerLivingEntityEvents.AFTER_DEATH.register(DifficultyEvents::decayDifficulty);
    }

    public static void onDamageArmor(DamageAccess access)
    {
        var optional = MobDifficulty.get(access.getAttacker());
        if (optional.isEmpty()) return;
        int level = optional.get().getLevel();
        double factor = LHConfig.common().scaling.exponentialDamage ?
                Math.pow(1 + LHConfig.common().scaling.damageFactor, level) :
                1 + level * LHConfig.common().scaling.damageFactor;
        access.addModifier(DamageModifier.multiply((float) factor));
    }

    public static int onDropExperience(int amount, PlayerEntity player, LivingEntity livingEntity)
    {
        var op = MobDifficulty.get(livingEntity);
        if (op.isEmpty()) return amount;
        MobDifficulty diff = op.get();
        if (diff.noDrop) return amount;
        int exp = amount;
        int level = diff.getLevel();
        exp = (int) (exp * (1 + LHConfig.common().scaling.expDropFactor * level));
        return exp;
    }

    public static void decayDifficulty(LivingEntity entity, DamageSource damageSource)
    {
        Entity killer = damageSource.getAttacker();
        PlayerEntity player = null;
        if (killer instanceof PlayerEntity pl)
            player = pl;
        else if (killer instanceof Ownable own && own.getOwner() instanceof PlayerEntity pl)
            player = pl;
        var op = MobDifficulty.get(entity);
        if (op.isEmpty()) return;
        MobDifficulty mobDiff = op.get();
        MobEntity mob = mobDiff.owner;
        if (killer != null)
            mobDiff.onKilled(mob, player);
        if (player != null)
        {
            PlayerDifficulty playerDiff = PlayerDifficulty.get(player);
            playerDiff.addKillCredit(mobDiff);
            WorldChunk chunk = mob.getWorld().getWorldChunk(mob.getBlockPos());
            var chunkDiff = ChunkDifficulty.get(chunk);
            if (chunkDiff.isPresent())
                chunkDiff.get().addKillHistory(player, mob, mobDiff);
        }
    }
}
