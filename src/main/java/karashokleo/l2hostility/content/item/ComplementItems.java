package karashokleo.l2hostility.content.item;

import karashokleo.l2hostility.content.item.complements.*;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHCplTexts;
import karashokleo.l2hostility.init.LHItems;
import karashokleo.l2hostility.init.LHTexts;
import karashokleo.leobrary.datagen.object.MaterialSet;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.EntityType;
import net.minecraft.item.FoodComponent;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Rarity;

public class ComplementItems
{
    public static TooltipItem WIND_BOTTLE;
    public static TooltipItem VOID_EYE;
    public static TooltipItem CAPTURED_WIND;
    public static TooltipItem CAPTURED_BULLET;
    public static TooltipItem SUN_MEMBRANE;
    public static TooltipItem EXPLOSION_SHARD;
    public static TooltipItem HARD_ICE;
    public static TooltipItem SOUL_FLAME;
    public static TooltipItem STORM_CORE;
    public static TooltipItem BLACKSTONE_CORE;
    public static TooltipItem RESONANT_FEATHER;
    public static TooltipItem SPACE_SHARD;
    public static TooltipItem WARDEN_BONE_SHARD;
    public static TooltipItem GUARDIAN_EYE;
    public static TooltipItem FORCE_FIELD;
    public static TransformItem GUARDIAN_RUNE;
    public static TransformItem PIGLIN_RUNE;
    public static BurntItem EMERALD;
    public static BurntItem CURSED_DROPLET;
    public static BurntItem LIFE_ESSENCE;

    public static MaterialSet TOTEMIC_GOLD;
    public static MaterialSet POSEIDITE;
    public static MaterialSet SHULKERATE;
    public static MaterialSet SCULKIUM;
    public static MaterialSet ETERNIUM;

    public static void register()
    {
        WIND_BOTTLE = LHItems.Entry.of(
                "wind_capture_bottle",
                new WindBottle(
                    new FabricItemSettings().rarity(Rarity.COMMON),
                    LHCplTexts.WIND_BOTTLE::get
                )
            )
            .addModel()
            .addEN("Wind Capturing Bottle")
            .addZH("捕风瓶")
            .register();

        VOID_EYE = LHItems.Entry.of(
                "void_eye",
                new WindBottle(
                    new FabricItemSettings().rarity(Rarity.EPIC),
                    () -> LHCplTexts.VOID_EYE.get(LHConfig.common().complements.materials.belowVoid)
                )
            )
            .addModel()
            .addEN()
            .addZH("虚空之眼")
            .register();

        CAPTURED_WIND = LHItems.Entry.of(
                "captured_wind",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    () -> LHCplTexts.CAPTURED_WIND.get(LHConfig.common().complements.materials.windSpeed * 20)
                )
            )
            .addModel()
            .addEN("Essence of Wind")
            .addZH("千风精华")
            .register();

        CAPTURED_BULLET = LHItems.Entry.of(
                "captured_shulker_bullet",
                new WindBottle(
                    new FabricItemSettings().rarity(Rarity.UNCOMMON),
                    LHCplTexts.CAPTURED_BULLET::get
                )
            )
            .addModel()
            .addEN("Shulker Bullet in Bottle")
            .addZH("瓶中潜影弹")
            .register();

        SUN_MEMBRANE = LHItems.Entry.of(
                "sun_membrane",
                new RefinedRadianceItem(
                    new FabricItemSettings().rarity(Rarity.EPIC),
                    () -> LHCplTexts.SUN_MEMBRANE.get(LHConfig.common().complements.materials.phantomHeight)
                )
            )
            .addModel()
            .addEN("Membrane of the Sun")
            .addZH("逐日之翼")
            .register();

