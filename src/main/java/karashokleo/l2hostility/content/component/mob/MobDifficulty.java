package karashokleo.l2hostility.content.component.mob;

import com.mojang.datafixers.util.Pair;
import dev.xkmc.l2serial.serialization.SerialClass;
import dev.xkmc.l2serial.util.Wrappers;
import karashokleo.l2hostility.content.component.chunk.ChunkDifficulty;
import karashokleo.l2hostility.content.component.chunk.RegionalDifficultyModifier;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.logic.*;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.init.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@SerialClass
public class MobDifficulty
{
    private static final int TICK_REMOVE_INTERNAl = 10;
    @SerialClass.SerialField
    public final LinkedHashMap<MobTrait, Integer> traits = new LinkedHashMap<>();
    @SerialClass.SerialField
    private final HashMap<Identifier, CapStorageData> data = new HashMap<>();
    private final ArrayList<Pair<MobTrait, Integer>> pending = new ArrayList<>();
    public MobEntity owner;
    @SerialClass.SerialField
    public int lv;
    @SerialClass.SerialField
    public boolean summoned = false, noDrop = false, fullDrop = false;
    @SerialClass.SerialField
    public double dropRate = 1;
    @Nullable
    @SerialClass.SerialField
    public BlockPos pos = null;
    @SerialClass.SerialField
    private Stage stage = Stage.PRE_INIT;
    //    @Nullable
//    private TraitSpawnerBlockEntity summoner = null;
    private boolean inherited = false;
    private boolean ticking = false;
    private EntityConfig.Config configCache = null;

    public MobDifficulty(MobEntity mob)
    {
        this.owner = mob;
    }

    public MobDifficulty(MobEntity owner, Stage stage, int lv, boolean summoned, boolean noDrop, boolean fullDrop, double dropRate, @Nullable BlockPos pos, boolean inherited, boolean ticking)
    {
        this.owner = owner;
        this.stage = stage;
        this.lv = lv;
        this.summoned = summoned;
        this.noDrop = noDrop;
        this.fullDrop = fullDrop;
        this.dropRate = dropRate;
        this.pos = pos;
        this.inherited = inherited;
        this.ticking = ticking;
    }

    public static Optional<MobDifficulty> get(MobEntity mob)
    {
        return Optional.ofNullable(mob.getComponent(LHComponents.MOB_DIFFICULTY).diff);
    }

    public static Optional<MobDifficulty> get(@Nullable Entity entity)
    {
        return entity instanceof MobEntity mob ? get(mob) : Optional.empty();
    }

    public static boolean hasTrait(Entity entity, MobTrait trait)
    {
        Optional<MobDifficulty> diff = MobDifficulty.get(entity);
        return diff.isPresent() && diff.get().hasTrait(trait);
    }

    public void sync()
    {
        LHComponents.MOB_DIFFICULTY.sync(owner);
    }

    public int clampLevel(int lv)
    {
        int cap = LHConfig.common().difficulty.maxMobLevel;
        var config = getConfigCache();
        if (config != null && config.maxLevel > 0)
        {
            cap = Math.min(config.maxLevel, cap);
        }
        return Math.min(cap, lv);
    }

    @Nullable
    public EntityConfig.Config getConfigCache()
    {
        if (configCache == null)
        {
            configCache = LHData.entities.get(owner.getType());
        }
        if (configCache == null)
        {
            configCache = LHData.difficulties.get(owner.getWorld().getRegistryKey().getValue(), owner.getType());
        }
        return configCache;
    }

    public void setConfigCache(EntityConfig.Config config)
    {
        configCache = config;
    }

    public void init(RegionalDifficultyModifier difficulty)
    {
        boolean skip = !LHConfig.common().scaling.allowNoAI && owner.isAiDisabled();
        MobDifficultyCollector instance = new MobDifficultyCollector();
        var config = getConfigCache();
        if (config != null) instance.acceptConfig(config.difficulty);
        difficulty.modifyInstance(owner.getBlockPos(), instance);
        PlayerEntity player = PlayerFinder.getNearestPlayer(owner.getWorld(), owner);
        if (player != null)
        {
            PlayerDifficulty playerDiff = PlayerDifficulty.get(player);
            playerDiff.apply(instance);
            if (!LHConfig.common().scaling.allowPlayerAllies && owner.isTeammate(player)) skip = true;
        }
        lv = skip ? 0 : TraitManager.fill(this, owner, traits, instance);
        fullDrop = instance.isFullDrop();
        stage = Stage.INIT;
        sync();
    }

    public void deinit()
    {
        traits.clear();
        lv = 0;
        stage = Stage.PRE_INIT;
    }

    public boolean reInit(int level, boolean max)
    {
        deinit();
        init((pos, ins) ->
        {
            ins.base = level;
            if (max) ins.setFullChance();
        });
        return true;
    }

    public void copyFrom(MobEntity par, MobEntity child, MobDifficulty parent)
    {
        InheritContext ctx = new InheritContext(par, parent, child, this, !parent.inherited);
        parent.inherited = true;
        lv = parent.lv;
        summoned = parent.summoned;
        noDrop = parent.noDrop;
        dropRate = parent.dropRate * LHConfig.common().scaling.splitDropRateFactor;
        for (var ent : parent.traits.entrySet())
        {
            int rank = ent.getKey().inherited(this, ent.getValue(), ctx);
            if (rank > 0) traits.put(ent.getKey(), rank);
        }
        TraitManager.fill(this, child, traits, MobDifficultyCollector.noTrait(lv));
        stage = Stage.INIT;
    }

    public void traitEvent(BiConsumer<MobTrait, Integer> cons)
    {
        traits.forEach(cons);
    }

