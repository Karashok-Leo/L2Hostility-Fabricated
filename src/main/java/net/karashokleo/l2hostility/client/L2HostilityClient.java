package net.karashokleo.l2hostility.client;

import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import dev.xkmc.l2tabs.tabs.inventory.TabRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.item.trinket.misc.PocketOfRestoration;
import net.karashokleo.l2hostility.content.screen.equipment.EquipmentScreen;
import net.karashokleo.l2hostility.content.screen.tab.DifficultyTab;
import net.karashokleo.l2hostility.init.LHItems;
import net.karashokleo.l2hostility.init.LHMiscs;
import net.karashokleo.l2hostility.init.LHNetworking;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class L2HostilityClient implements ClientModInitializer
{
    public static TabToken<InvTabData, DifficultyTab> TAB_DIFFICULTY;

    @Override
    public void onInitializeClient()
    {
        LHNetworking.initClient();
        ClientEvents.register();
        HandledScreens.register(LHMiscs.EQUIPMENTS, EquipmentScreen::new);
        TAB_DIFFICULTY = TabRegistry.GROUP.registerTab(5000, DifficultyTab::new,
                () -> Items.ZOMBIE_HEAD, LHTexts.INFO_TAB_TITLE.get());

        ModelPredicateProviderRegistry.register(LHItems.RESTORATION, L2Hostility.id("filled"), (stack, world, entity, seed) -> stack.getSubNbt(PocketOfRestoration.ROOT) == null ? 0 : 1);
    }

    public static MinecraftClient getClient()
    {
        return MinecraftClient.getInstance();
    }

    @Nullable
    public static ClientPlayerEntity getClientPlayer()
    {
        return getClient().player;
    }

    @Nullable
    public static ClientWorld getClientWorld()
    {
        return getClient().world;
    }

//    @SubscribeEvent
//    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
//        event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "l2hostility", new DifficultyOverlay());
//    }
}