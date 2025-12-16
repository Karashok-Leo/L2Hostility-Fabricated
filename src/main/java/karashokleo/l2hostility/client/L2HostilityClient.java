package karashokleo.l2hostility.client;

import dev.xkmc.l2tabs.tabs.core.TabToken;
import dev.xkmc.l2tabs.tabs.inventory.InvTabData;
import dev.xkmc.l2tabs.tabs.inventory.TabRegistry;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.item.trinket.misc.PocketOfRestoration;
import karashokleo.l2hostility.content.screen.equipment.EquipmentScreen;
import karashokleo.l2hostility.content.screen.tab.DifficultyOverlay;
import karashokleo.l2hostility.content.screen.tab.DifficultyTab;
import karashokleo.l2hostility.init.*;
import karashokleo.leobrary.gui.api.GuiOverlayRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ShulkerBulletEntityRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class L2HostilityClient implements ClientModInitializer
{
    public static TabToken<InvTabData, DifficultyTab> TAB_DIFFICULTY;

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

    @Override
    public void onInitializeClient()
    {
        LHParticles.registerClient();
        LHNetworking.initClient();
        ClientEvents.register();

        EntityRendererRegistry.register(LHEntities.HOSTILITY_BULLET, ShulkerBulletEntityRenderer::new);
        EntityRendererRegistry.register(LHEntities.HOSTILITY_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 3.0f, true));
        EntityRendererRegistry.register(LHEntities.SOUL_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 1, true));
        EntityRendererRegistry.register(LHEntities.STRONG_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 1, true));
        EntityRendererRegistry.register(LHEntities.BLACK_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 1, true));
        EntityRendererRegistry.register(LHEntities.WITCH_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 1, true));
        EntityRendererRegistry.register(LHEntities.ETERNAL_WITCH_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 1, true));

        GuiOverlayRegistry.registerLayer(10, new DifficultyOverlay());

        BlockRenderLayerMap.INSTANCE.putBlock(LHBlocks.SPAWNER.block(), RenderLayer.getCutout());

        HandledScreens.register(LHMiscs.EQUIPMENTS, EquipmentScreen::new);
        TAB_DIFFICULTY = TabRegistry.GROUP.registerTab(5000, DifficultyTab::new, () -> Items.ZOMBIE_HEAD, LHTexts.INFO_TAB_TITLE.get());

        ModelPredicateProviderRegistry.register(TrinketItems.RESTORATION, L2Hostility.id("filled"), (stack, world, entity, seed) -> stack.getSubNbt(PocketOfRestoration.ROOT) == null ? 0 : 1);

        BuiltinItemRendererRegistry.INSTANCE.register(ComplementItems.FORCE_FIELD, ForceFieldRenderer.INSTANCE);
    }
}