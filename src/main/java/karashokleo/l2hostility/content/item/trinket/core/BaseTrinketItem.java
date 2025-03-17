package karashokleo.l2hostility.content.item.trinket.core;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;

// trinket that player can only wear single
public class BaseTrinketItem extends TrinketItem
{
    public BaseTrinketItem(Settings settings)
    {
        super(settings.maxCount(1).fireproof().rarity(Rarity.EPIC));
    }

    public BaseTrinketItem(Settings settings, int durability)
    {
        super(settings.maxDamage(durability).fireproof().rarity(Rarity.EPIC));
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        return !TrinketCompat.hasItemInTrinket(entity, this) && super.canEquip(stack, slot, entity);
    }


}
