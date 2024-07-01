package karashokleo.l2hostility.content.item.misc.tool;

import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DetectorGlasses extends Item implements Equipment
{
    public DetectorGlasses(Settings settings)
    {
        super(settings);
    }

    @Override
    public EquipmentSlot getSlotType()
    {
        return EquipmentSlot.HEAD;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_GLASSES.get().formatted(Formatting.GRAY));
        tooltip.add(LHTexts.sectionRender().formatted(Formatting.DARK_GREEN));
    }
}
