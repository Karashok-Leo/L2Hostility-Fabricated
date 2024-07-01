package karashokleo.l2hostility.content.item.complements;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class TransformItem extends TooltipItem
{
    private final Supplier<EntityType<? extends MobEntity>> from;
    private final Supplier<EntityType<? extends MobEntity>> to;

    public TransformItem(
            Settings settings,
            Supplier<MutableText> textSupplier,
            Supplier<EntityType<? extends MobEntity>> from,
            Supplier<EntityType<? extends MobEntity>> to
    )
    {
        super(settings, textSupplier);
        this.from = from;
        this.to = to;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand)
    {
        World world = entity.getWorld();
        if (entity.getType() != from.get()) return ActionResult.FAIL;
        if (world.getDifficulty() == Difficulty.PEACEFUL) return ActionResult.FAIL;
        if (world instanceof ServerWorld serverWorld)
        {
            convertMob(serverWorld, entity);
            stack.decrement(1);
            return ActionResult.CONSUME;
        }
        return ActionResult.SUCCESS;
    }

    private void convertMob(ServerWorld world, LivingEntity entity)
    {
        MobEntity result = to.get().create(world);
        assert result != null;
        result.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch());
        result.initialize(world, world.getLocalDifficulty(result.getBlockPos()), SpawnReason.CONVERSION, null, null);
        result.setAiDisabled(((MobEntity) entity).isAiDisabled());
        if (entity.hasCustomName())
        {
            result.setCustomName(entity.getCustomName());
            result.setCustomNameVisible(entity.isCustomNameVisible());
        }
        result.setPersistent();
        world.spawnEntity(result);
        entity.discard();
    }
}
