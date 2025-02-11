package karashokleo.l2hostility.data.generate;

import karashokleo.enchantment_infusion.api.recipe.EnchantmentIngredient;
import karashokleo.enchantment_infusion.api.util.EIRecipeUtil;
import karashokleo.enchantment_infusion.content.data.EnchantmentInfusionRecipeBuilder;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.recipe.BurntRecipeBuilder;
import karashokleo.l2hostility.init.LHBlocks;
import karashokleo.l2hostility.init.LHEnchantments;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.compat.patchouli.PatchouliHelper;
import karashokleo.leobrary.datagen.util.IdUtil;
import karashokleo.leobrary.datagen.util.RecipeTemplate;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("all")
public class RecipeProvider extends FabricRecipeProvider
{
    private static final IdUtil ID_UTIL = IdUtil.of(L2Hostility.MOD_ID);

    public RecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    private static void enchantmentAdd(Consumer<EnchantmentInfusionRecipeBuilder> consumer, Enchantment enchantment, int level, Consumer<RecipeJsonProvider> exporter)
    {
        EIRecipeUtil.add(
                consumer,
                enchantment,
                level,
                exporter,
                ID_UTIL.path(Objects.requireNonNull(Registries.ENCHANTMENT.getId(enchantment)).getPath() + "/" + level).get()
        );
    }

    private static void enchantmentSet(Consumer<EnchantmentInfusionRecipeBuilder> consumer, Enchantment enchantment, int level, Consumer<RecipeJsonProvider> exporter)
    {
        EIRecipeUtil.set(
                consumer,
                enchantment,
                level,
                exporter,
                ID_UTIL.path(Objects.requireNonNull(Registries.ENCHANTMENT.getId(enchantment)).getPath() + "/" + level).get()
        );
    }

    private static void convert(Consumer<RecipeJsonProvider> exporter, Item in, Item out, int count)
    {
        RecipeTemplate.unlock(new BurntRecipeBuilder(Ingredient.ofItems(in), out.getDefaultStack(), count)::criterion, in)
                .offerTo(exporter, ID_UTIL.path(in).path("_to_").path(out).get());
    }

    public static void recycle(Consumer<RecipeJsonProvider> exporter, TagKey<Item> source, Item result, float experience)
    {
        RecipeTemplate.unlock(CookingRecipeJsonBuilder.createBlasting(Ingredient.fromTag(source), RecipeCategory.MISC, result, experience, 200)::criterion, result)
                .offerTo(exporter, ID_UTIL.path(result).path("_recycle").get());
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {
        ID_UTIL.pushAndPop("complements/", () ->
        {
            ID_UTIL.pushAndPop("convert/", () ->
            {
                convert(exporter, Items.EMERALD, ComplementItems.EMERALD, 64 * 27 * 9);

                convert(exporter, Items.EMERALD_BLOCK, ComplementItems.EMERALD, 64 * 27);

                convert(exporter, Items.ROTTEN_FLESH, ComplementItems.CURSED_DROPLET, 64 * 27 * 9);

                convert(exporter, Items.SOUL_SAND, ComplementItems.CURSED_DROPLET, 64 * 27 * 16);

                convert(exporter, Items.SOUL_SOIL, ComplementItems.CURSED_DROPLET, 64 * 27 * 16);

                convert(exporter, Items.GHAST_TEAR, ComplementItems.CURSED_DROPLET, 64 * 9);

                convert(exporter, Items.NETHER_STAR, ComplementItems.CURSED_DROPLET, 64);

                convert(exporter, Items.COOKED_BEEF, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COOKED_CHICKEN, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COOKED_MUTTON, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COOKED_PORKCHOP, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COOKED_RABBIT, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COOKED_COD, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COOKED_SALMON, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.BEEF, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.CHICKEN, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.MUTTON, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.PORKCHOP, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.RABBIT, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.COD, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.SALMON, ComplementItems.LIFE_ESSENCE, 64 * 27 * 9);

                convert(exporter, Items.TOTEM_OF_UNDYING, ComplementItems.LIFE_ESSENCE, 64);
            });

            ID_UTIL.pushAndPop("craft/", () ->
            {
                RecipeTemplate.shapeless(ComplementItems.WIND_BOTTLE, 1, Items.GLASS_BOTTLE)
                        .input(Items.GLASS_BOTTLE)
                        .input(Items.PHANTOM_MEMBRANE)
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.WIND_BOTTLE).get());

//            RecipeTemplate.shaped( LCBlocks.ETERNAL_ANVIL, 1, ComplementItems.ETERNIUM.ingot())
//                    .pattern("AAA").pattern(" B ").pattern("BBB")
//                    .input('A', ComplementItems.ETERNIUM.getBlock())
//                    .input('B', ComplementItems.ETERNIUM.ingot())
//                    .offerTo(exporter, ID_UTIL.get(LCBlocks.ETERNAL_ANVIL.asItem()));

                RecipeTemplate.shaped(ComplementItems.ETERNIUM.nugget(), 1, ComplementItems.EXPLOSION_SHARD)
                        .pattern("3C4")
                        .pattern("BAB")
                        .pattern("1C2")
                        .input('A', ComplementItems.EXPLOSION_SHARD)
                        .input('B', Items.ANVIL)
                        .input('C', Items.ENDER_PEARL)
                        .input('1', EnchantmentIngredient.of(Enchantments.MENDING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.INFINITY, 1))
                        .input('3', EnchantmentIngredient.of(Enchantments.PROTECTION, 4))
                        .input('4', EnchantmentIngredient.of(Enchantments.UNBREAKING, 3))
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.ETERNIUM.nugget()).get());

                RecipeTemplate.blasting(exporter, Items.TOTEM_OF_UNDYING, ComplementItems.TOTEMIC_GOLD.ingot(), 1, ID_UTIL.get(ComplementItems.TOTEMIC_GOLD.ingot()));

                RecipeTemplate.blasting(exporter, Items.TRIDENT, ComplementItems.POSEIDITE.ingot(), 1, ID_UTIL.get(ComplementItems.POSEIDITE.ingot()));

                RecipeTemplate.shapeless(ComplementItems.SHULKERATE.ingot(), 1, ComplementItems.CAPTURED_BULLET)
                        .input(Items.SHULKER_SHELL, 2)
                        .input(ComplementItems.CAPTURED_BULLET)
                        .input(Items.IRON_INGOT)
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.SHULKERATE.ingot()).get());

