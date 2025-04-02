package karashokleo.l2hostility.content.item.trinket.core;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

/**
 * Trinket item that player can only wear single
 */
public class SingleEpicTrinketItem extends TrinketItem
{
    public SingleEpicTrinketItem()
    {
        super(new FabricItemSettings());
    }

    public SingleEpicTrinketItem(Settings settings)
    {
        super(settings.maxCount(1).fireproof().rarity(Rarity.EPIC));
    }

    public SingleEpicTrinketItem(Settings settings, int durability)
    {
        super(settings.maxDamage(durability).fireproof().rarity(Rarity.EPIC));
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        return !TrinketCompat.hasItemInTrinket(entity, this) &&
               super.canEquip(stack, slot, entity);
    }
}