        EXPLOSION_SHARD = LHItems.Entry.of(
                "explosion_shard",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.UNCOMMON),
                    () -> LHCplTexts.EXPLOSION_SHARD.get(LHConfig.common().complements.materials.explosionDamage)
                )
            )
            .addModel()
            .addEN("Remnant Shard of Explosion")
            .addZH("破灭残片")
            .register();

        HARD_ICE = LHItems.Entry.of(
                "hard_ice",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.UNCOMMON),
                    LHCplTexts.HARD_ICE::get
                )
            )
            .addModel()
            .addEN("Unliving Ice")
            .addZH("告死寒霜")
            .register();

        SOUL_FLAME = LHItems.Entry.of(
                "soul_flame",
                new RefinedRadianceItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    LHCplTexts.SOUL_FLAME::get
                )
            )
            .addModel()
            .addEN()
            .addZH("灵魂火种")
            .register();

        STORM_CORE = LHItems.Entry.of(
                "storm_core",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.UNCOMMON),
                    LHCplTexts.STORM_CORE::get
                )
            )
            .addModel()
            .addEN("Crystal of Storm")
            .addZH("烈空结晶")
            .register();

        BLACKSTONE_CORE = LHItems.Entry.of(
                "blackstone_core",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    LHCplTexts.BLACKSTONE_CORE::get
                )
            )
            .addModel()
            .addEN()
            .addZH("黑石核心")
            .register();

        RESONANT_FEATHER = LHItems.Entry.of(
                "resonant_feather",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.EPIC),
                    LHCplTexts.RESONANT_FEATHER::get
                )
            )
            .addModel()
            .addEN()
            .addZH("共振之羽")
            .register();

        SPACE_SHARD = LHItems.Entry.of(
                "space_shard",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.EPIC),
                    () -> LHConfig.common().complements.materials.enableSpaceShard ?
                        LHCplTexts.SPACE_SHARD.get(LHConfig.common().complements.materials.spaceDamage) :
                        LHTexts.BANNED.get()
                )
            )
            .addModel()
            .addEN("Space Shard (Creative)")
            .addZH("空间碎片(创造)")
            .register();

        WARDEN_BONE_SHARD = LHItems.Entry.of(
                "warden_bone_shard",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    LHCplTexts.WARDEN_BONE_SHARD::get
                )
            )
            .addModel()
            .addEN()
            .addZH("强化骨片")
            .register();

        GUARDIAN_EYE = LHItems.Entry.of(
                "guardian_eye",
                new TooltipItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    LHCplTexts.GUARDIAN_EYE::get
                )
            )
            .addModel()
            .addEN("Eye of Elder Guardian")
            .addZH("守卫者之眼")
            .register();

        FORCE_FIELD = LHItems.Entry.of(
                "force_field",
                new TooltipItem(
                    new FabricItemSettings().fireproof().rarity(Rarity.EPIC),
                    LHCplTexts.FORCE_FIELD::get
                )
            )
            .addEN("Wither Force Field")
            .addZH("守护力场")
            .register();

        GUARDIAN_RUNE = LHItems.Entry.of(
                "guardian_rune",
                new TransformItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    LHCplTexts.GUARDIAN_RUNE::get,
                    () -> EntityType.GUARDIAN,
                    () -> EntityType.ELDER_GUARDIAN
                )
            )
            .addModel()
            .addEN("Rune of Guardian")
            .addZH("守卫者强化符文")
            .register();

        PIGLIN_RUNE = LHItems.Entry.of(
                "piglin_rune",
                new TransformItem(
                    new FabricItemSettings().rarity(Rarity.RARE),
                    LHCplTexts.PIGLIN_RUNE::get,
                    () -> EntityType.PIGLIN,
                    () -> EntityType.PIGLIN_BRUTE
                )
            )
            .addModel()
            .addEN("Rune of Piglin")
            .addZH("猪灵强化符文")
            .register();

        EMERALD = LHItems.Entry.of(
                "heirophant_green",
                new BurntItem(
                    new FabricItemSettings().fireproof().rarity(Rarity.EPIC)
                )
            )
            .addModel()
            .addEN()
            .addZH("法皇之绿")
            .register();

        CURSED_DROPLET = LHItems.Entry.of(
                "cursed_droplet",
                new BurntItem(
                    new FabricItemSettings().fireproof().rarity(Rarity.RARE)
                )
            )
            .addModel()
            .addEN()
            .addZH("亡魂之泪")
            .register();

        LIFE_ESSENCE = LHItems.Entry.of(
                "life_essence",
                new BurntItem(
                    new FabricItemSettings()
                        .fireproof()
                        .rarity(Rarity.RARE)
                        .food(
                            new FoodComponent.Builder()
                                .hunger(20)
                                .saturationModifier(1.2F)
                                .alwaysEdible()
                                .snack()
                                .build()
                        )
                )
            )
            .addModel()
            .addEN("Essence of Life")
            .addZH("生命精华")
            .register();

        TOTEMIC_GOLD = MaterialEntry.of("totemic_gold", "生命")
            .ingotAndNugget(new FabricItemSettings().rarity(Rarity.UNCOMMON))
            .block(FabricBlockSettings.create().mapColor(MapColor.GOLD).instrument(Instrument.BELL).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.METAL))
            .buildBlock(blockBuilder -> blockBuilder.addLoot().addTag(BlockTags.NEEDS_STONE_TOOL, BlockTags.PICKAXE_MINEABLE))
            .register();

        POSEIDITE = MaterialEntry.of("poseidite", "海神")
            .ingotAndNugget(new FabricItemSettings().fireproof().rarity(Rarity.UNCOMMON))
            .block(FabricBlockSettings.create().mapColor(MapColor.WATER_BLUE).instrument(Instrument.BELL).requiresTool().strength(3.0F, 6.0F).sounds(BlockSoundGroup.METAL))
            .buildBlock(blockBuilder -> blockBuilder.addLoot().addTag(BlockTags.NEEDS_STONE_TOOL, BlockTags.PICKAXE_MINEABLE))
            .register();

        SHULKERATE = MaterialEntry.of("shulkerate", "潜影")
            .ingotAndNugget(new FabricItemSettings().rarity(Rarity.RARE))
            .block(FabricBlockSettings.create().mapColor(MapColor.PURPLE).requiresTool().strength(12.0F, 24.0F).sounds(BlockSoundGroup.METAL))
            .buildBlock(blockBuilder -> blockBuilder.addLoot().addTag(BlockTags.NEEDS_IRON_TOOL, BlockTags.PICKAXE_MINEABLE))
            .register();

        SCULKIUM = MaterialEntry.of("sculkium", "幽匿")
            .ingotAndNugget(new FabricItemSettings().rarity(Rarity.RARE))
            .block(FabricBlockSettings.create().mapColor(MapColor.BLACK).requiresTool().strength(12.0F, 24.0F).sounds(BlockSoundGroup.METAL))
            .buildBlock(blockBuilder -> blockBuilder.addLoot().addTag(BlockTags.NEEDS_IRON_TOOL, BlockTags.PICKAXE_MINEABLE))
            .register();

        ETERNIUM = MaterialEntry.of("eternium", "永恒")
            .ingotAndNugget(new FabricItemSettings().fireproof().rarity(Rarity.EPIC))
            .block(FabricBlockSettings.create().mapColor(MapColor.LIGHT_BLUE).instrument(Instrument.BASEDRUM).requiresTool().strength(50.0F, 1200.0F).sounds(BlockSoundGroup.METAL))
            .buildBlock(blockBuilder -> blockBuilder.addLoot().addTag(BlockTags.NEEDS_DIAMOND_TOOL, BlockTags.PICKAXE_MINEABLE))
            .register();
    }
}
