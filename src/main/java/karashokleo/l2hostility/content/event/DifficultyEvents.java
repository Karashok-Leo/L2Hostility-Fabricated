package karashokleo.l2hostility.content.event;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.chunk.WorldChunk;

public class DifficultyEvents
{
    public static void register()
    {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) ->
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
}
