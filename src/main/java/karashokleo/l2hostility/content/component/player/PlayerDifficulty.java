package karashokleo.l2hostility.content.component.player;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.content.logic.LevelEditor;
import karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import karashokleo.l2hostility.content.logic.TraitManager;
import karashokleo.l2hostility.init.LHComponents;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@SerialClass
public class PlayerDifficulty
{
    @SerialClass.SerialField
    public final TreeSet<Identifier> dimensions = new TreeSet<>();
    @SerialClass.SerialField
    private final DifficultyLevel difficulty = new DifficultyLevel();
    public PlayerEntity owner;
    @SerialClass.SerialField
    public int maxRankKilled = 0, rewardCount = 0;
    @Nullable
    public ChunkDifficulty prevChunk;

    public PlayerDifficulty(PlayerEntity player)
    {
        this.owner = player;
    }

    public static PlayerDifficulty get(PlayerEntity player)
    {
        return player.getComponent(LHComponents.PLAYER_DIFFICULTY).diff;
    }

    public static void sync(PlayerEntity player)
    {
        LHComponents.PLAYER_DIFFICULTY.sync(player);
    }

    public void onClone(boolean lossless, boolean keepInventory)
    {
        if (lossless)
        {
            return;
        }
        if (LHConfig.common().difficulty.keepInventoryRuleKeepDifficulty && keepInventory)
        {
            return;
        }
        if (LHConfig.common().difficulty.deathDecayDimension)
        {
            dimensions.clear();
        }
        if (LHConfig.common().difficulty.deathDecayTraitCap)
        {
            if (maxRankKilled > 0)
            {
                maxRankKilled--;
            }
        }
        difficulty.decay();
    }

    public void tick()
    {
        if (!(owner instanceof ServerPlayerEntity))
        {
            return;
        }
        // 恶意刷怪笼
//        var opt = ChunkDifficulty.at(owner.getWorld(), owner.getBlockPos());
//        if (opt.isPresent()) {
//            var sec = opt.get().getSection(player.getBlockPos().getY());
//            if (sec.activePos != null) {
//                if (player.getWorld().canSetBlock(sec.activePos)) {
//                    if (player.getWorld().getBlockEntity(sec.activePos) instanceof TraitSpawnerBlockEntity spawner) {
//                        spawner.track(player);
//                    }
//                }
//            }
//        }
        if (dimensions.add(owner.getWorld().getDimensionKey().getValue()))
        {
            sync(owner);
        }
    }

    public void apply(MobDifficultyCollector instance)
    {
        instance.setPlayer(owner);
        instance.acceptBonus(getLevel());
        instance.setTraitCap(getRankCap());
        for (var stack : CurseTrinketItem.getFromPlayer(owner))
        {
            stack.item().modifyDifficulty(instance);
        }
    }

    public int getRankCap()
    {
        return TraitManager.getTraitCap(maxRankKilled, difficulty);
    }

    public void addKillCredit(MobDifficulty cap)
    {
        double growFactor = 1;
        // 从饰品栏获取难度增长系数
        for (var stack : CurseTrinketItem.getFromPlayer(owner))
        {
            growFactor *= stack.item().getGrowFactor(stack.stack(), this, cap);
        }
        difficulty.grow(growFactor, cap);
        cap.traits.values().stream().max(Comparator.naturalOrder())
            .ifPresent(integer -> maxRankKilled = Math.max(maxRankKilled, integer));
        if (getLevel().getLevel() > rewardCount * 10)
        {
            rewardCount++;
            // 奖励恶意吸收宝珠
            owner.getInventory().insertStack(ConsumableItems.HOSTILITY_ORB.getDefaultStack());
            // TODO drop reward
        }
        sync(owner);
    }

    public int getRewardCount()
    {
        return rewardCount;
    }

    public DifficultyLevel getLevel()
    {
        return DifficultyLevel.merge(difficulty, getExtraLevel());
    }

    private int getDimCount()
    {
        return Math.max(0, dimensions.size() - 1);
    }

    private int getExtraLevel()
    {
        int ans = 0;
        ans += getDimCount() * LHConfig.common().scaling.dimensionFactor;
        ans += (int) owner.getAttributeValue(LHMiscs.ADD_LEVEL);
        return ans;
    }

    public List<Text> getPlayerDifficultyDetail()
    {
        int item = (int) owner.getAttributeValue(LHMiscs.ADD_LEVEL);
        int dim = getDimCount() * LHConfig.common().scaling.dimensionFactor;
        return List.of(
            LHTexts.INFO_PLAYER_ADAPTIVE_LEVEL.get(difficulty.level).formatted(Formatting.GRAY),
            LHTexts.INFO_PLAYER_ITEM_LEVEL.get(item).formatted(Formatting.GRAY),
            LHTexts.INFO_PLAYER_DIM_LEVEL.get(dim).formatted(Formatting.GRAY),
            LHTexts.INFO_PLAYER_EXT_LEVEL.get(difficulty.extraLevel).formatted(Formatting.GRAY)
        );
    }

    public LevelEditor getLevelEditor()
    {
        return new LevelEditor(difficulty, getExtraLevel());
    }
}
