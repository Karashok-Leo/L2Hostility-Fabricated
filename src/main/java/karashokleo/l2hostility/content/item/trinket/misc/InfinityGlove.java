package karashokleo.l2hostility.content.item.trinket.misc;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.BaseTrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class InfinityGlove extends BaseTrinketItem
{
    public InfinityGlove(Settings settings)
    {
        super(settings);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        var map = super.getModifiers(stack, slot, entity, uuid);
        if (slot.inventory().getSlotType().getGroup().equals("hand"))
        {
            SlotAttributes.addSlotModifier(map, "hand/ring", uuid, 5, EntityAttributeModifier.Operation.ADDITION);
            SlotAttributes.addSlotModifier(map, "chest/charm", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
        }
        if (slot.inventory().getSlotType().getGroup().equals("offhand"))
        {
            SlotAttributes.addSlotModifier(map, "offhand/ring", uuid, 5, EntityAttributeModifier.Operation.ADDITION);
            SlotAttributes.addSlotModifier(map, "chest/charm", uuid, 1, EntityAttributeModifier.Operation.ADDITION);
        }
        return map;
    }
}
