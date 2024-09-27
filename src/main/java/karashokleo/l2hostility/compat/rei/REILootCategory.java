package karashokleo.l2hostility.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.l2hostility.init.LHTexts;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class REILootCategory implements DisplayCategory<REILootDisplay>
{
    private static final int SLOT_OFFSET = 36;
    private static final int ARROW_OFFSET = -1;

    @Override
    public List<Widget> setupDisplay(REILootDisplay display, Rectangle bounds)
    {
        Point startPoint = new Point(bounds.getCenterX(), bounds.getCenterY());
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + ARROW_OFFSET, startPoint.y - 9)));
        Slot traits = Widgets.createSlot(new Point(startPoint.x - SLOT_OFFSET - 8, startPoint.y - 8)).entries(display.getTraits()).markInput();
        Slot trinkets = Widgets.createSlot(new Point(startPoint.x - SLOT_OFFSET + 8 + 6, startPoint.y - 8)).entries(display.getTrinkets()).markInput();
        Slot loot = Widgets.createSlot(new Point(startPoint.x + SLOT_OFFSET - 8, startPoint.y - 8)).entries(display.getLoot()).markOutput();
        widgets.add(traits);
        widgets.add(trinkets);
        widgets.add(loot);
        widgets.add(Widgets.createTooltip(point ->
                bounds.contains(point) &&
                !(traits.getBounds().contains(point) ||
                  trinkets.getBounds().contains(point) ||
                  loot.getBounds().contains(point)) ?
                        Tooltip.create(point, display.getTooltip()) : null));
        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 32;
    }

    @Override
    public CategoryIdentifier<? extends REILootDisplay> getCategoryIdentifier()
    {
        return REICompat.LOOT;
    }

    @Override
    public Text getTitle()
    {
        return LHTexts.LOOT_TITLE.get();
    }

    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(Items.IRON_SWORD);
    }
}
