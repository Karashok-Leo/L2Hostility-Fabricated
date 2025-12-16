package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.client.gui.DrawContext;

public class PageSlotWidget extends SlotWidget
{
    public final PageManager<EmiStack> manager;
    public final int offset;

    public PageSlotWidget(PageManager<EmiStack> manager, int offset, int x, int y)
    {
        super(EmiStack.EMPTY, x, y);
        this.manager = manager;
        this.offset = offset;
    }

    @Override
    public EmiIngredient getStack()
    {
        EmiStack stack = this.manager.get(this.offset);
        return stack == null ? EmiStack.EMPTY : stack;
    }

    @Override
    public void render(DrawContext draw, int mouseX, int mouseY, float delta)
    {
        if (this.getStack().isEmpty())
        {
            return;
        }
        super.render(draw, mouseX, mouseY, delta);
    }

    @Override
    public void drawBackground(DrawContext draw, int mouseX, int mouseY, float delta)
    {
        super.drawBackground(draw, mouseX, mouseY, delta);
    }
}