                RecipeTemplate.shapeless(ComplementItems.SCULKIUM.ingot(), 2, ComplementItems.WARDEN_BONE_SHARD)
                        .input(Items.ECHO_SHARD)
                        .input(ComplementItems.WARDEN_BONE_SHARD, 2)
                        .input(Items.COPPER_INGOT)
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.SCULKIUM.ingot()).get());

                RecipeTemplate.shaped(ComplementItems.GUARDIAN_RUNE, 1, ComplementItems.CURSED_DROPLET)
                        .pattern("DAD")
                        .pattern("CBC")
                        .pattern("DCD")
                        .input('A', ComplementItems.CURSED_DROPLET)
                        .input('B', ComplementItems.POSEIDITE.ingot())
                        .input('C', Items.NAUTILUS_SHELL)
                        .input('D', Items.PRISMARINE_SHARD)
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.GUARDIAN_RUNE).get());

                RecipeTemplate.shaped(ComplementItems.PIGLIN_RUNE, 1, ComplementItems.CURSED_DROPLET)
                        .pattern("DAD")
                        .pattern("CBC")
                        .pattern("DCD")
                        .input('A', ComplementItems.CURSED_DROPLET)
                        .input('B', Items.NETHER_STAR)
                        .input('C', Items.NETHERITE_SCRAP)
                        .input('D', Items.BLACKSTONE)
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.PIGLIN_RUNE).get());

//            RecipeTemplate.shapeless( ComplementItems.FRAGILE_WARP_STONE, 1, ComplementItems.VOID_EYE)
//                    .input(Items.ECHO_SHARD).input(ComplementItems.VOID_EYE, 1).input(Items.ENDER_PEARL)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.FRAGILE_WARP_STONE));

//            smithing(exporter, ComplementItems.FRAGILE_WARP_STONE, ComplementItems.SHULKERATE.ingot(), ComplementItems.REINFORCED_WARP_STONE);

//            RecipeTemplate.shaped( ComplementItems.TOTEM_OF_DREAM, 1, ComplementItems.FRAGILE_WARP_STONE)
//                    .pattern("CAC").pattern("ABA").pattern("CAC")
//                    .input('A', ComplementItems.TOTEMIC_GOLD.ingot())
//                    .input('B', ComplementItems.FRAGILE_WARP_STONE)
//                    .input('C', Items.ENDER_PEARL)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.TOTEM_OF_DREAM));
//
//            RecipeTemplate.shaped( ComplementItems.TOTEM_OF_THE_SEA, 1, ComplementItems.POSEIDITE.ingot())
//                    .pattern("ACA").pattern("ABA").pattern("ACA")
//                    .input('A', ComplementItems.TOTEMIC_GOLD.ingot())
//                    .input('B', Items.HEART_OF_THE_SEA)
//                    .input('C', ComplementItems.POSEIDITE.ingot())
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.TOTEM_OF_THE_SEA));

//            RecipeTemplate.shapeless( ComplementItems.SOUL_CHARGE, 2, Items.BLAZE_POWDER)
//                    .input(ItemTags.SOUL_FIRE_BASE_BLOCKS)
//                    .input(Items.BLAZE_POWDER, 2)
//                    .input(Items.GUNPOWDER, 2)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.SOUL_CHARGE));
//
//            RecipeTemplate.shapeless( ComplementItems.BLACK_CHARGE, 2, Items.BLAZE_POWDER)
//                    .input(Items.BLACKSTONE)
//                    .input(Items.BLAZE_POWDER, 2)
//                    .input(Items.GUNPOWDER, 2)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.BLACK_CHARGE));
//
//            RecipeTemplate.shapeless( ComplementItems.STRONG_CHARGE, 2, Items.BLAZE_POWDER)
//                    .input(Ingredient.of(Items.COAL, Items.CHARCOAL))
//                    .input(Items.BLAZE_POWDER, 2)
//                    .input(Items.GUNPOWDER, 2)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.STRONG_CHARGE));
//
//            RecipeTemplate.shaped( ComplementItems.TOTEMIC_CARROT, 1, ComplementItems.TOTEMIC_GOLD.ingot())
//                    .pattern("AAA").pattern("ABA").pattern("AAA")
//                    .input('A', ComplementItems.TOTEMIC_GOLD.nugget())
//                    .input('B', Items.CARROT)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.TOTEMIC_CARROT));
//
//            RecipeTemplate.shaped( ComplementItems.ENCHANT_TOTEMIC_CARROT, 1, ComplementItems.TOTEMIC_GOLD.ingot())
//                    .pattern("ACA").pattern("ABA").pattern("AAA")
//                    .input('A', ComplementItems.TOTEMIC_GOLD.ingot())
//                    .input('B', Items.CARROT)
//                    .input('C', ComplementItems.LIFE_ESSENCE)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.ENCHANT_TOTEMIC_CARROT));
//
//            RecipeTemplate.shaped( ComplementItems.TOTEMIC_APPLE, 1, ComplementItems.TOTEMIC_GOLD.ingot())
//                    .pattern("ACA").pattern("ABA").pattern("AAA")
//                    .input('A', ComplementItems.TOTEMIC_GOLD.ingot())
//                    .input('B', Items.APPLE)
//                    .input('C', ComplementItems.LIFE_ESSENCE)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.TOTEMIC_APPLE));
//
//            RecipeTemplate.shaped( ComplementItems.ENCHANTED_TOTEMIC_APPLE, 1, ComplementItems.TOTEMIC_GOLD.ingot())
//                    .pattern("ACA").pattern("CBC").pattern("ACA")
//                    .input('A', ComplementItems.TOTEMIC_GOLD.getBlock())
//                    .input('B', Items.APPLE)
//                    .input('C', ComplementItems.LIFE_ESSENCE)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.ENCHANTED_TOTEMIC_APPLE));

                RecipeTemplate.shapeless(ComplementItems.WARDEN_BONE_SHARD, 1, ComplementItems.RESONANT_FEATHER)
                        .input(LHTags.DELICATE_BONE)
                        .input(ComplementItems.RESONANT_FEATHER)
                        .offerTo(exporter, ID_UTIL.path(ComplementItems.WARDEN_BONE_SHARD).get());

