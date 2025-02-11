package karashokleo.l2hostility.content.item.consumable.charge;

import karashokleo.l2hostility.content.entity.fireball.BaseFireballEntity;
import karashokleo.l2hostility.init.LHCplTexts;
import karashokleo.l2hostility.util.raytrace.RayTraceUtil;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseChargeItem extends Item
{
    protected final PlayerFire<?> playerFire;
    protected final BlockFire<?> blockFire;

    public BaseChargeItem(Settings settings, PlayerFire<?> playerFire, BlockFire<?> blockFire)
    {
        super(settings);
        this.playerFire = playerFire;
        this.blockFire = blockFire;
        // 发射器行为注册
        // 放在这里是否合适?
        DispenserBlock.registerBehavior(this, new FireChargeBehavior(blockFire));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        ItemStack itemstack = player.getStackInHand(hand);
        Random r = player.getRandom();
        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, (r.nextFloat() - r.nextFloat()) * 0.2F + 1.0F);
        if (!world.isClient())
        {
            Vec3d v = RayTraceUtil.getRayTerm(Vec3d.ZERO, player.getPitch(), player.getYaw(), 1);
            BaseFireballEntity fireBall = playerFire.create(player, 0, 0, 0, world);
            fireBall.setItem(itemstack);
            fireBall.setPosition(player.getEyePos().add(0, -0.1, 0).add(v));
            fireBall.setVelocity(v);
            world.spawnEntity(fireBall);
        }
        player.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!player.getAbilities().creativeMode) itemstack.decrement(1);
        return TypedActionResult.success(itemstack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHCplTexts.CHARGE_THROW.get().formatted(Formatting.GRAY));
    }

    public interface PlayerFire<T extends BaseFireballEntity>
    {
        T create(PlayerEntity player, double vx, double vy, double vz, World world);
    }

    public interface BlockFire<T extends BaseFireballEntity>
    {
        T create(double x, double y, double z, double vx, double vy, double vz, World world);
    }
}
