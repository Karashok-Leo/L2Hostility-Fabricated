package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;

import java.util.List;

public abstract class EMIPageRecipe implements EmiRecipe
{
    protected abstract List<EmiStack> getStacks();

    public int getPageYOffset()
    {
        return 75;
    }

    @Override
    public int getDisplayHeight()
    {
        return ((this.getStacks().size() - 1) / 8 + 1) * 18 + this.getPageYOffset();
    }

    @Override
    public int getDisplayWidth()
    {
        return 144;
    }

    @Override
    public boolean supportsRecipeTree()
    {
        return false;
    }

    @Override
    public void addWidgets(WidgetHolder widgets)
    {
        List<EmiStack> stacks = this.getStacks();
        int ph = (widgets.getHeight() - 42) / 18;
        int pageSize = (ph + 1) * 8;
        PageManager<EmiStack> manager = new PageManager<>(stacks, pageSize);
        if (pageSize < stacks.size())
        {
            widgets.addButton(2, 2, 12, 12, 0, 0, () -> true, (mouseX, mouseY, button) -> manager.scroll(-1));
            widgets.addButton(widgets.getWidth() - 14, 2, 12, 12, 12, 0, () -> true, (mouseX, mouseY, button) -> manager.scroll(1));
        }

        for (int i = 0; i < stacks.size() && i / 8 <= ph; ++i)
        {
            widgets.add(new PageSlotWidget(manager, i, i % 8 * 18, i / 8 * 18 + this.getPageYOffset()));
        }
    }
}
