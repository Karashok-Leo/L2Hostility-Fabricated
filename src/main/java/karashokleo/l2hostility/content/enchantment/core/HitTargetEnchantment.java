package karashokleo.l2hostility.content.enchantment.core;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface HitTargetEnchantment
{
    static void handle(LivingEntity attacker, LivingHurtEvent event)
    {
        ItemStack mainhand = attacker.getMainHandStack();
        ItemStack offhand = attacker.getOffHandStack();
        if (!handle(mainhand, attacker, event))
            handle(offhand, attacker, event);
    }

    static boolean handle(ItemStack stack, LivingEntity attacker, LivingHurtEvent event)
    {
        for (var entry : EnchantmentHelper.get(stack).entrySet())
            if (entry.getKey() instanceof HitTargetEnchantment ench)
            {
                ench.onHurting(entry.getValue(), attacker, event);
                return true;
            }
        return false;
    }

    void onHurting(int level, LivingEntity attacker, LivingHurtEvent event);
}
