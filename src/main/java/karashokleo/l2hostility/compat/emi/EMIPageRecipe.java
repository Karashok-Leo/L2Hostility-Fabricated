package karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;

import java.util.List;

public interface EMIPageRecipe extends EmiRecipe
{
    int BUTTON_SIZE = 12;
    int SLOT_SIZE = 18;
    int SLOTS_PER_ROW = 8;

    List<EmiStack> getStacks();

    default int getPageYOffset()
    {
        return 75;
    }

    @Override
    default int getDisplayHeight()
    {
        return ((this.getStacks().size() - 1) / SLOTS_PER_ROW + 1) * SLOT_SIZE + this.getPageYOffset();
    }

    @Override
    default int getDisplayWidth()
    {
        return 144;
    }

    @Override
    default boolean supportsRecipeTree()
    {
        return false;
    }

    @Override
    default void addWidgets(WidgetHolder widgets)
    {
        List<EmiStack> stacks = this.getStacks();
        int ph = (widgets.getHeight() - getPageYOffset() - 6) / SLOT_SIZE;
        int pageSize = (ph + 1) * SLOTS_PER_ROW;
        PageManager<EmiStack> manager = new PageManager<>(stacks, pageSize);
        if (pageSize < stacks.size())
        {
            final int offset = 2;
            widgets.addButton(
                offset, offset,
                BUTTON_SIZE, BUTTON_SIZE,
                0, 0,
                () -> true,
                (mouseX, mouseY, button) -> manager.scroll(-1)
            );
            widgets.addButton(
                widgets.getWidth() - offset - BUTTON_SIZE, offset,
                BUTTON_SIZE, BUTTON_SIZE,
                BUTTON_SIZE, 0,
                () -> true,
                (mouseX, mouseY, button) -> manager.scroll(1)
            );
        }

        for (int i = 0; i < stacks.size() && i / SLOTS_PER_ROW <= ph; ++i)
        {
            widgets.add(new PageSlotWidget(manager, i, i % SLOTS_PER_ROW * SLOT_SIZE, i / SLOTS_PER_ROW * SLOT_SIZE + this.getPageYOffset()));
        }
    }
}
