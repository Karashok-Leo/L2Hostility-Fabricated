package karashokleo.l2hostility.content.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class EmeraldParticle extends SpriteBillboardParticle
{
    public static final int LIFE = 20;

    protected EmeraldParticle(ClientWorld world, double x0, double y0, double z0, double x1, double y1, double z1)
    {
        super(world, x0, y0, z0, 0, 0, 0);
        this.velocityX = (x1 - x0) / LIFE;
        this.velocityY = (y1 - y0) / LIFE;
        this.velocityZ = (z1 - z0) / LIFE;
        this.setBoundingBoxSpacing(0.03F, 0.03F);
        this.gravityStrength = 0;
        this.maxAge = LIFE;
    }

    @Override
    public void tick()
    {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0)
        {
            this.markDead();
        } else
        {
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        }
    }

    @Override
    public ParticleTextureSheet getType()
    {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType>
    {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider)
        {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
        {
            EmeraldParticle part = new EmeraldParticle(world, x, y, z, velocityX, velocityY, velocityZ);
            part.setSprite(spriteProvider);
            return part;
        }
    }
}
