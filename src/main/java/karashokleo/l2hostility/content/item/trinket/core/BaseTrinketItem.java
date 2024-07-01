package karashokleo.l2hostility.content.item.trinket.core;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.util.Rarity;

public class BaseTrinketItem extends TrinketItem
{
    public BaseTrinketItem(Settings settings)
    {
        super(settings.maxCount(1).fireproof().rarity(Rarity.EPIC));
    }

    public BaseTrinketItem(Settings settings,int durability)
    {
        super(settings.maxDamage(durability).fireproof().rarity(Rarity.EPIC));
    }
}