//            RecipeTemplate.shaped( ComplementItems.SONIC_SHOOTER, 1, ComplementItems.SCULKIUM.ingot())
//                    .pattern("ABB").pattern("III").pattern("IC ")
//                    .input('I', ComplementItems.SCULKIUM.ingot())
//                    .input('A', ComplementItems.VOID_EYE)
//                    .input('B', ComplementItems.RESONANT_FEATHER)
//                    .input('C', ComplementItems.EXPLOSION_SHARD)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.SONIC_SHOOTER));
//
//            RecipeTemplate.shaped( ComplementItems.HELLFIRE_WAND, 1, ComplementItems.SUN_MEMBRANE)
//                    .pattern(" FM").pattern(" CF").pattern("C  ")
//                    .input('F', ComplementItems.SOUL_FLAME)
//                    .input('M', ComplementItems.SUN_MEMBRANE)
//                    .input('C', ComplementItems.EXPLOSION_SHARD)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.HELLFIRE_WAND));
//
//            RecipeTemplate.shaped( ComplementItems.WINTERSTORM_WAND, 1, ComplementItems.HARD_ICE)
//                    .pattern(" FM").pattern(" CF").pattern("C  ")
//                    .input('F', ComplementItems.HARD_ICE)
//                    .input('M', ComplementItems.STORM_CORE)
//                    .input('C', Items.STICK)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.WINTERSTORM_WAND));
//
//
//            RecipeTemplate.shaped( ComplementItems.DIFFUSION_WAND, 1, ComplementItems.STORM_CORE)
//                    .pattern(" FM").pattern(" CF").pattern("C  ")
//                    .input('F', Items.DIAMOND)
//                    .input('M', ComplementItems.STORM_CORE)
//                    .input('C', Items.STICK)
//                    .offerTo(exporter, ID_UTIL.get(ComplementItems.DIFFUSION_WAND));
            });

            ID_UTIL.pushAndPop("vanilla/", () ->
            {
                RecipeTemplate.shapeless(Items.ECHO_SHARD, 1, ComplementItems.RESONANT_FEATHER)
                        .input(ComplementItems.RESONANT_FEATHER)
                        .input(Items.AMETHYST_SHARD)
                        .input(Items.SCULK, 4)
                        .offerTo(exporter, ID_UTIL.path(Items.ECHO_SHARD).get());

                RecipeTemplate.shaped(Items.ELYTRA, 1, ComplementItems.SUN_MEMBRANE)
                        .pattern("ABA")
                        .pattern("CEC")
                        .pattern("D D")
                        .input('A', ComplementItems.EXPLOSION_SHARD)
                        .input('B', ComplementItems.CAPTURED_WIND)
                        .input('C', ComplementItems.SUN_MEMBRANE)
                        .input('D', ComplementItems.RESONANT_FEATHER)
                        .input('E', ComplementItems.STORM_CORE)
                        .offerTo(exporter, ID_UTIL.path(Items.ELYTRA).get());

                RecipeTemplate.shaped(Items.ANCIENT_DEBRIS, 1, ComplementItems.EXPLOSION_SHARD)
                        .pattern("ABA")
                        .pattern("ACA")
                        .pattern("ADA")
                        .input('A', ComplementItems.EXPLOSION_SHARD)
                        .input('B', Items.NETHER_STAR)
                        .input('C', Items.CRYING_OBSIDIAN)
                        .input('D', ComplementItems.FORCE_FIELD)
                        .offerTo(exporter, ID_UTIL.path(Items.ANCIENT_DEBRIS).get());

                RecipeTemplate.shaped(Items.GILDED_BLACKSTONE, 1, ComplementItems.BLACKSTONE_CORE)
                        .pattern("ABA")
                        .pattern("BCB")
                        .pattern("ABA")
                        .input('A', Items.BLACKSTONE)
                        .input('B', Items.GOLD_INGOT)
                        .input('C', ComplementItems.BLACKSTONE_CORE)
                        .offerTo(exporter, ID_UTIL.path(Items.GILDED_BLACKSTONE).get());

                RecipeTemplate.shaped(Items.REINFORCED_DEEPSLATE, 1, ComplementItems.WARDEN_BONE_SHARD)
                        .pattern(" B ")
                        .pattern("BAB")
                        .pattern(" B ")
                        .input('A', Items.DEEPSLATE)
                        .input('B', ComplementItems.WARDEN_BONE_SHARD)
                        .offerTo(exporter, ID_UTIL.path(Items.REINFORCED_DEEPSLATE).get());

                RecipeTemplate.shaped(Items.HEART_OF_THE_SEA, 1, ComplementItems.GUARDIAN_EYE)
                        .pattern("ABA")
                        .pattern("BCB")
                        .pattern("ABA")
                        .input('A', Items.PRISMARINE_SHARD)
                        .input('B', Items.PRISMARINE_CRYSTALS)
                        .input('C', ComplementItems.GUARDIAN_EYE)
                        .offerTo(exporter, ID_UTIL.path(Items.HEART_OF_THE_SEA).get());

                RecipeTemplate.shaped(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1, ComplementItems.BLACKSTONE_CORE)
                        .pattern("BAB")
                        .pattern("BCB")
                        .pattern("BBB")
                        .input('B', Items.DIAMOND)
                        .input('C', Items.NETHERRACK)
                        .input('A', ComplementItems.BLACKSTONE_CORE)
                        .offerTo(exporter, ID_UTIL.path(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE).get());
            });

            ID_UTIL.pushAndPop("eggs/", () ->
            {
                /*
                zombie, husk, drowned, zombified piglin, skeleton, stray, wither skeleton, phantom, ghast
                */
                ID_UTIL.pushAndPop("undead/", () ->
                {
                    RecipeTemplate.shaped(Items.ZOMBIE_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("AAA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.ROTTEN_FLESH)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .offerTo(exporter, ID_UTIL.path(Items.ZOMBIE_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.HUSK_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("ADA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.ROTTEN_FLESH)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .input('D', Items.SAND)
                            .offerTo(exporter, ID_UTIL.path(Items.HUSK_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.DROWNED_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("ADA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.ROTTEN_FLESH)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .input('D', Items.KELP)
                            .offerTo(exporter, ID_UTIL.path(Items.DROWNED_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("ADA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.ROTTEN_FLESH)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .input('D', Items.GOLD_INGOT)
                            .offerTo(exporter, ID_UTIL.path(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.SKELETON_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("AAA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.BONE)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .offerTo(exporter, ID_UTIL.path(Items.SKELETON_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.STRAY_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("ADA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.BONE)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .input('D', Items.SNOWBALL)
                            .offerTo(exporter, ID_UTIL.path(Items.STRAY_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.WITHER_SKELETON_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("ADA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.BONE)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .input('D', Items.WITHER_SKELETON_SKULL)
                            .offerTo(exporter, ID_UTIL.path(Items.WITHER_SKELETON_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.PHANTOM_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("AAA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.PHANTOM_MEMBRANE)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .offerTo(exporter, ID_UTIL.path(Items.PHANTOM_SPAWN_EGG).get());

                    RecipeTemplate.shaped(Items.GHAST_SPAWN_EGG, 1, ComplementItems.CURSED_DROPLET)
                            .pattern("AAA")
                            .pattern("ABA")
                            .pattern("ACA")
                            .input('A', Items.GHAST_TEAR)
                            .input('B', ComplementItems.CURSED_DROPLET)
                            .input('C', Items.EGG)
                            .offerTo(exporter, ID_UTIL.path(Items.GHAST_SPAWN_EGG).get());

                });
                 /*
                 allay
                 blaze
                 cave spider
                 creeper
                 elder guardian
                 enderman
                 endermite
                 evoker
                 guardian
                 hoglin
                 magma cube
                 piglin
                 piglin brute
                 */

                /*pig, cow, mooshroom, chicken, rabbit, bee, cod, salmon, tropical fish, squid, glow squid, frog, turtle*/
                ID_UTIL.pushAndPop("passive/", () ->
                {
                    ID_UTIL.pushAndPop("animal/", () ->
                    {
                        RecipeTemplate.shaped(Items.PIG_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.PORKCHOP)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.PIG_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.COW_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.BEEF)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.COW_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.MOOSHROOM_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.RED_MUSHROOM)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.COW_SPAWN_EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.MOOSHROOM_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.SHEEP_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.MUTTON)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.SHEEP_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.CHICKEN_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.CHICKEN)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.CHICKEN_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.RABBIT_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.RABBIT)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.RABBIT_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.BEE_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.HONEYCOMB)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.BEE_SPAWN_EGG).get());
                    });

                    ID_UTIL.pushAndPop("aquatic_spawn/", () ->
                    {
                        RecipeTemplate.shaped(Items.COD_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.COD)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.COD_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.SALMON_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.SALMON)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.SALMON_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.TROPICAL_FISH_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.TROPICAL_FISH)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.TROPICAL_FISH_SPAWN_EGG).get());


                        RecipeTemplate.shaped(Items.SQUID_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.INK_SAC)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.SQUID_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.GLOW_SQUID_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.GLOW_INK_SAC)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.GLOW_SQUID_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.FROG_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern(" A ")
                                .pattern("LBL")
                                .pattern(" C ")
                                .input('A', Items.FROGSPAWN)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.FROG_SPAWN_EGG).get());

                        RecipeTemplate.shaped(Items.TURTLE_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("AAA")
                                .pattern("LBL")
                                .pattern("ACA")
                                .input('A', Items.SCUTE)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.TURTLE_SPAWN_EGG).get());
                    });

                    ID_UTIL.pushAndPop("aquatic_alternate/", () ->
                    {
                        RecipeTemplate.shaped(Items.COD_BUCKET, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern(" A ")
                                .pattern("LBL")
                                .pattern(" C ")
                                .input('A', Items.COD)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.BUCKET)
                                .offerTo(exporter, ID_UTIL.path(Items.COD_BUCKET).get());

                        RecipeTemplate.shaped(Items.SALMON_BUCKET, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern(" A ")
                                .pattern("LBL")
                                .pattern(" C ")
                                .input('A', Items.SALMON)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.BUCKET)
                                .offerTo(exporter, ID_UTIL.path(Items.SALMON_BUCKET).get());

                        RecipeTemplate.shaped(Items.TROPICAL_FISH_BUCKET, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern(" A ")
                                .pattern("LBL")
                                .pattern(" C ")
                                .input('A', Items.TROPICAL_FISH)
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.BUCKET)
                                .offerTo(exporter, ID_UTIL.path(Items.TROPICAL_FISH_BUCKET).get());

                        RecipeTemplate.shaped(Items.TURTLE_SPAWN_EGG, 1, ComplementItems.LIFE_ESSENCE)
                                .pattern("LBL")
                                .pattern(" C ")
                                .input('B', ComplementItems.LIFE_ESSENCE)
                                .input('L', ComplementItems.TOTEMIC_GOLD.ingot())
                                .input('C', Items.TURTLE_EGG)
                                .offerTo(exporter, ID_UTIL.path(Items.TURTLE_SPAWN_EGG).get());
                    });

                    // panda
                    // polar bear
                    // fox
                    // cat
                    // dog
                    // goat
                    // horse
                    // donkey
                    // mule
                    // llama
                    // ocelot
                    // parrot
                    // panda
                    // dolphin
                    // bat
                    // axolotl
                });
            });

            ID_UTIL.pushAndPop("storage/", () ->
            {
                RecipeTemplate.storage(exporter, ComplementItems.TOTEMIC_GOLD, ID_UTIL);
                RecipeTemplate.storage(exporter, ComplementItems.POSEIDITE, ID_UTIL);
                RecipeTemplate.storage(exporter, ComplementItems.SHULKERATE, ID_UTIL);
                RecipeTemplate.storage(exporter, ComplementItems.SCULKIUM, ID_UTIL);
                RecipeTemplate.storage(exporter, ComplementItems.ETERNIUM, ID_UTIL);
            });
        });

        ID_UTIL.pushAndPop("consumable/", () ->
        {
            RecipeTemplate.shapeless(ConsumableItems.BOTTLE_SANITY, 3, ConsumableItems.HOSTILITY_ORB)
                    .input(ConsumableItems.HOSTILITY_ORB)
                    .input(Items.GLASS_BOTTLE, 3)
                    .offerTo(exporter, ID_UTIL.path(ConsumableItems.BOTTLE_SANITY).path("_craft").get());

            RecipeTemplate.shapeless(ConsumableItems.BOTTLE_SANITY, 3, ComplementItems.LIFE_ESSENCE)
                    .input(ConsumableItems.BOTTLE_SANITY)
                    .input(ComplementItems.LIFE_ESSENCE)
                    .input(Items.GLASS_BOTTLE, 3)
                    .offerTo(exporter, ID_UTIL.path(ConsumableItems.BOTTLE_SANITY).path("_renew").get());

            RecipeTemplate.shapeless(ConsumableItems.BOTTLE_CURSE, 3, ComplementItems.CURSED_DROPLET)
                    .input(ComplementItems.CURSED_DROPLET)
                    .input(Items.GLASS_BOTTLE, 3)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.BOTTLE_CURSE));

            RecipeTemplate.shapeless(ConsumableItems.BOOSTER_POTION, 1, MiscItems.WITCH_DROPLET)
                    .input(MiscItems.WITCH_DROPLET)
                    .input(ConsumableItems.BOTTLE_SANITY)
                    .input(ComplementItems.LIFE_ESSENCE)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.BOOSTER_POTION));

            RecipeTemplate.shapeless(ConsumableItems.BOOK_OMNISCIENCE, 1, ConsumableItems.BOOK_COPY)
                    .input(ConsumableItems.BOOK_COPY)
                    .input(LHTraits.REPRINT.asItem())
                    .input(LHTraits.SPLIT.asItem())
                    .input(Items.NETHER_STAR)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.BOOK_OMNISCIENCE));

            convert(exporter, ConsumableItems.BOTTLE_CURSE, MiscItems.HOSTILITY_ESSENCE, 512);

            RecipeTemplate.shapeless(ConsumableItems.SOUL_FIRE_CHARGE, 2, Items.BLAZE_POWDER)
                    .input(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                    .input(Items.BLAZE_POWDER, 2)
                    .input(Items.GUNPOWDER, 2)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.SOUL_FIRE_CHARGE));

            RecipeTemplate.shapeless(ConsumableItems.STRONG_FIRE_CHARGE, 2, Items.BLAZE_POWDER)
                    .input(Ingredient.ofItems(Items.COAL, Items.CHARCOAL))
                    .input(Items.BLAZE_POWDER, 2)
                    .input(Items.GUNPOWDER, 2)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.STRONG_FIRE_CHARGE));

            RecipeTemplate.shapeless(ConsumableItems.BLACK_FIRE_CHARGE, 2, Items.BLAZE_POWDER)
                    .input(Items.BLACKSTONE)
                    .input(Items.BLAZE_POWDER, 2)
                    .input(Items.GUNPOWDER, 2)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.BLACK_FIRE_CHARGE));

            RecipeTemplate.shapeless(ConsumableItems.WITCH_CHARGE, 1, MiscItems.WITCH_DROPLET)
                    .input(MiscItems.WITCH_DROPLET)
                    .input(ComplementItems.CURSED_DROPLET)
                    .input(Items.BLAZE_POWDER, 1)
                    .input(Items.GUNPOWDER, 1)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.WITCH_CHARGE));

            RecipeTemplate.shaped(ConsumableItems.ETERNAL_WITCH_CHARGE, 1, MiscItems.WITCH_DROPLET)
                    .pattern("ABA")
                    .pattern("BCB")
                    .pattern("DBD")
                    .input('A', Items.GUNPOWDER)
                    .input('D', Items.BLAZE_POWDER)
                    .input('B', ComplementItems.BLACKSTONE_CORE)
                    .input('C', MiscItems.WITCH_DROPLET)
                    .offerTo(exporter, ID_UTIL.get(ConsumableItems.ETERNAL_WITCH_CHARGE));
        });

        ID_UTIL.pushAndPop("misc/", () ->
        {
            RecipeTemplate.shaped(MiscItems.DETECTOR, 1, Items.LIGHTNING_ROD)
                    .pattern("ADA")
                    .pattern("BCB")
                    .pattern("ABA")
                    .input('A', Items.ROTTEN_FLESH)
                    .input('B', Items.BONE)
                    .input('C', Items.IRON_INGOT)
                    .input('D', Items.LIGHTNING_ROD)
                    .offerTo(exporter, ID_UTIL.get(MiscItems.DETECTOR));

            RecipeTemplate.shaped(MiscItems.DETECTOR_GLASSES, 1, Items.ENDER_EYE)
                    .pattern("ADA")
                    .input('A', Items.ENDER_EYE)
                    .input('D', Items.IRON_INGOT)
                    .offerTo(exporter, ID_UTIL.get(MiscItems.DETECTOR_GLASSES));

//            RecipeTemplate.shapeless(MiscItems.WITCH_CHARGE, 1,MiscItems.WITCH_DROPLET)
//                    .input(MiscItems.WITCH_DROPLET)
//                    .input(ComplementItems.CURSED_DROPLET)
//                    .input(Items.GUNPOWDER)
//                    .input(Items.BLAZE_POWDER)
//                    .offerTo(exporter, ID_UTIL.get(MiscItems.WITCH_CHARGE));
////
//            RecipeTemplate.shaped(MiscItems.ETERNAL_WITCH_CHARGE, 1,MiscItems.WITCH_DROPLET)
//                    .pattern("ABA").pattern("BCB").pattern("DBD")
//                    .input('A', Items.GUNPOWDER)
//                    .input('D', Items.BLAZE_POWDER)
//                    .input('B', ComplementItems.BLACKSTONE_CORE)
//                    .input('C', MiscItems.WITCH_DROPLET)
//                    .offerTo(exporter, ID_UTIL.get(MiscItems.ETERNAL_WITCH_CHARGE));

            RecipeTemplate.shaped(MiscItems.WITCH_WAND, 1, MiscItems.CHAOS.ingot())
                    .pattern("123")
                    .pattern("7I4")
                    .pattern("S65")
                    .input('I', MiscItems.CHAOS.ingot())
                    .input('S', Items.STICK)
                    .input('1', LHTraits.POISON.asItem())
                    .input('2', LHTraits.WITHER.asItem())
                    .input('3', LHTraits.SLOWNESS.asItem())
                    .input('4', LHTraits.WEAKNESS.asItem())
                    .input('5', LHTraits.LEVITATION.asItem())
                    .input('6', LHTraits.FREEZING.asItem())
                    .input('7', LHTraits.CURSED.asItem())
                    .offerTo(exporter, ID_UTIL.get(MiscItems.WITCH_WAND));

            RecipeTemplate.shaped(MiscItems.CHAOS.ingot(), 1, ConsumableItems.HOSTILITY_ORB)
                    .pattern("B4B")
                    .pattern("1A2")
                    .pattern("B3B")
                    .input('A', ConsumableItems.HOSTILITY_ORB)
                    .input('B', ConsumableItems.BOTTLE_CURSE)
                    .input('1', ComplementItems.SOUL_FLAME)
                    .input('2', ComplementItems.HARD_ICE)
                    .input('3', ComplementItems.EXPLOSION_SHARD)
                    .input('4', ComplementItems.CAPTURED_WIND)
                    .offerTo(exporter, ID_UTIL.get(MiscItems.CHAOS.ingot()));

            recycle(exporter, LHTags.CHAOS, MiscItems.CHAOS.ingot(), 1f);

            recycle(exporter, LHTags.TRAIT_ITEM, MiscItems.MIRACLE_POWDER, 1f);

            RecipeTemplate.shaped(MiscItems.MIRACLE.ingot(), 1, MiscItems.CHAOS.ingot())
                    .pattern("ABA")
                    .pattern("ACA")
                    .pattern("ABA")
                    .input('C', MiscItems.CHAOS.ingot())
                    .input('B', MiscItems.HOSTILITY_ESSENCE)
                    .input('A', MiscItems.MIRACLE_POWDER)
                    .offerTo(exporter, ID_UTIL.get(MiscItems.MIRACLE.ingot()));

            ID_UTIL.pushAndPop("storage/", () ->
            {
                RecipeTemplate.storage(exporter, MiscItems.CHAOS.ingot(), MiscItems.CHAOS.blockSet().item(), ID_UTIL);
                RecipeTemplate.storage(exporter, MiscItems.MIRACLE.ingot(), MiscItems.MIRACLE.blockSet().item(), ID_UTIL);
            });
        });

        ID_UTIL.pushAndPop("trinket/", () ->
        {
            ID_UTIL.pushAndPop("loot/", () ->
            {
                RecipeTemplate.shaped(TrinketItems.LOOT_1, 1, Items.EMERALD)
                        .pattern(" A ")
                        .pattern("DID")
                        .pattern(" A ")
                        .input('I', Items.EMERALD)
                        .input('A', Items.GOLD_INGOT)
                        .input('D', Items.COPPER_INGOT)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.LOOT_1));

                RecipeTemplate.shaped(TrinketItems.LOOT_2, 1, Items.DIAMOND)
                        .pattern(" A ")
                        .pattern("DID")
                        .pattern(" A ")
                        .input('I', Items.DIAMOND)
                        .input('A', Items.BLAZE_POWDER)
                        .input('D', Items.DRAGON_BREATH)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.LOOT_2));

                RecipeTemplate.shaped(TrinketItems.LOOT_3, 1, MiscItems.CHAOS.ingot())
                        .pattern(" A ")
                        .pattern("DID")
                        .pattern(" A ")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', ComplementItems.LIFE_ESSENCE)
                        .input('D', MiscItems.WITCH_DROPLET)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.LOOT_3));

                RecipeTemplate.shaped(TrinketItems.LOOT_4, 1, MiscItems.MIRACLE.ingot())
                        .pattern(" A ")
                        .pattern("DID")
                        .pattern(" A ")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('A', ComplementItems.BLACKSTONE_CORE)
                        .input('D', ComplementItems.FORCE_FIELD)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.LOOT_4));
            });

            ID_UTIL.pushAndPop("curse/", () ->
            {
                RecipeTemplate.shaped(TrinketItems.CURSE_SLOTH, 1, MiscItems.CHAOS.ingot())
                        .pattern("B1B")
                        .pattern("CIC")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', ComplementItems.BLACKSTONE_CORE)
                        .input('1', EnchantmentIngredient.of(Enchantments.VANISHING_CURSE, 1))
                        .input('B', Items.COPPER_INGOT)
                        .input('C', ConsumableItems.BOTTLE_SANITY)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_SLOTH));

                RecipeTemplate.shaped(TrinketItems.CURSE_ENVY, 1, MiscItems.CHAOS.ingot())
                        .pattern("B1B")
                        .pattern("CIC")
                        .pattern("B2B")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.SILK_TOUCH, 1))
                        .input('B', Items.PRISMARINE_SHARD)
                        .input('C', Items.ENDER_EYE)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_ENVY));

                RecipeTemplate.shaped(TrinketItems.CURSE_LUST, 1, MiscItems.CHAOS.ingot())
                        .pattern("B1B")
                        .pattern("CID")
                        .pattern("B2B")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.BINDING_CURSE, 1))
                        .input('B', Items.PHANTOM_MEMBRANE)
                        .input('C', LHTraits.REGEN.asItem())
                        .input('D', LHTraits.INVISIBLE.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_LUST));

                RecipeTemplate.shaped(TrinketItems.CURSE_GREED, 1, MiscItems.CHAOS.ingot())
                        .pattern("B1B")
                        .pattern("CID")
                        .pattern("B2B")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.FORTUNE, 1))
                        .input('B', Items.GOLD_INGOT)
                        .input('C', LHTraits.SPEEDY.asItem())
                        .input('D', LHTraits.TANK.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_GREED));

                RecipeTemplate.shaped(TrinketItems.CURSE_GLUTTONY, 1, MiscItems.CHAOS.ingot())
                        .pattern("B1B")
                        .pattern("CID")
                        .pattern("B2B")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.VANISHING_CURSE, 1))
                        .input('B', Items.NETHERITE_INGOT)
                        .input('C', LHTraits.CURSED.asItem())
                        .input('D', LHTraits.WITHER.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_GLUTTONY));

                RecipeTemplate.shaped(TrinketItems.CURSE_WRATH, 1, MiscItems.CHAOS.ingot())
                        .pattern("314")
                        .pattern("5I6")
                        .pattern("B2B")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('B', MiscItems.HOSTILITY_ESSENCE)
                        .input('1', LHTraits.FIERY.asItem())
                        .input('2', LHTraits.REPRINT.asItem())
                        .input('3', LHTraits.SHULKER.asItem())
                        .input('4', LHTraits.GRENADE.asItem())
                        .input('5', LHTraits.STRIKE.asItem())
                        .input('6', LHTraits.REFLECT.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_WRATH));

                RecipeTemplate.shaped(TrinketItems.CURSE_PRIDE, 1, MiscItems.CHAOS.ingot())
                        .pattern("515")
                        .pattern("3I4")
                        .pattern("B2B")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('B', MiscItems.HOSTILITY_ESSENCE)
                        .input('1', LHTraits.KILLER_AURA.asItem())
                        .input('2', LHTraits.PROTECTION.asItem())
                        .input('3', LHTraits.DEMENTOR.asItem())
                        .input('4', LHTraits.ADAPTIVE.asItem())
                        .input('5', LHTraits.GROWTH.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.CURSE_PRIDE));
            });

            ID_UTIL.pushAndPop("ring/", () ->
            {
                RecipeTemplate.shaped(TrinketItems.RING_OCEAN, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("DID")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', ComplementItems.GUARDIAN_EYE)
                        .input('B', ComplementItems.POSEIDITE.ingot())
                        .input('D', LHTraits.CONFUSION.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_OCEAN));

                RecipeTemplate.shaped(TrinketItems.RING_LIFE, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("DID")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', LHTraits.UNDYING.asItem())
                        .input('B', ComplementItems.SHULKERATE.ingot())
                        .input('D', LHTraits.REPELLING.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_LIFE));

                RecipeTemplate.shaped(TrinketItems.RING_HEALING, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("DID")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', Items.GHAST_TEAR)
                        .input('B', ComplementItems.TOTEMIC_GOLD.ingot())
                        .input('D', LHTraits.REGEN.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_HEALING));

                RecipeTemplate.shaped(TrinketItems.RING_DIVINITY, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("DID")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', ComplementItems.LIFE_ESSENCE)
                        .input('B', ComplementItems.TOTEMIC_GOLD.ingot())
                        .input('D', LHTraits.DISPELL.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_DIVINITY));

                RecipeTemplate.shaped(TrinketItems.RING_REFLECTION, 1, MiscItems.CHAOS.ingot())
                        .pattern("1A2")
                        .pattern("DID")
                        .pattern("3A4")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', ComplementItems.FORCE_FIELD)
                        .input('1', LHTraits.POISON.asItem())
                        .input('2', LHTraits.SLOWNESS.asItem())
                        .input('3', LHTraits.CONFUSION.asItem())
                        .input('4', LHTraits.BLIND.asItem())
                        .input('D', LHTraits.REFLECT.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_REFLECTION));

                RecipeTemplate.shaped(TrinketItems.RING_CORROSION, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("DID")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', LHTraits.CORROSION.asItem())
                        .input('B', ComplementItems.CURSED_DROPLET)
                        .input('D', LHTraits.EROSION.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_CORROSION));

                RecipeTemplate.shaped(TrinketItems.RING_INCARCERATION, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("1I2")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('1', LHTraits.SLOWNESS.asItem())
                        .input('2', LHTraits.FREEZING.asItem())
                        .input('A', LHTraits.KILLER_AURA.asItem())
                        .input('B', ComplementItems.BLACKSTONE_CORE)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RING_INCARCERATION));
            });

            ID_UTIL.pushAndPop("misc/", () ->
            {
                RecipeTemplate.shaped(TrinketItems.FLAMING_THORN, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("DID")
                        .pattern("BAB")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', LHTraits.DRAIN.asItem())
                        .input('B', ComplementItems.WARDEN_BONE_SHARD)
                        .input('D', LHTraits.SOUL_BURNER.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.FLAMING_THORN));

                RecipeTemplate.shaped(TrinketItems.INFINITY_GLOVE, 1, MiscItems.CHAOS.ingot())
                        .pattern("BAB")
                        .pattern("III")
                        .pattern("DID")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', LHTraits.SPLIT.asItem())
                        .input('B', LHTraits.ENDER.asItem())
                        .input('D', LHTraits.PULLING.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.INFINITY_GLOVE));

                RecipeTemplate.shaped(TrinketItems.ODDEYES_GLASSES, 1, MiscItems.CHAOS.ingot())
                        .pattern(" A ")
                        .pattern("1I2")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('A', Items.GOLD_INGOT)
                        .input('1', Items.CYAN_STAINED_GLASS_PANE)
                        .input('2', Items.MAGENTA_STAINED_GLASS_PANE)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.ODDEYES_GLASSES));

                RecipeTemplate.shaped(TrinketItems.TRIPLE_STRIP_CAPE, 1, MiscItems.CHAOS.ingot())
                        .pattern(" I ")
                        .pattern("CCC")
                        .pattern("FFF")
                        .input('I', MiscItems.CHAOS.ingot())
                        .input('C', ItemTags.BANNERS)
                        .input('F', ComplementItems.RESONANT_FEATHER)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.TRIPLE_STRIP_CAPE));

                RecipeTemplate.shaped(TrinketItems.ABRAHADABRA, 1, MiscItems.MIRACLE.ingot())
                        .pattern("AIA")
                        .pattern("EOE")
                        .pattern("BIC")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('E', ComplementItems.ETERNIUM.ingot())
                        .input('O', TrinketItems.RING_REFLECTION)
                        .input('A', LHTraits.RAGNAROK.asItem())
                        .input('B', LHTraits.REPELLING.asItem())
                        .input('C', LHTraits.PULLING.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.ABRAHADABRA));

                RecipeTemplate.shaped(TrinketItems.NIDHOGGUR, 1, MiscItems.MIRACLE.ingot())
                        .pattern("AIA")
                        .pattern("EOE")
                        .pattern("BIB")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('E', ComplementItems.ETERNIUM.ingot())
                        .input('O', TrinketItems.CURSE_GREED)
                        .input('A', LHTraits.RAGNAROK.asItem())
                        .input('B', LHTraits.PULLING.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.NIDHOGGUR));

                RecipeTemplate.shaped(TrinketItems.PLATINUM_STAR, 1, MiscItems.MIRACLE.ingot())
                        .pattern("BIB")
                        .pattern("ISI")
                        .pattern("BIB")
                        .input('S', MiscItems.MIRACLE.ingot())
                        .input('B', LHTraits.KILLER_AURA.asItem())
                        .input('I', Items.NETHER_STAR)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.PLATINUM_STAR));

                RecipeTemplate.shaped(TrinketItems.RESTORATION, 1, MiscItems.MIRACLE.ingot())
                        .pattern("BLB")
                        .pattern("SIS")
                        .pattern("BGB")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('B', ComplementItems.BLACKSTONE_CORE)
                        .input('S', LHTraits.DISPELL.asItem())
                        .input('L', LHTraits.MOONWALK.asItem())
                        .input('G', LHTraits.GRAVITY.asItem())
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.RESTORATION));

                RecipeTemplate.shaped(TrinketItems.ABYSSAL_THORN, 1, MiscItems.MIRACLE.ingot())
                        .pattern("AIA")
                        .pattern("IEI")
                        .pattern("XIX")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('E', ComplementItems.ETERNIUM.ingot())
                        .input('A', ComplementItems.GUARDIAN_EYE)
                        .input('X', ComplementItems.BLACKSTONE_CORE)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.ABYSSAL_THORN));

                RecipeTemplate.shaped(TrinketItems.DIVINITY_CROSS, 1, MiscItems.MIRACLE.ingot())
                        .pattern("STS")
                        .pattern("TIT")
                        .pattern("ETA")
                        .input('I', MiscItems.MIRACLE.ingot())
                        .input('T', ComplementItems.LIFE_ESSENCE)
                        .input('E', LHTraits.DRAIN.asItem())
                        .input('A', LHTraits.KILLER_AURA.asItem())
                        .input('S', MiscItems.WITCH_DROPLET)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.DIVINITY_CROSS));

                RecipeTemplate.shaped(TrinketItems.DIVINITY_LIGHT, 1, MiscItems.MIRACLE.ingot())
                        .pattern("STS")
                        .pattern("TIT")
                        .pattern("ETA")
                        .input('T', MiscItems.MIRACLE.ingot())
                        .input('I', TrinketItems.CURSE_SLOTH)
                        .input('E', LHTraits.GRAVITY.asItem())
                        .input('A', LHTraits.KILLER_AURA.asItem())
                        .input('S', ConsumableItems.HOSTILITY_ORB)
                        .offerTo(exporter, ID_UTIL.get(TrinketItems.DIVINITY_LIGHT));
            });
        });

        ID_UTIL.pushAndPop("enchantment/", () ->
        {
            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(1, MiscItems.CHAOS.ingot())
                            .withPedestalItem(1, ConsumableItems.BOTTLE_SANITY)
                            .withPedestalItem(2, Items.LAPIS_LAZULI)
                            .withPedestalItem(4, ComplementItems.FORCE_FIELD),
                    LHEnchantments.INSULATOR, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(1, MiscItems.CHAOS.ingot())
                            .withPedestalItem(1, ConsumableItems.BOTTLE_SANITY)
                            .withPedestalItem(2, Items.LAPIS_LAZULI)
                            .withPedestalItem(4, ComplementItems.GUARDIAN_EYE),
                    LHEnchantments.SPLIT_SUPPRESS, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(1, Items.GLASS)
                            .withPedestalItem(3, ComplementItems.SHULKERATE.nugget())
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.SHULKER_ARMOR, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(3, Items.OBSIDIAN)
                            .withPedestalItem(1, Items.CRYING_OBSIDIAN)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.STABLE_BODY, 1, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(Enchantments.UNBREAKING, 1)
                            .withPedestalItem(1, Items.DIAMOND)
                            .withPedestalItem(3, Items.LAPIS_LAZULI),
                    LHEnchantments.DURABLE_ARMOR, 1, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(Enchantments.UNBREAKING, 2)
                            .withPedestalItem(4, Items.DIAMOND)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.DURABLE_ARMOR, 2, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(Enchantments.UNBREAKING, 3)
                            .withPedestalItem(1, Items.DIAMOND_HELMET)
                            .withPedestalItem(1, Items.DIAMOND_CHESTPLATE)
                            .withPedestalItem(1, Items.DIAMOND_LEGGINGS)
                            .withPedestalItem(1, Items.DIAMOND_BOOTS)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.DURABLE_ARMOR, 3, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(3, Items.WITHER_ROSE)
                            .withPedestalItem(1, ComplementItems.FORCE_FIELD)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.LIFE_SYNC, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(2, Items.WEEPING_VINES)
                            .withPedestalItem(2, Items.TWISTING_VINES)
                            .withPedestalItem(2, Items.ROTTEN_FLESH)
                            .withPedestalItem(2, Items.LAPIS_LAZULI),
                    LHEnchantments.LIFE_MENDING, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(2, ComplementItems.SHULKERATE.nugget())
                            .withPedestalItem(2, Items.NETHERITE_SCRAP)
                            .withPedestalItem(2, Items.AMETHYST_SHARD)
                            .withPedestalItem(2, Items.LAPIS_LAZULI),
                    LHEnchantments.SAFEGUARD, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(3, ComplementItems.SHULKERATE.ingot())
                            .withPedestalItem(2, ComplementItems.WARDEN_BONE_SHARD)
                            .withPedestalItem(1, ComplementItems.EXPLOSION_SHARD)
                            .withPedestalItem(2, Items.LAPIS_LAZULI),
                    LHEnchantments.HARDENED, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(4, ComplementItems.SCULKIUM.nugget())
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.DAMPENED, 1, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(LHEnchantments.HARDENED, 1)
                            .withPedestalItem(1, ComplementItems.SPACE_SHARD)
                            .withPedestalItem(3, ComplementItems.ETERNIUM.ingot())
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.ETERNAL, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(1, ComplementItems.STORM_CORE)
                            .withPedestalItem(3, ComplementItems.SHULKERATE.nugget())
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.WIND_SWEEP, 1, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(Enchantments.FIRE_ASPECT, 2)
                            .withPedestalItem(4, ComplementItems.SOUL_FLAME)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.FLAME_BLADE, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(4, ComplementItems.HARD_ICE)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.ICE_BLADE, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(1, ComplementItems.CURSED_DROPLET)
                            .withPedestalItem(3, ComplementItems.EXPLOSION_SHARD)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.SHARP_BLADE, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(2, Items.FERMENTED_SPIDER_EYE)
                            .withPedestalItem(2, ComplementItems.CURSED_DROPLET)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.CURSE_BLADE, 1, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(Enchantments.THORNS, 3)
                            .withPedestalItem(2, ComplementItems.HARD_ICE)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.ICE_THORN, 1, exporter
            );

            enchantmentSet(
                    builder -> builder
                            .withTableIngredient(Enchantments.THORNS, 3)
                            .withPedestalItem(2, ComplementItems.SOUL_FLAME)
                            .withPedestalItem(4, Items.LAPIS_LAZULI),
                    LHEnchantments.FLAME_THORN, 1, exporter
            );

            enchantmentAdd(
                    builder -> builder
                            .withPedestalItem(2, ComplementItems.VOID_EYE)
                            .withPedestalItem(2, ComplementItems.SUN_MEMBRANE)
                            .withPedestalItem(4, ComplementItems.RESONANT_FEATHER),
                    LHEnchantments.VOID_TOUCH, 1, exporter
            );

            ID_UTIL.pushAndPop("immune/", () ->
            {
                enchantmentSet(
                        builder -> builder
                                .withTableIngredient(Enchantments.INFINITY, 1)
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.PROJECTILE_PROTECTION, 4))
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.PROTECTION, 4))
                                .withPedestalItem(4, ComplementItems.FORCE_FIELD),
                        LHEnchantments.ENCH_PROJECTILE, 1, exporter
                );

                enchantmentSet(
                        builder -> builder
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.FIRE_PROTECTION, 4))
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.PROTECTION, 4))
                                .withPedestalItem(2, ComplementItems.SOUL_FLAME)
                                .withPedestalItem(2, ComplementItems.HARD_ICE),
                        LHEnchantments.ENCH_FIRE, 1, exporter
                );

                enchantmentSet(
                        builder -> builder
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.PROTECTION, 4))
                                .withPedestalItem(2, ComplementItems.SUN_MEMBRANE)
                                .withPedestalItem(2, ComplementItems.VOID_EYE)
                                .withPedestalItem(2, ComplementItems.CAPTURED_WIND),
                        LHEnchantments.ENCH_ENVIRONMENT, 1, exporter
                );

                enchantmentSet(
                        builder -> builder
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.BLAST_PROTECTION, 4))
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.PROTECTION, 4))
                                .withPedestalItem(2, ComplementItems.EXPLOSION_SHARD)
                                .withPedestalItem(2, Items.CRYING_OBSIDIAN),
                        LHEnchantments.ENCH_EXPLOSION, 1, exporter
                );

                enchantmentSet(
                        builder -> builder
                                .withPedestalItem(2, EnchantmentIngredient.of(Enchantments.PROTECTION, 4))
                                .withPedestalItem(2, ComplementItems.VOID_EYE)
                                .withPedestalItem(2, ComplementItems.FORCE_FIELD)
                                .withPedestalItem(2, ComplementItems.RESONANT_FEATHER),
                        LHEnchantments.ENCH_MAGIC, 1, exporter
                );

                enchantmentSet(
                        builder -> builder
                                .withTableIngredient(LHEnchantments.ENCH_ENVIRONMENT, 1)
                                .withPedestalItem(1, EnchantmentIngredient.of(LHEnchantments.ENCH_FIRE, 1))
                                .withPedestalItem(1, EnchantmentIngredient.of(LHEnchantments.ENCH_MAGIC, 1))
                                .withPedestalItem(1, EnchantmentIngredient.of(LHEnchantments.ENCH_EXPLOSION, 1))
                                .withPedestalItem(1, EnchantmentIngredient.of(LHEnchantments.ENCH_PROJECTILE, 1))
                                .withPedestalItem(4, ComplementItems.FORCE_FIELD),
                        LHEnchantments.ENCH_INVINCIBLE, 1, exporter
                );

                enchantmentSet(
                        builder -> builder
                                .withTableIngredient(Enchantments.PROTECTION, 4)
                                .withPedestalItem(2, Items.NETHER_STAR)
                                .withPedestalItem(6, Items.END_ROD),
                        LHEnchantments.ENCH_MATES, 1, exporter
                );
            });
        });

        RecipeTemplate.shaped(LHBlocks.SPAWNER.item(), 1, Items.NETHER_STAR)
                .pattern("ADA")
                .pattern("BCB")
                .pattern("ABA")
                .input('C', Items.NETHER_STAR)
                .input('B', ComplementItems.EXPLOSION_SHARD)
                .input('A', Items.NETHERITE_INGOT)
                .input('D', ComplementItems.CURSED_DROPLET)
                .offerTo(exporter, ID_UTIL.get(LHBlocks.SPAWNER.item()));

        PatchouliHelper.shapeless(MiscItems.GUIDE_BOOK, Items.BOOK)
                .input(Items.BOOK)
                .input(Items.ROTTEN_FLESH)
                .input(Items.BONE)
                .offerTo(exporter);
    }
}