    public int getEnchantBonus()
    {
        return (int) (lv * LHConfig.common().scaling.enchantmentFactor);
    }

    public int getLevel()
    {
        return lv;
    }

    public void setLevel(int level)
    {
        lv = level;
        lv = clampLevel(level);
        TraitManager.scale(owner, lv);
    }

    public boolean isInitialized()
    {
        return stage != Stage.PRE_INIT;
    }

    public int getTraitLevel(MobTrait trait)
    {
        return traits.getOrDefault(trait, 0);
    }

    public boolean hasTrait(MobTrait trait)
    {
        return getTraitLevel(trait) > 0;
    }

    public void setTrait(MobTrait trait, int lv)
    {
        pending.add(Pair.of(trait, lv));
    }

    public void removeTrait(MobTrait trait)
    {
        if (!traits.containsKey(trait)) return;
        if (ticking) setTrait(trait, 0);
        else traits.remove(trait);
        sync();
    }

    private boolean clearPending(MobEntity mob)
    {
        if (pending.isEmpty()) return false;
        while (!pending.isEmpty())
        {
            var temp = new ArrayList<>(pending);
            for (var pair : pending)
                traits.put(pair.getFirst(), pair.getSecond());
            pending.clear();
            for (var pair : temp)
            {
                pair.getFirst().initialize(mob, pair.getSecond());
                pair.getFirst().postInit(mob, pair.getSecond());
            }
            for (var pair : temp)
            {
                if (pair.getSecond() == 0)
                {
                    traits.remove(pair.getFirst());
                }
            }
        }
        return true;
    }

    public boolean shouldDiscard()
    {
        var config = getConfigCache();
        if (config == null || config.minSpawnLevel <= 0) return false;
        return lv < config.minSpawnLevel;
    }

    public void serverTick()
    {
        boolean sync = false;
        ticking = true;
        if (!isInitialized())
        {
            if (this.shouldDiscard())
            {
                owner.discard();
                return;
            }
            ChunkDifficulty.at(owner.getWorld(), owner.getBlockPos())
                    .ifPresent(this::init);
        }
        if (stage == Stage.INIT)
        {
            stage = Stage.POST_INIT;
            ItemPopulator.postFill(this, owner);
            traits.forEach((k, v) -> k.postInit(owner, v));
            clearPending(owner);
            owner.setHealth(owner.getMaxHealth());
            sync = true;
        }
        if (!traits.isEmpty() &&
            !LHConfig.common().scaling.allowTraitOnOwnable &&
            owner instanceof Ownable own &&
            own.getOwner() instanceof PlayerEntity)
        {
            traits.clear();
            sync = true;
        }

        if (isInitialized())
        {
            if (!traits.isEmpty())
            {
                if (owner.age % TICK_REMOVE_INTERNAl == 0)
                {
                    sync |= traits.keySet().removeIf(Objects::isNull);
                    sync |= traits.keySet().removeIf(MobTrait::isBanned);
                }
                traits.forEach((k, v) -> k.serverTick(owner, v));
            }
            sync |= clearPending(owner);
        }
        // 恶意刷怪笼
//        if (pos != null)
//        {
//            if (summoner == null) {
//                if (owner.level().getBlockEntity(pos) instanceof TraitSpawnerBlockEntity be) {
//                    summoner = be;
//                }
//            }
//            if (summoner == null || summoner.isRemoved()) {
//                owner.discard();
//            }
//        }
        if (sync && !owner.isRemoved()) sync();
        ticking = false;
    }

    public void onKilled(MobEntity mob, @Nullable PlayerEntity player)
    {
        // 恶意刷怪笼
//        if (summoner != null && !summoner.isRemoved()) {
//            summoner.data.onDeath(mob);
//        }
        // ---
        if (player instanceof ServerPlayerEntity sp)
        {
            LHTriggers.TRAIT_LEVEL.trigger(sp, this);
            LHTriggers.TRAIT_COUNT.trigger(sp, this);
            LHTriggers.KILL_TRAITS.trigger(sp, this);
            LHTriggers.TRAIT_FLAME.trigger(sp, mob, this);
            LHTriggers.TRAIT_EFFECT.trigger(sp, mob, this);
        }
    }

    @Nullable
    public <T extends CapStorageData> T getData(Identifier id)
    {
        return Wrappers.cast(data.get(id));
    }

    public <T extends CapStorageData> T getOrCreateData(Identifier id, Supplier<T> sup)
    {
        return Wrappers.cast(data.computeIfAbsent(id, e -> sup.get()));
    }

    public List<Text> getTitle(boolean showLevel, boolean showTrait)
    {
        List<Text> ans = new ArrayList<>();
        if (showLevel && lv > 0)
            ans.add(LHTexts.LV.get(lv).setStyle(Style.EMPTY
                    .withColor(fullDrop ? LHConfig.client().overHeadLevelColorAbyss :
                            LHConfig.client().overHeadLevelColor)));
        if (!showTrait) return ans;
        MutableText temp = null;
        int count = 0;
        for (var e : traits.entrySet())
        {
            var comp = e.getKey().getFullName(e.getValue());
            if (temp == null)
            {
                temp = comp;
                count = 1;
            } else
            {
                temp.append(Text.literal(" / ").formatted(Formatting.WHITE)).append(comp);
                count++;
                if (count >= 3)
                {
                    ans.add(temp);
                    count = 0;
                    temp = null;
                }
            }
        }
        if (count > 0) ans.add(temp);
        return ans;
    }

    public enum Stage
    {
        PRE_INIT, INIT, POST_INIT
    }
}
