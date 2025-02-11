package karashokleo.l2hostility.content.item.trinket.core;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;

public interface ReflectTrinket
{
    /*
     * If the entity can reflect the given trait
     * */
    static boolean canReflect(LivingEntity entity, MobTrait trait)
    {
        return !TrinketCompat.getTrinketItems(entity, stack -> stack.getItem() instanceof ReflectTrinket reflectTrinket && reflectTrinket.canReflect(trait)).isEmpty();
    }

    /*
     * If the trinket can reflect the given trait
     * */
    boolean canReflect(MobTrait trait);
}
