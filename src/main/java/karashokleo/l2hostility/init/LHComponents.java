package karashokleo.l2hostility.init;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.component.ChunkDifficultyComponent;
import karashokleo.l2hostility.content.component.MobDifficultyComponent;
import karashokleo.l2hostility.content.component.PlayerDifficultyComponent;
import net.minecraft.entity.mob.MobEntity;

public class LHComponents implements EntityComponentInitializer, ChunkComponentInitializer
{
    public static final ComponentKey<PlayerDifficultyComponent> PLAYER_DIFFICULTY = ComponentRegistry.getOrCreate(L2Hostility.id("player_difficulty"), PlayerDifficultyComponent.class);
    public static final ComponentKey<ChunkDifficultyComponent> CHUNK_DIFFICULTY = ComponentRegistry.getOrCreate(L2Hostility.id("chunk_difficulty"), ChunkDifficultyComponent.class);
    public static final ComponentKey<MobDifficultyComponent> MOB_DIFFICULTY = ComponentRegistry.getOrCreate(L2Hostility.id("mob_difficulty"), MobDifficultyComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(PLAYER_DIFFICULTY, PlayerDifficultyComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
        registry.registerFor(MobEntity.class, MOB_DIFFICULTY, MobDifficultyComponent::new);
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry)
    {
        registry.register(CHUNK_DIFFICULTY, ChunkDifficultyComponent::new);
    }
}
