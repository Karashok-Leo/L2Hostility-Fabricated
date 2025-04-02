package karashokleo.l2hostility.content.item.trinket.core;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public interface DamageListenerTrinket
{
    default void onDamageSourceCreate(ItemStack stack, LivingEntity entity, DamageSource damageSource, RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Vec3d position)
    {
    }

    default void onAttacking(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
    }

    default void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
    }

    default void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
    }

    default void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
    }

    default void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
    }
}
