package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.data.config.WeaponConfig;
import net.karashokleo.l2hostility.data.config.WorldDifficultyConfig;
import net.karashokleo.l2hostility.data.generate.*;
import net.karashokleo.l2hostility.data.config.provider.EntityConfigProvider;
import net.karashokleo.l2hostility.data.config.provider.TraitConfigProvider;
import net.karashokleo.l2hostility.data.config.provider.WeaponConfigProvider;
import net.karashokleo.l2hostility.data.config.provider.WorldDifficultyConfigProvider;
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
    public static WorldDifficultyConfig difficulties;

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
                LHTags.SKILL_EFFECT,
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

//        L2Hostility.REGISTRATE.CONFIGS.forEach(e -> e.accept(collector));
//
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

//    public static <T extends LivingEntity> void addEntity(AbstractDataProvider.Collector collector, int min, int base, RegistryObject<EntityType<T>> obj, EntityConfig.TraitBase... traits) {
//        collector.add(L2Hostility.ENTITY, obj.getId(), new EntityConfig().putEntity(min, base, 0, 0, List.of(obj.get()), List.of(traits)));
//    }
//
//    public static <T extends LivingEntity> void addEntity(AbstractDataProvider.Collector collector, int min, int base, RegistryObject<EntityType<T>> obj, List<EntityConfig.TraitBase> traits, List<EntityConfig.ItemPool> items) {
//        collector.add(L2Hostility.ENTITY, obj.getId(), new EntityConfig().putEntityAndItem(min, base, 0, 0, List.of(obj.get()), traits, items));
}
