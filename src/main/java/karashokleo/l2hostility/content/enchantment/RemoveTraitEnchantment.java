package karashokleo.l2hostility.content.enchantment;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

import java.util.function.Supplier;

public class RemoveTraitEnchantment extends HostilityEnchantment implements HitTargetEnchantment
{
    private final Supplier<MobTrait> sup;

    public RemoveTraitEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes, Supplier<MobTrait> sup)
    {
        super(weight, target, slotTypes, 1);
        this.sup = sup;
    }

    @Override
    public void onHurting(int level, LivingEntity attacker, LivingHurtEvent event)
    {
        MobDifficulty.get(event.getEntity()).ifPresent(diff ->
        {
            diff.removeTrait(sup.get());
            diff.sync();
        });
    }
}
