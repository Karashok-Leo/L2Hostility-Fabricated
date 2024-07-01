package karashokleo.l2hostility.content.screen.tab;

import dev.xkmc.l2tabs.tabs.core.TabBase;
import dev.xkmc.l2tabs.tabs.core.TabManager;
import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class DifficultyTab extends TabBase<InvTabData, DifficultyTab>
{
    public DifficultyTab(int index, TabToken<InvTabData, DifficultyTab> token, TabManager<InvTabData> manager, ItemStack stack, Text title)
    {
        super(index, token, manager, stack, title);
    }

    @Override
    public void onTabClicked()
    {
        MinecraftClient.getInstance().setScreen(new DifficultyScreen(this.getMessage()));
    }
}
