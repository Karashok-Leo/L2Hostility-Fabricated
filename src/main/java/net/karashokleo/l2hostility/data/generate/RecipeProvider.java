package net.karashokleo.l2hostility.data.generate;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.recipe.EnchantmentIngredient;
import net.karashokleo.l2hostility.content.recipe.EnchantmentRecipeBuilder;
import net.karashokleo.l2hostility.init.*;
import net.karashokleo.leobrary.compat.patchouli.PatchouliHelper;
import net.karashokleo.leobrary.datagen.util.RecipeGenUtil;
import net.minecraft.data.server.recipe.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class RecipeProvider extends FabricRecipeProvider
{
    private static final String currentFolder = "";

    public RecipeProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter)
    {
        // LHBlocks.BURST_SPAWNER / LHItems.WITCH_CHARGE / LHItems.ETERNAL_WITCH_CHARGE
        // LCItems.EXPLOSION_SHARD / LCItems.CURSED_DROPLET / LCItems.LIFE_ESSENCE
        // LCItems.BLACKSTONE_CORE / LCItems.SOUL_FLAME / LCItems.HARD_ICE
        // LCItems.CAPTURED_WIND / LCItems.FORCE_FIELD / LCItems.GUARDIAN_EYE
        // LCItems.WARDEN_BONE_SHARD / LCItems.RESONANT_FEATHER
        // LCItems.POSEIDITE.getIngot() / LCItems.SHULKERATE.getIngot() / LCItems.TOTEMIC_GOLD.getIngot() / LCItems.ETERNIUM.getIngot()

        //misc
        {
//            RecipeGenUtil.shaped(LHBlocks.BURST_SPAWNER, 1,Items.NETHER_STAR)
//                    .pattern("ADA").pattern("BCB").pattern("ABA")
//                    .input('C', Items.NETHER_STAR)
//                    .input('B', LCItems.EXPLOSION_SHARD)
//                    .input('A', Items.NETHERITE_INGOT)
//                    .input('D', LCItems.CURSED_DROPLET)
//                    .offerTo(exporter);

            RecipeGenUtil.shaped(LHItems.DETECTOR, 1, Items.LIGHTNING_ROD)
                    .pattern("ADA").pattern("BCB").pattern("ABA")
                    .input('A', Items.ROTTEN_FLESH)
                    .input('B', Items.BONE)
                    .input('C', Items.IRON_INGOT)
                    .input('D', Items.LIGHTNING_ROD)
                    .offerTo(exporter);

            RecipeGenUtil.shaped(LHItems.DETECTOR_GLASSES, 1, Items.ENDER_EYE)
                    .pattern("ADA")
                    .input('A', Items.ENDER_EYE)
                    .input('D', Items.IRON_INGOT)
                    .offerTo(exporter);

            RecipeGenUtil.shapeless(LHItems.BOTTLE_SANITY, 3, LHItems.HOSTILITY_ORB)
                    .input(LHItems.HOSTILITY_ORB)
                    .input(Items.GLASS_BOTTLE, 3)
                    .offerTo(exporter, getID(LHItems.BOTTLE_SANITY, "_craft"));

            RecipeGenUtil.shapeless(LHItems.BOTTLE_SANITY, 3, LCItems.LIFE_ESSENCE)
                    .input(LHItems.BOTTLE_SANITY)
                    .input(LCItems.LIFE_ESSENCE)
                    .input(Items.GLASS_BOTTLE, 3)
                    .offerTo(exporter, getID(LHItems.BOTTLE_SANITY, "_renew"));

            RecipeGenUtil.shapeless(LHItems.BOTTLE_CURSE, 3, LCItems.CURSED_DROPLET)
                    .input(LCItems.CURSED_DROPLET)
                    .input(Items.GLASS_BOTTLE, 3)
                    .offerTo(exporter);

            RecipeGenUtil.shapeless(LHItems.BOOSTER_POTION, 1, LHItems.WITCH_DROPLET)
                    .input(LHItems.WITCH_DROPLET)
                    .input(LHItems.BOTTLE_SANITY)
                    .input(LCItems.LIFE_ESSENCE)
                    .offerTo(exporter);

//            RecipeGenUtil.shapeless(LHItems.WITCH_CHARGE, 1,LHItems.WITCH_DROPLET)
//                    .input(LHItems.WITCH_DROPLET)
//                    .input(LCItems.CURSED_DROPLET)
//                    .input(Items.GUNPOWDER)
//                    .input(Items.BLAZE_POWDER)
//                    .offerTo(exporter);
//
//            RecipeGenUtil.shaped(LHItems.ETERNAL_WITCH_CHARGE, 1,LHItems.WITCH_DROPLET)
//                    .pattern("ABA").pattern("BCB").pattern("DBD")
//                    .input('A', Items.GUNPOWDER)
//                    .input('D', Items.BLAZE_POWDER)
//                    .input('B', LCItems.BLACKSTONE_CORE)
//                    .input('C', LHItems.WITCH_DROPLET)
//                    .offerTo(exporter);

            RecipeGenUtil.shapeless(LHItems.BOOK_OMNISCIENCE, 1, LHItems.BOOK_COPY)
                    .input(LHItems.BOOK_COPY)
                    .input(LHTraits.REPRINT.asItem())
                    .input(LHTraits.SPLIT.asItem())
                    .input(Items.NETHER_STAR)
                    .offerTo(exporter);
        }

        //trinket
        {
            RecipeGenUtil.shaped(LHItems.CHAOS_INGOT, 1, LHItems.HOSTILITY_ORB)
                    .pattern("B4B").pattern("1A2").pattern("B3B")
                    .input('A', LHItems.HOSTILITY_ORB)
                    .input('B', LHItems.BOTTLE_CURSE)
                    .input('1', LCItems.SOUL_FLAME)
                    .input('2', LCItems.HARD_ICE)
                    .input('3', LCItems.EXPLOSION_SHARD)
                    .input('4', LCItems.CAPTURED_WIND)
                    .offerTo(exporter);

//            convert(exporter, LHItems.BOTTLE_CURSE, LHItems.HOSTILITY_ESSENCE, 512);
            recycle(exporter, LHTags.CHAOS, LHItems.CHAOS_INGOT, 1f);
            recycle(exporter, LHTags.TRAIT_ITEM, LHItems.MIRACLE_POWDER, 1f);

            RecipeGenUtil.shaped(LHItems.MIRACLE_INGOT, 1, LHItems.CHAOS_INGOT)
                    .pattern("ABA").pattern("ACA").pattern("ABA")
                    .input('C', LHItems.CHAOS_INGOT)
                    .input('B', LHItems.HOSTILITY_ESSENCE)
                    .input('A', LHItems.MIRACLE_POWDER)
                    .offerTo(exporter);

            // loot
            {
                RecipeGenUtil.shaped(LHItems.LOOT_1, 1, Items.EMERALD)
                        .pattern(" A ").pattern("DID").pattern(" A ")
                        .input('I', Items.EMERALD)
                        .input('A', Items.GOLD_INGOT)
                        .input('D', Items.COPPER_INGOT)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.LOOT_2, 1, Items.DIAMOND)
                        .pattern(" A ").pattern("DID").pattern(" A ")
                        .input('I', Items.DIAMOND)
                        .input('A', Items.BLAZE_POWDER)
                        .input('D', Items.DRAGON_BREATH)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.LOOT_3, 1, LHItems.CHAOS_INGOT)
                        .pattern(" A ").pattern("DID").pattern(" A ")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LCItems.LIFE_ESSENCE)
                        .input('D', LHItems.WITCH_DROPLET)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.LOOT_4, 1, LHItems.MIRACLE_INGOT)
                        .pattern(" A ").pattern("DID").pattern(" A ")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('A', LCItems.BLACKSTONE_CORE)
                        .input('D', LCItems.FORCE_FIELD)
                        .offerTo(exporter);
            }

            // curse
            {
                RecipeGenUtil.shaped(LHItems.CURSE_SLOTH, 1, LHItems.CHAOS_INGOT)
                        .pattern("B1B").pattern("CIC").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LCItems.BLACKSTONE_CORE)
                        .input('1', EnchantmentIngredient.of(Enchantments.VANISHING_CURSE, 1))
                        .input('B', Items.COPPER_INGOT)
                        .input('C', LHItems.BOTTLE_SANITY)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.CURSE_ENVY, 1, LHItems.CHAOS_INGOT)
                        .pattern("B1B").pattern("CIC").pattern("B2B")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.SILK_TOUCH, 1))
                        .input('B', Items.PRISMARINE_SHARD)
                        .input('C', Items.ENDER_EYE)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.CURSE_LUST, 1, LHItems.CHAOS_INGOT)
                        .pattern("B1B").pattern("CID").pattern("B2B")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.BINDING_CURSE, 1))
                        .input('B', Items.PHANTOM_MEMBRANE)
                        .input('C', LHTraits.REGEN.asItem())
                        .input('D', LHTraits.INVISIBLE.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.CURSE_GREED, 1, LHItems.CHAOS_INGOT)
                        .pattern("B1B").pattern("CID").pattern("B2B")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.FORTUNE, 1))
                        .input('B', Items.GOLD_INGOT)
                        .input('C', LHTraits.SPEEDY.asItem())
                        .input('D', LHTraits.TANK.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.CURSE_GLUTTONY, 1, LHItems.CHAOS_INGOT)
                        .pattern("B1B").pattern("CID").pattern("B2B")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('1', EnchantmentIngredient.of(Enchantments.LOOTING, 1))
                        .input('2', EnchantmentIngredient.of(Enchantments.VANISHING_CURSE, 1))
                        .input('B', Items.NETHERITE_INGOT)
                        .input('C', LHTraits.CURSED.asItem())
                        .input('D', LHTraits.WITHER.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.CURSE_WRATH, 1, LHItems.CHAOS_INGOT)
                        .pattern("314").pattern("5I6").pattern("B2B")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('B', LHItems.HOSTILITY_ESSENCE)
                        .input('1', LHTraits.FIERY.asItem())
                        .input('2', LHTraits.REPRINT.asItem())
                        .input('3', LHTraits.SHULKER.asItem())
                        .input('4', LHTraits.GRENADE.asItem())
                        .input('5', LHTraits.STRIKE.asItem())
                        .input('6', LHTraits.REFLECT.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.CURSE_PRIDE, 1, LHItems.CHAOS_INGOT)
                        .pattern("515").pattern("3I4").pattern("B2B")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('B', LHItems.HOSTILITY_ESSENCE)
                        .input('1', LHTraits.KILLER_AURA.asItem())
                        .input('2', LHTraits.PROTECTION.asItem())
                        .input('3', LHTraits.DEMENTOR.asItem())
                        .input('4', LHTraits.ADAPTIVE.asItem())
                        .input('5', LHTraits.GROWTH.asItem())
                        .offerTo(exporter);
            }

            // ring
            {
                RecipeGenUtil.shaped(LHItems.RING_OCEAN, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("DID").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LCItems.GUARDIAN_EYE)
                        .input('B', LCItems.POSEIDITE)
                        .input('D', LHTraits.CONFUSION.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RING_LIFE, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("DID").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LHTraits.UNDYING.asItem())
                        .input('B', LCItems.SHULKERATE)
                        .input('D', LHTraits.REPELLING.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RING_HEALING, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("DID").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', Items.GHAST_TEAR)
                        .input('B', LCItems.TOTEMIC_GOLD)
                        .input('D', LHTraits.REGEN.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RING_DIVINITY, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("DID").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LCItems.LIFE_ESSENCE)
                        .input('B', LCItems.TOTEMIC_GOLD)
                        .input('D', LHTraits.DISPELL.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RING_REFLECTION, 1, LHItems.CHAOS_INGOT)
                        .pattern("1A2").pattern("DID").pattern("3A4")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LCItems.FORCE_FIELD)
                        .input('1', LHTraits.POISON.asItem())
                        .input('2', LHTraits.SLOWNESS.asItem())
                        .input('3', LHTraits.CONFUSION.asItem())
                        .input('4', LHTraits.BLIND.asItem())
                        .input('D', LHTraits.REFLECT.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RING_CORROSION, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("DID").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LHTraits.CORROSION.asItem())
                        .input('B', LCItems.CURSED_DROPLET)
                        .input('D', LHTraits.EROSION.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RING_INCARCERATION, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("1I2").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('1', LHTraits.SLOWNESS.asItem())
                        .input('2', LHTraits.FREEZING.asItem())
                        .input('A', LHTraits.KILLER_AURA.asItem())
                        .input('B', LCItems.BLACKSTONE_CORE)
                        .offerTo(exporter);
            }

            // misc
            {
                RecipeGenUtil.shaped(LHItems.FLAMING_THORN, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("DID").pattern("BAB")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LHTraits.DRAIN.asItem())
                        .input('B', LCItems.WARDEN_BONE_SHARD)
                        .input('D', LHTraits.SOUL_BURNER.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.WITCH_WAND, 1, LHItems.CHAOS_INGOT)
                        .pattern("123").pattern("7I4").pattern("S65")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('S', Items.STICK)
                        .input('1', LHTraits.POISON.asItem())
                        .input('2', LHTraits.WITHER.asItem())
                        .input('3', LHTraits.SLOWNESS.asItem())
                        .input('4', LHTraits.WEAKNESS.asItem())
                        .input('5', LHTraits.LEVITATION.asItem())
                        .input('6', LHTraits.FREEZING.asItem())
                        .input('7', LHTraits.CURSED.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.INFINITY_GLOVE, 1, LHItems.CHAOS_INGOT)
                        .pattern("BAB").pattern("III").pattern("DID")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', LHTraits.SPLIT.asItem())
                        .input('B', LHTraits.ENDER.asItem())
                        .input('D', LHTraits.PULLING.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.ODDEYES_GLASSES, 1, LHItems.CHAOS_INGOT)
                        .pattern(" A ").pattern("1I2")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('A', Items.GOLD_INGOT)
                        .input('1', Items.CYAN_STAINED_GLASS_PANE)
                        .input('2', Items.MAGENTA_STAINED_GLASS_PANE)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.TRIPLE_STRIP_CAPE, 1, LHItems.CHAOS_INGOT)
                        .pattern(" I ").pattern("CCC").pattern("FFF")
                        .input('I', LHItems.CHAOS_INGOT)
                        .input('C', ItemTags.BANNERS)
                        .input('F', LCItems.RESONANT_FEATHER)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.ABRAHADABRA, 1, LHItems.MIRACLE_INGOT)
                        .pattern("AIA").pattern("EOE").pattern("BIC")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('E', LCItems.ETERNIUM)
                        .input('O', LHItems.RING_REFLECTION)
                        .input('A', LHTraits.RAGNAROK.asItem())
                        .input('B', LHTraits.REPELLING.asItem())
                        .input('C', LHTraits.PULLING.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.NIDHOGGUR, 1, LHItems.MIRACLE_INGOT)
                        .pattern("AIA").pattern("EOE").pattern("BIB")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('E', LCItems.ETERNIUM)
                        .input('O', LHItems.CURSE_GREED)
                        .input('A', LHTraits.RAGNAROK.asItem())
                        .input('B', LHTraits.PULLING.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.PLATINUM_STAR, 1, LHItems.MIRACLE_INGOT)
                        .pattern("BIB").pattern("ISI").pattern("BIB")
                        .input('S', LHItems.MIRACLE_INGOT)
                        .input('B', LHTraits.KILLER_AURA.asItem())
                        .input('I', Items.NETHER_STAR)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.RESTORATION, 1, LHItems.MIRACLE_INGOT)
                        .pattern("BLB").pattern("SIS").pattern("BGB")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('B', LCItems.BLACKSTONE_CORE)
                        .input('S', LHTraits.DISPELL.asItem())
                        .input('L', LHTraits.MOONWALK.asItem())
                        .input('G', LHTraits.GRAVITY.asItem())
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.ABYSSAL_THORN, 1, LHItems.MIRACLE_INGOT)
                        .pattern("AIA").pattern("IEI").pattern("XIX")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('E', LCItems.ETERNIUM)
                        .input('A', LCItems.GUARDIAN_EYE)
                        .input('X', LCItems.BLACKSTONE_CORE)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.DIVINITY_CROSS, 1, LHItems.MIRACLE_INGOT)
                        .pattern("STS").pattern("TIT").pattern("ETA")
                        .input('I', LHItems.MIRACLE_INGOT)
                        .input('T', LCItems.LIFE_ESSENCE)
                        .input('E', LHTraits.DRAIN.asItem())
                        .input('A', LHTraits.KILLER_AURA.asItem())
                        .input('S', LHItems.WITCH_DROPLET)
                        .offerTo(exporter);

                RecipeGenUtil.shaped(LHItems.DIVINITY_LIGHT, 1, LHItems.MIRACLE_INGOT)
                        .pattern("STS").pattern("TIT").pattern("ETA")
                        .input('T', LHItems.MIRACLE_INGOT)
                        .input('I', LHItems.CURSE_SLOTH)
                        .input('E', LHTraits.GRAVITY.asItem())
                        .input('A', LHTraits.KILLER_AURA.asItem())
                        .input('S', LHItems.HOSTILITY_ORB)
                        .offerTo(exporter);
            }

        }

        // ench
        {
            enchantment(LHEnchantments.INSULATOR, 1, LHItems.CHAOS_INGOT)
                    .pattern("AIA").pattern("DBD").pattern("ACA")
                    .input('B', Items.BOOK)
                    .input('D', Items.LAPIS_LAZULI)
                    .input('I', LHItems.CHAOS_INGOT)
                    .input('A', LCItems.FORCE_FIELD)
                    .input('C', LHItems.BOTTLE_SANITY)
                    .offerTo(exporter);

            enchantment(LHEnchantments.SPLIT_SUPPRESS, 1, LHItems.CHAOS_INGOT)
                    .pattern("AIA").pattern("DBD").pattern("ACA")
                    .input('B', Items.BOOK)
                    .input('D', Items.LAPIS_LAZULI)
                    .input('I', LHItems.CHAOS_INGOT)
                    .input('A', LCItems.GUARDIAN_EYE)
                    .input('C', LHItems.BOTTLE_SANITY)
                    .offerTo(exporter);

            PatchouliHelper.shapeless(LHItems.GUIDE_BOOK,Items.BOOK)
                    .input(Items.BOOK)
                    .input(Items.ROTTEN_FLESH)
                    .input(Items.BONE)
                    .offerTo(exporter);
        }

        // compat
