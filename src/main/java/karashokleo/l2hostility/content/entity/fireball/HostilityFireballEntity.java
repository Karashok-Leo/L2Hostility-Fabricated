package karashokleo.l2hostility.content.entity.fireball;

import karashokleo.l2hostility.init.LHEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class HostilityFireballEntity extends BaseFireballEntity
{
    public static final String EXPLOSION_POWER_KEY = "ExplosionPower";
    public static final String CREATE_FIRE_KEY = "CreateFire";
    public static final String DESTROY_BLOCK_KEY = "DestroyBlock";
    public static final String DAMAGE_KEY = "Damage";
    private int explosionPower = 1;
    private boolean createFire = false;
    private boolean destroyBlock = false;
    private float damage = 4.0f;

    public HostilityFireballEntity(EntityType<? extends HostilityFireballEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public HostilityFireballEntity(double x, double y, double z, double vx, double vy, double vz, World world, int explosionPower, boolean createFire, boolean destroyBlock, float damage)
    {
        super(LHEntities.HOSTILITY_FIREBALL, x, y, z, vx, vy, vz, world);
        this.explosionPower = explosionPower;
        this.createFire = createFire;
        this.destroyBlock = destroyBlock;
        this.damage = damage;
    }

    public HostilityFireballEntity(World world, LivingEntity owner, double vx, double vy, double vz, int explosionPower, boolean createFire, boolean destroyBlock, float damage)
    {
        super(LHEntities.HOSTILITY_FIREBALL, owner, vx, vy, vz, world);
        this.explosionPower = explosionPower;
        this.createFire = createFire;
        this.destroyBlock = destroyBlock;
        this.damage = damage;
    }

    @Override
    protected void onCollisionAction(Vec3d pos)
    {
        Explosion explosion = new Explosion(
                this.getWorld(), this, null, null,
                this.getX(), this.getY(), this.getZ(),
                this.explosionPower,
                this.createFire,
                this.destroyBlock ? Explosion.DestructionType.DESTROY_WITH_DECAY : Explosion.DestructionType.KEEP
        );
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
    }

    @Override
    protected void onEntityHitAction(Entity target)
    {
        Entity owner = this.getOwner();
        boolean damaged = target.damage(this.getDamageSources().fireball(this, owner), this.damage);
        if (owner instanceof LivingEntity && damaged)
        {
            this.applyDamageEffects((LivingEntity) owner, target);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt)
    {
        super.writeCustomDataToNbt(nbt);
        nbt.putByte(EXPLOSION_POWER_KEY, (byte) this.explosionPower);
        nbt.putBoolean(CREATE_FIRE_KEY, this.createFire);
        nbt.putBoolean(DESTROY_BLOCK_KEY, this.destroyBlock);
        nbt.putFloat(DAMAGE_KEY, this.damage);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt)
    {
        super.readCustomDataFromNbt(nbt);
        this.explosionPower = nbt.getByte(EXPLOSION_POWER_KEY);
        this.createFire = nbt.getBoolean(CREATE_FIRE_KEY);
        this.destroyBlock = nbt.getBoolean(DESTROY_BLOCK_KEY);
        this.damage = nbt.getFloat(DAMAGE_KEY);
    }

    @Override
    protected float getDrag()
    {
        return 0.95F;
    }

    @Override
    protected boolean isBurning()
    {
        return true;
    }
}
