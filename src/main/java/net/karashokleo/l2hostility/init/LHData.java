package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.data.config.DifficultyConfig;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.data.config.WeaponConfig;
import net.karashokleo.l2hostility.data.config.loader.EntityConfigLoader;
import net.karashokleo.l2hostility.data.config.loader.TraitConfigLoader;
import net.karashokleo.l2hostility.data.config.loader.WeaponConfigLoader;
import net.karashokleo.l2hostility.data.config.loader.DifficultyConfigLoader;
import net.karashokleo.l2hostility.data.generate.*;
import net.karashokleo.l2hostility.data.config.provider.EntityConfigProvider;
import net.karashokleo.l2hostility.data.config.provider.TraitConfigProvider;
import net.karashokleo.l2hostility.data.config.provider.WeaponConfigProvider;
import net.karashokleo.l2hostility.data.config.provider.WorldDifficultyConfigProvider;
import net.karashokleo.leobrary.compat.patchouli.PatchouliHelper;
import net.karashokleo.leobrary.datagen.generator.DynamicRegistryGenerator;
import net.karashokleo.leobrary.datagen.generator.ModelGenerator;
import net.karashokleo.leobrary.datagen.generator.TagGenerator;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourceType;

public class LHData implements DataGeneratorEntrypoint
{
    public static final LanguageGenerator EN_TEXTS = new LanguageGenerator("en_us");
    public static final LanguageGenerator ZH_TEXTS = new LanguageGenerator("zh_cn");
    public static final ModelGenerator MODELS = new ModelGenerator();
    public static final TagGenerator<Item> ITEM_TAGS = new TagGenerator<>(RegistryKeys.ITEM);
    public static final TagGenerator<MobTrait> TRAIT_TAGS = new TagGenerator<>(LHTraits.TRAIT_KEY);
    public static final TagGenerator<EntityType<?>> ENTITY_TYPE_TAGS = new TagGenerator<>(RegistryKeys.ENTITY_TYPE);
    public static final TagGenerator<Enchantment> ENCHANTMENT_TAGS = new TagGenerator<>(RegistryKeys.ENCHANTMENT);
    public static final TagGenerator<StatusEffect> STATUS_EFFECT_TAGS = new TagGenerator<>(RegistryKeys.STATUS_EFFECT);
    public static final DynamicRegistryGenerator DYNAMICS = new DynamicRegistryGenerator("LH Dynamic Registries");

    public static EntityConfig entities;
    public static TraitConfig traits;
    public static WeaponConfig weapons;
    public static DifficultyConfig difficulties;

    public static void register()
    {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new EntityConfigLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new TraitConfigLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new WeaponConfigLoader());
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new DifficultyConfigLoader());
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
//        builder.add("config.jade.plugin_l2hostility.mob", "L2Hostility");
//        builder.add("curios.identifier.hostility_curse", "L2Hostility - Curse");
//        builder.add("curios.modifiers.hostility_curse", "When worn as Curse:");

        ENCHANTMENT_TAGS.add(
                LHTags.NO_DISPELL,
                Enchantments.UNBREAKING
        );
        STATUS_EFFECT_TAGS.add(
                LHTags.CLEANSE_BLACKLIST,
                StatusEffects.NIGHT_VISION,
                StatusEffects.BAD_OMEN,
                StatusEffects.HERO_OF_THE_VILLAGE,
                StatusEffects.DOLPHINS_GRACE,
                StatusEffects.CONDUIT_POWER,
                StatusEffects.WATER_BREATHING
        );

        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(EntityConfigProvider::new);
        pack.addProvider(TraitConfigProvider::new);
        pack.addProvider(WeaponConfigProvider::new);
        pack.addProvider(WorldDifficultyConfigProvider::new);

        EN_TEXTS.generate(pack);
        ZH_TEXTS.generate(pack);
        MODELS.generate(pack);
        ITEM_TAGS.generate(pack);
        TRAIT_TAGS.generate(pack);
        ENTITY_TYPE_TAGS.generate(pack);
        ENCHANTMENT_TAGS.generate(pack);
        STATUS_EFFECT_TAGS.generate(pack);
        DYNAMICS.generate(pack);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(AdvancementProvider::new);
        PatchouliHelper.model(pack, LHItems.GUIDE_BOOK);



//        collector.add(L2DamageTracker.ARMOR, new ResourceLocation(L2Hostility.MODID, "equipments"), new ArmorEffectConfig()
//                .add(LHItems.CURSE_WRATH.getId().toString(),
//                        MobEffects.BLINDNESS, MobEffects.DARKNESS, MobEffects.CONFUSION,
//                        MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN, MobEffects.WEAKNESS));
//
//        if (ModList.get().isLoaded(TwilightForestMod.ID))
//            TFData.genConfig(collector);
//        if (ModList.get().isLoaded(Cataclysm.MODID))
//            CataclysmData.genConfig(collector);
//        if (ModList.get().isLoaded("bosses_of_mass_destruction"))
//            BoMDData.genConfig(collector);
//        if (ModList.get().isLoaded(IceAndFire.MODID))
//            IaFData.genConfig(collector);
    }
}