//        if (ModList.isLoaded(TwilightForestMod.ID)) {
//            TFData.genRecipe(exporter);
//        }
//        if (ModList.isLoaded(Cataclysm.MODID)) {
//            CataclysmData.genRecipe(exporter);
//        }
    }

    @SuppressWarnings("ConstantConditions")
    private static Identifier getID(Enchantment item)
    {
        return L2Hostility.id(currentFolder + Registries.ENCHANTMENT.getId(item).getPath());
    }

    @SuppressWarnings("ConstantConditions")
    private static Identifier getID(Enchantment item, String suffix)
    {
        return L2Hostility.id(currentFolder + Registries.ENCHANTMENT.getId(item).getPath() + suffix);
    }

    @SuppressWarnings("ConstantConditions")
    public static Identifier getID(ItemConvertible item)
    {
        return L2Hostility.id(currentFolder + Registries.ITEM.getId(item.asItem()).getPath());
    }

    @SuppressWarnings("ConstantConditions")
    private static Identifier getID(Item item, String suffix)
    {
        return L2Hostility.id(currentFolder + Registries.ITEM.getId(item).getPath() + suffix);
    }

    @SuppressWarnings("ConstantConditions")
    private static EnchantmentRecipeBuilder enchantment(Enchantment enchantment, int level, Item item)
    {
        return RecipeGenUtil.unlock(EnchantmentRecipeBuilder.create(enchantment, level)::criterion, item);
    }

    @SuppressWarnings("ConstantConditions")
//    private static void convert(Consumer<RecipeJsonProvider> exporter, Item in, Item out, int count)
//    {
//        RecipeGenUtil.unlock(new BurntRecipeBuilder(Ingredient.ofItems(in), out.getDefaultStack(), count)::criterion, in)
//                .offerTo(exporter, getID(out));
//    }

    public static void recycle(Consumer<RecipeJsonProvider> exporter, TagKey<Item> source, Item result, float experience)
    {
        RecipeGenUtil.unlock(CookingRecipeJsonBuilder.createBlasting(Ingredient.fromTag(source), RecipeCategory.MISC, result, experience, 200)::criterion, result)
                .offerTo(exporter, getID(result, "_recycle"));
    }
}
