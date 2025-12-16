package karashokleo.l2hostility.compat.rei;

import com.google.common.collect.Lists;
import karashokleo.l2hostility.init.LHTexts;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;

public class REITraitCategory implements DisplayCategory<REITraitDisplay>
{
    @Override
    public List<Widget> setupDisplay(REITraitDisplay display, Rectangle bounds)
    {
        List<Widget> widgets = Lists.newArrayList();
        Widget entityWidget = Widgets.createDrawableWidget((context, mouseX, mouseY, delta) ->
            display.getEntityWrapper().render(context, bounds.getCenterX(), bounds.getY(), mouseX, mouseY));
        widgets.add(entityWidget);
        int width = 8 * 18 + 4;
        Rectangle rectangle = new Rectangle(
            bounds.getCenterX() - width / 2,
            bounds.getY() + 28,
            width,
            96
        );
        widgets.add(Widgets.createSlotBase(rectangle));
        List<Slot> map = CollectionUtils.map(
            display.getTraits(),
            (t) -> Widgets.createSlot(new Point(0, 0)).disableBackground().entry(t)
        );
        widgets.add(new REIScrollableSlotsWidget(rectangle, map));
        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 144;
    }

    @Override
    public CategoryIdentifier<? extends REITraitDisplay> getCategoryIdentifier()
    {
        return REICompat.TRAIT;
    }

    @Override
    public Text getTitle()
    {
        return LHTexts.TRAIT_TITLE.get();
    }

    @Override
    public Renderer getIcon()
    {
        return EntryStacks.of(Items.SKELETON_SKULL);
    }
}
