package karashokleo.l2hostility.content.item.complements;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BurntItem extends Item
{
    public BurntItem(Settings settings)
    {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return true;
    }
}
