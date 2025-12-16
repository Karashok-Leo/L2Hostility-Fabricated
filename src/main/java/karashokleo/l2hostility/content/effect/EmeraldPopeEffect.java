package karashokleo.l2hostility.content.effect;

import karashokleo.effect_overlay.api.ClientRenderEffect;
import karashokleo.effect_overlay.api.EffectRenderer;
import karashokleo.effect_overlay.api.FirstPlayerRenderEffect;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHDamageTypes;
import karashokleo.l2hostility.init.LHParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EmeraldPopeEffect extends StatusEffect implements ClientRenderEffect, FirstPlayerRenderEffect
{
    public EmeraldPopeEffect()
    {
        super(StatusEffectCategory.NEUTRAL, 0x00FF00);
    }

    private static void renderEffect(int lv, Entity entity)
    {
        if (MinecraftClient.getInstance().isPaused())
        {
            return;
        }
        int r = (lv + 1) * LHConfig.common().complements.properties.emeraldBaseRange;
        int count = (1 + lv) * (1 + lv) * 4;
        for (int i = 0; i < count; i++)
        {
            addParticle(entity.getWorld(), entity.getPos(), r);
        }
    }

    @Environment(EnvType.CLIENT)
    private static void addParticle(World w, Vec3d vec, int r)
    {
        float tpi = (float) (Math.PI * 2);
        Vec3d v0 = new Vec3d(0, r, 0);
        Vec3d v1 = v0.rotateX(tpi / 3).rotateY((float) (Math.random() * tpi));
        float a0 = (float) (Math.random() * tpi);
        float b0 = (float) Math.acos(2 * Math.random() - 1);
        v0 = v0.rotateX(a0).rotateY(b0);
        v1 = v1.rotateX(a0).rotateY(b0);
        w.addImportantParticle(
            LHParticles.EMERALD,
            vec.x + v0.x,
            vec.y + v0.y,
            vec.z + v0.z,
            vec.x + v1.x,
            vec.y + v1.y,
            vec.z + v1.z
        );
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        if (entity.getWorld().isClient())
        {
            return;
        }
        int radius = (amplifier + 1) * LHConfig.common().complements.properties.emeraldBaseRange;
        var atk = entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        int damage = (int) (LHConfig.common().complements.properties.emeraldDamageFactor * (atk == null ? 1 : atk.getValue()));
        DamageSource source = entity.getDamageSources().create(LHDamageTypes.EMERALD);
        Vec3d selfPos = entity.getPos();
        for (Entity target : entity.getWorld().getOtherEntities(entity, new Box(entity.getBlockPos()).expand(radius)))
        {
            Vec3d targetPos = target.getPos();
            if (target instanceof Monster &&
                !target.isTeammate(entity) &&
                ((LivingEntity) target).hurtTime == 0 &&
                targetPos.squaredDistanceTo(selfPos) < radius * radius)
            {
                double dist = targetPos.distanceTo(selfPos);
                if (dist > 0.1)
                {
                    ((LivingEntity) target).takeKnockback(0.4F, selfPos.x - targetPos.x, selfPos.z - targetPos.z);
                }
                target.damage(source, damage);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 10 == 0;
    }

    @Override
    public @Nullable EffectRenderer getRenderer(LivingEntity entity, int lv)
    {
        if (entity != MinecraftClient.getInstance().player)
        {
            renderEffect(lv, entity);
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientWorldRender(AbstractClientPlayerEntity player, StatusEffectInstance value)
    {
        renderEffect(value.getAmplifier(), player);
    }
}
