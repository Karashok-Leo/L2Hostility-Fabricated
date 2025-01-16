package karashokleo.l2hostility.content.enchantment.weapon;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.enchantment.RemoveTraitEnchantment;
import karashokleo.l2hostility.init.LHTraits;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;

public class SplitSuppressEnchantment extends RemoveTraitEnchantment
{
    public static final String FLAG = "SuppressSplit";

    public SplitSuppressEnchantment()
    {
        super(
                Enchantment.Rarity.VERY_RARE,
                EnchantmentTarget.WEAPON,
                new EquipmentSlot[]{EquipmentSlot.MAINHAND},
                () -> LHTraits.SPLIT
        );
    }

    @Override
    public void onHurting(int level, LivingEntity attacker, LivingHurtEvent event)
    {
        super.onHurting(level, attacker, event);
        if (event.getEntity() instanceof SlimeEntity slime)
        {
            slime.addCommandTag(FLAG);
        }
    }
}
