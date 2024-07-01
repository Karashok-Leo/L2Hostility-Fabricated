package karashokleo.l2hostility.content.enchantment;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface HitTargetEnchantment
{
    static void handle(LivingEntity entity, LivingHurtEvent event)
    {
        ItemStack mainhand = entity.getMainHandStack();
        ItemStack offhand = entity.getOffHandStack();
        if (!handle(mainhand, entity, event))
            handle(offhand, entity, event);
    }

    static boolean handle(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        for (var entry : EnchantmentHelper.get(stack).entrySet())
            if (entry.getKey() instanceof HitTargetEnchantment ench)
            {
                ench.onHurting(entry.getValue(), entity, event);
                return true;
            }
        return false;
    }

    void onHurting(int level, LivingEntity entity, LivingHurtEvent event);
}
