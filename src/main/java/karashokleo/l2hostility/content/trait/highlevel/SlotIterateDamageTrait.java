package karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

public abstract class SlotIterateDamageTrait extends MobTrait
{
    public SlotIterateDamageTrait(IntSupplier color)
    {
        super(color);
    }

    public SlotIterateDamageTrait(Formatting format)
    {
        super(format);
    }

    @Override
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        process(level, entity, event.getEntity());
    }

    protected int process(int level, LivingEntity attacker, LivingEntity target)
    {
        List<EquipmentSlot> list = new ArrayList<>();
        for (EquipmentSlot slot : EquipmentSlot.values())
        {
            ItemStack stack = target.getEquippedStack(slot);
            if (stack.isDamageable())
            {
                list.add(slot);
            }
        }
        int count = Math.min(level, list.size());
        for (int i = 0; i < count; i++)
        {
            int index = attacker.getRandom().nextInt(list.size());
            perform(target, list.remove(index));
        }
        return count;
    }

    protected abstract void perform(LivingEntity target, EquipmentSlot slot);
}
