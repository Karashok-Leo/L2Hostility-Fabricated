package karashokleo.l2hostility.content.screen.tab;

import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.leobrary.gui.api.overlay.InfoSideBar;
import karashokleo.leobrary.gui.api.overlay.SideBar;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DifficultyOverlay extends InfoSideBar<SideBar.IntSignature>
{
    public DifficultyOverlay()
    {
        super(40, 3);
    }

    @Override
    protected List<Text> getText()
    {
        List<Pair<Text, Supplier<List<Text>>>> comp = new ArrayList<>();
        DifficultyScreen.addDifficultyInfo(comp,
            Formatting.RED, Formatting.GREEN, Formatting.GOLD);
        return comp.stream().map(Pair::getLeft).toList();
    }

    @Override
    protected boolean isOnHold()
    {
        return true;
    }

    @Override
    public IntSignature getSignature()
    {
        return new IntSignature(0);
    }

    @Override
    public boolean isScreenOn()
    {
        if (L2HostilityClient.getClient().currentScreen != null)
        {
            return false;
        }
        ClientPlayerEntity player = L2HostilityClient.getClientPlayer();
        if (player == null)
        {
            return false;
        }
        return player.getMainHandStack().isOf(MiscItems.DETECTOR) ||
            player.getOffHandStack().isOf(MiscItems.DETECTOR);
    }
}