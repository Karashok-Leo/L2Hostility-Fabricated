package karashokleo.l2hostility.content.item.trinket.core;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class DamageListenerTrinketItem extends BaseTrinketItem
{
    public DamageListenerTrinketItem(Settings settings)
    {
        super(settings);
    }

    public void onAttacking(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
    }

    public void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
    }

    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
    }
}
