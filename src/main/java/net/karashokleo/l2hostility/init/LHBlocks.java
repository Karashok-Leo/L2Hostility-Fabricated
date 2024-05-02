package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHBlocks
{
    public static Block BURST_SPAWNER;
    public static BlockItem SPAWNER;

    public static void register()
    {
        BURST_SPAWNER = new Block(FabricBlockSettings.copyOf(Blocks.SPAWNER).strength(50F, 1200F));
        SPAWNER = new BlockItem(BURST_SPAWNER, new FabricItemSettings());
        Registry.register(Registries.BLOCK, L2Hostility.id("hostility_spawner"), BURST_SPAWNER);
        Registry.register(Registries.ITEM, L2Hostility.id("hostility_spawner"), SPAWNER);
        LHData.MODELS.addBlock(generator -> generator.registerSimpleCubeAll(BURST_SPAWNER));
        LHMiscs.GROUP.add(SPAWNER);
    }
}
