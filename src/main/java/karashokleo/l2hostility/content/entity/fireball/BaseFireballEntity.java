package karashokleo.l2hostility.content.entity.fireball;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BaseFireballEntity extends AbstractFireballEntity
{
    public final int lifespan = 200;

    public BaseFireballEntity(EntityType<? extends BaseFireballEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public BaseFireballEntity(EntityType<? extends BaseFireballEntity> entityType, double x, double y, double z, double vx, double vy, double vz, World world)
    {
        super(entityType, x, y, z, vx, vy, vz, world);
    }

    public BaseFireballEntity(EntityType<? extends BaseFireballEntity> entityType, LivingEntity owner, double vx, double vy, double vz, World world)
    {
        super(entityType, owner, vx, vy, vz, world);
    }

    protected void onCollisionAction(Vec3d pos)
    {
    }

    protected void onEntityHitAction(Entity target)
    {
    }

    protected void onBlockHitAction(BlockPos pos)
    {
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket()
    {
        return super.createSpawnPacket();
    }

    @Override
    public void tick()
    {
        super.tick();
        if (this.age >= this.lifespan)
        {
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);
        if (this.getWorld().isClient())
        {
            return;
        }
        this.onCollisionAction(hitResult.getPos());
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult)
    {
        super.onEntityHit(entityHitResult);
        if (this.getWorld().isClient())
        {
            return;
        }
        this.onEntityHitAction(entityHitResult.getEntity());
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult)
    {
        super.onBlockHit(blockHitResult);
        if (this.getWorld().isClient())
        {
            return;
        }
        this.onBlockHitAction(blockHitResult.getBlockPos().offset(blockHitResult.getSide()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt)
    {
        super.readCustomDataFromNbt(nbt);
        this.age = nbt.getInt("Age");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt)
    {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Age", this.age);
    }

    // 飞行阻力，设置为1即无阻力
    @Override
    protected float getDrag()
    {
        return 1;
//        return super.getDrag();
    }

    // 受击逻辑，返回false则无法反弹
    @Override
    public boolean damage(DamageSource source, float amount)
    {
        return super.damage(source, amount);
//        return false;
    }

    @Override
    protected boolean canHit(Entity entity)
    {
        return super.canHit(entity);
//        return false;
    }

    @Override
    protected boolean isBurning()
    {
        return false;
    }
}
