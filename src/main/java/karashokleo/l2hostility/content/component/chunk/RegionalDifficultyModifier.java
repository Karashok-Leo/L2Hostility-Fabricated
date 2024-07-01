package karashokleo.l2hostility.content.component.chunk;

import karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import net.minecraft.util.math.BlockPos;

public interface RegionalDifficultyModifier
{
    void modifyInstance(BlockPos pos, MobDifficultyCollector instance);
}
