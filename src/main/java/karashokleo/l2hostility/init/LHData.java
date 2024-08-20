package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.config.ClientConfig;
import karashokleo.l2hostility.config.CommonConfig;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.data.config.DifficultyConfig;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.data.config.loader.DifficultyConfigLoader;
import karashokleo.l2hostility.data.config.loader.EntityConfigLoader;
import karashokleo.l2hostility.data.config.loader.TraitConfigLoader;
import karashokleo.l2hostility.data.config.loader.WeaponConfigLoader;
import karashokleo.l2hostility.data.config.provider.EntityConfigProvider;
import karashokleo.l2hostility.data.config.provider.TraitConfigProvider;
import karashokleo.l2hostility.data.config.provider.WeaponConfigProvider;
import karashokleo.l2hostility.data.config.provider.WorldDifficultyConfigProvider;
import karashokleo.l2hostility.data.generate.AdvancementProvider;
import karashokleo.l2hostility.data.generate.RecipeProvider;
import karashokleo.l2hostility.data.generate.TraitGLMProvider;
import karashokleo.leobrary.compat.patchouli.PatchouliHelper;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorage;
import karashokleo.leobrary.datagen.util.IdUtil;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.resource.ResourceType;

import java.lang.reflect.Field;
import java.util.Set;

public class LHData implements DataGeneratorEntrypoint
{
    public static EntityConfig entities;
    public static TraitConfig traits;
    public static WeaponConfig weapons;
    public static DifficultyConfig difficulties;

    public static void register()
    {
        ResourceManagerHelper resourceManagerHelper = ResourceManagerHelper.get(ResourceType.SERVER_DATA);
        resourceManagerHelper.registerReloadListener(new EntityConfigLoader());
        resourceManagerHelper.registerReloadListener(new TraitConfigLoader());
        resourceManagerHelper.registerReloadListener(new WeaponConfigLoader());
        resourceManagerHelper.registerReloadListener(new DifficultyConfigLoader());
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        LHGenerators.ITEM_TAGS.add(
                LHTags.DELICATE_BONE,
                Items.SCULK_CATALYST,
                Items.SCULK_SHRIEKER
        );
        LHGenerators.ENCHANTMENT_TAGS.add(
                LHTags.NO_DISPELL,
                Enchantments.UNBREAKING
        );
        LHGenerators.STATUS_EFFECT_TAGS.add(
                LHTags.CLEANSE_BLACKLIST,
                StatusEffects.NIGHT_VISION,
                StatusEffects.BAD_OMEN,
                StatusEffects.HERO_OF_THE_VILLAGE,
                StatusEffects.DOLPHINS_GRACE,
                StatusEffects.CONDUIT_POWER,
                StatusEffects.WATER_BREATHING
        );
        LHGenerators.EMPTY_LOOTS.addLoot(MiscItems.GUIDE_BOOK, PatchouliHelper.loot(MiscItems.GUIDE_BOOK));

        generateConfigTexts();

        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(EntityConfigProvider::new);
        pack.addProvider(TraitConfigProvider::new);
        pack.addProvider(WeaponConfigProvider::new);
        pack.addProvider(WorldDifficultyConfigProvider::new);

        GeneratorStorage.generate(L2Hostility.MOD_ID, pack);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(TraitGLMProvider::new);
        PatchouliHelper.model(pack, MiscItems.GUIDE_BOOK);

//        if (ModList.get().isLoaded(TwilightForestMod.ID))
//            TFData.genConfig(collector);
    }

    private static void generateConfigTexts()
    {
        IdUtil configId = IdUtil.of("text.autoconfig." + L2Hostility.MOD_ID);
        configId.pushAndPop("category.", () ->
        {
            LHGenerators.EN_TEXTS.addText(configId.get("common"), "Common");
            LHGenerators.EN_TEXTS.addText(configId.get("client"), "Client");
        });
        configId.pushAndPop("option.", () ->
        {
            configId.pushAndPop("common", () -> generateConfigTexts(CommonConfig.class, configId));
            configId.pushAndPop("client", () -> generateConfigTexts(ClientConfig.class, configId));
        });
    }

    private static void generateConfigTexts(Class<?> clazz, IdUtil prefix)
    {
        prefix.pushPrefix(".");
        Set<Class<?>> inner = Set.of(clazz.getDeclaredClasses());
        for (Field field : clazz.getDeclaredFields())
        {
            String name = field.getName();
            prefix.pushPrefix(name);
            LHGenerators.EN_TEXTS.addText(prefix.getNamespace() + "." + prefix.getPrefix(), name);
            Class<?> fieldClazz = field.getType();
            if (inner.contains(fieldClazz))
                generateConfigTexts(fieldClazz, prefix);
            prefix.popPrefix();
        }
        prefix.popPrefix();
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder)
    {
        LHGenerators.DYNAMICS.register(registryBuilder);
    }
}
