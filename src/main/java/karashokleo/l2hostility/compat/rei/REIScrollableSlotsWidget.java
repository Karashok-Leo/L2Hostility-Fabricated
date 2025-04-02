package karashokleo.l2hostility.compat.rei;

import com.google.common.collect.Lists;
import me.shedaniel.clothconfig2.ClothConfigInitializer;
import me.shedaniel.clothconfig2.api.scroll.ScrollingContainer;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.gui.widgets.CloseableScissors;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.WidgetWithBounds;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public class REIScrollableSlotsWidget extends WidgetWithBounds
{
    private final Rectangle bounds;
    private final List<Slot> widgets;
    private final ScrollingContainer scrolling = new ScrollingContainer()
    {
        public Rectangle getBounds()
        {
            Rectangle bounds = REIScrollableSlotsWidget.this.getBounds();
            return new Rectangle(bounds.x + 1, bounds.y + 1, bounds.width - 2, bounds.height - 2);
        }

        public int getMaxScrollHeight()
        {
            return MathHelper.ceil((float) REIScrollableSlotsWidget.this.widgets.size() / 8.0F) * 18;
        }
    };

    public REIScrollableSlotsWidget(Rectangle bounds, List<Slot> widgets)
    {
        this.bounds = Objects.requireNonNull(bounds);
        this.widgets = Lists.newArrayList(widgets);
    }

    public boolean mouseScrolled(double double_1, double double_2, double double_3)
    {
        if (this.containsMouse(double_1, double_2))
        {
            this.scrolling.offset(ClothConfigInitializer.getScrollStep() * -double_3, true);
            return true;
        } else
        {
            return false;
        }
    }

    public Rectangle getBounds()
    {
        return this.bounds;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        return this.scrolling.updateDraggingState(mouseX, mouseY, button) || super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
    {
        return this.scrolling.mouseDragged(mouseX, mouseY, button, deltaX, deltaY) || super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public void render(DrawContext graphics, int mouseX, int mouseY, float delta)
    {
        this.scrolling.updatePosition(delta);
        Rectangle innerBounds = this.scrolling.getScissorBounds();

        try (CloseableScissors ignored = scissor(graphics, innerBounds))
        {
            for (int y = 0; y < MathHelper.ceil((float) this.widgets.size() / 8.0F); ++y)
            {
                for (int x = 0; x < 8; ++x)
                {
                    int index = y * 8 + x;
                    if (this.widgets.size() <= index)
                    {
                        break;
                    }

                    Slot widget = this.widgets.get(index);
                    widget.getBounds().setLocation(this.bounds.x + 2 + x * 18, this.bounds.y + 2 + y * 18 - this.scrolling.scrollAmountInt());
                    widget.render(graphics, mouseX, mouseY, delta);
                }
            }
        }

        try (CloseableScissors ignored = scissor(graphics, this.scrolling.getBounds()))
        {
            this.scrolling.renderScrollBar(graphics, -16777216, 1.0F, REIRuntime.getInstance().isDarkThemeEnabled() ? 0.8F : 1.0F);
        }
    }

    public List<? extends Element> children()
    {
        return this.widgets;
    }
}
