package net.karashokleo.l2hostility.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.karashokleo.l2hostility.data.config.EntityConfig;
import net.karashokleo.l2hostility.data.config.TraitConfig;
import net.karashokleo.l2hostility.data.config.WeaponConfig;
import net.karashokleo.l2hostility.data.config.WorldDifficultyConfig;
import net.karashokleo.l2hostility.data.provider.*;
import net.karashokleo.l2hostility.data.provider.cfg.EntityConfigProvider;
import net.karashokleo.l2hostility.data.provider.cfg.TraitConfigProvider;
import net.karashokleo.l2hostility.data.provider.cfg.WeaponConfigProvider;
import net.karashokleo.l2hostility.data.provider.cfg.WorldDifficultyConfigProvider;

public class LHData implements DataGeneratorEntrypoint
{
    public static TraitConfig traits;
    public static EntityConfig entities;
    public static WeaponConfig weapons;
    public static WorldDifficultyConfig difficulties;

    public static void clear()
    {
        traits = new TraitConfig();
        entities = new EntityConfig();
        weapons = new WeaponConfig();
        difficulties = new WorldDifficultyConfig();
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        FabricDataGenerator.Pack pack=fabricDataGenerator.createPack();

        pack.addProvider(EntityConfigProvider::new);
        pack.addProvider(TraitConfigProvider::new);
        pack.addProvider(WeaponConfigProvider::new);
        pack.addProvider(WorldDifficultyConfigProvider::new);

        pack.addProvider(EN_US_LangProvider::new);
//        pack.addProvider(ZH_CN_LangProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(TagEntityTypeProvider::new);
        pack.addProvider(TagItemProvider::new);
        pack.addProvider(TraitTagProvider::new);

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

//    public static <T extends LivingEntity> void addEntity(ConfigDataProvider.Collector collector, int min, int base, RegistryObject<EntityType<T>> obj, EntityConfig.TraitBase... traits) {
//        collector.add(L2Hostility.ENTITY, obj.getId(), new EntityConfig().putEntity(min, base, 0, 0, List.of(obj.get()), List.of(traits)));
//    }
//
//    public static <T extends LivingEntity> void addEntity(ConfigDataProvider.Collector collector, int min, int base, RegistryObject<EntityType<T>> obj, List<EntityConfig.TraitBase> traits, List<EntityConfig.ItemPool> items) {
//        collector.add(L2Hostility.ENTITY, obj.getId(), new EntityConfig().putEntityAndItem(min, base, 0, 0, List.of(obj.get()), traits, items));
}
