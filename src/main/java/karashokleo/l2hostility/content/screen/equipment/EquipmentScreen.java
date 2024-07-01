package karashokleo.l2hostility.content.screen.equipment;

import dev.xkmc.l2tabs.lib.menu.BaseInventoryScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class EquipmentScreen extends BaseInventoryScreen<EquipmentScreenHandler>
{
    public EquipmentScreen(EquipmentScreenHandler cont, PlayerInventory plInv, Text title)
    {
        super(cont, plInv, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY)
    {
        var sr = handler.sprite.get().getRenderer(this);
        sr.start(context);
        if (handler.getAsPredSlot("hand", 0, 1).getStack().isEmpty())
            sr.draw(context, "hand", "altas_shield", 1, 19);
        if (handler.getAsPredSlot("armor", 0, 0).getStack().isEmpty())
            sr.draw(context, "armor", "altas_helmet", 1, 1);
        if (handler.getAsPredSlot("armor", 0, 1).getStack().isEmpty())
            sr.draw(context, "armor", "altas_chestplate", 1, 1 + 18);
        if (handler.getAsPredSlot("armor", 0, 2).getStack().isEmpty())
            sr.draw(context, "armor", "altas_leggings", 1, 1 + 18 * 2);
        if (handler.getAsPredSlot("armor", 0, 3).getStack().isEmpty())
            sr.draw(context, "armor", "altas_boots", 1, 1 + 18 * 3);
    }
}
