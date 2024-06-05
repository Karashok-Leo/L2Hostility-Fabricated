package net.karashokleo.l2hostility.content.item.complements;

import net.karashokleo.l2hostility.init.LHCplTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class NoGravMagicalItem extends TooltipItem
{
    public NoGravMagicalItem(Settings settings, Supplier<MutableText> textSupplier)
    {
        super(settings, textSupplier);
    }

    public boolean onEntityItemUpdate(ItemEntity entity)
    {
        World world = entity.getWorld();
        Vec3d pos = entity.getPos();
        NbtCompound persistentData = entity.getCustomData();

        if (world.isClient())
        {
            if (world.random.nextFloat() < getIdleParticleChance(entity))
            {
                Vec3d ppos = offsetRandomly(pos, world.random, .5f);
                world.addParticle(ParticleTypes.END_ROD, ppos.x, pos.y, ppos.z, 0, -.1f, 0);
            }

            if (entity.isSilent() && !persistentData.getBoolean("PlayEffects"))
            {
                Vec3d baseMotion = new Vec3d(0, 1, 0);
                world.addParticle(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 0, 0, 0);
                for (int i = 0; i < 20; i++)
                {
                    Vec3d motion = offsetRandomly(baseMotion, world.random, 1);
                    world.addParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                    world.addParticle(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                }
                persistentData.putBoolean("PlayEffects", true);
            }
            return false;
        }

        entity.setGlowing(true);
        entity.setNoGravity(true);

        if (!persistentData.contains("JustCreated"))
            return false;
        onCreated(entity, persistentData);
        return false;
    }

    protected float getIdleParticleChance(ItemEntity entity)
    {
        return MathHelper.clamp(entity.getStack().getCount() - 10, 5, 100) / 64f;
    }

    protected void onCreated(ItemEntity entity, NbtCompound persistentData)
    {
//        entity.lifespan = 6000;
        persistentData.remove("JustCreated");
        // just a flag to tell the client to play an effect
        entity.setSilent(true);
    }

    public static Vec3d offsetRandomly(Vec3d vec, Random r, float radius)
    {
        return new Vec3d(vec.x + (r.nextFloat() - .5f) * 2 * radius, vec.y + (r.nextFloat() - .5f) * 2 * radius, vec.z + (r.nextFloat() - .5f) * 2 * radius);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(LHCplTexts.FLOAT.get().formatted(Formatting.GRAY));
    }
}
