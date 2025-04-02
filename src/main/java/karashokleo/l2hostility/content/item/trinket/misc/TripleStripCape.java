package karashokleo.l2hostility.content.item.trinket.misc;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public class TripleStripCape extends SingleEpicTrinketItem
{
    public TripleStripCape()
    {
        super();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        var map = super.getModifiers(stack, slot, entity, uuid);
        SlotAttributes.addSlotModifier(map, "chest/back", uuid, 3, EntityAttributeModifier.Operation.ADDITION);
        return map;
    }
}
