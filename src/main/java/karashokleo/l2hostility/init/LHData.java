package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.config.ClientConfig;
import karashokleo.l2hostility.config.CommonConfig;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.trait.base.MobTrait;
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
import karashokleo.leobrary.datagen.generator.*;
import karashokleo.leobrary.datagen.util.IdUtil;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.ResourceType;

import java.lang.reflect.Field;
import java.util.Set;

public class LHData implements DataGeneratorEntrypoint
{
    public static final DynamicRegistryGenerator<DamageType> DYNAMICS = new DynamicRegistryGenerator<>("LH Dynamic Registries", RegistryKeys.DAMAGE_TYPE);
    public static final LanguageGenerator EN_TEXTS = new LanguageGenerator("en_us");
    public static final LanguageGenerator ZH_TEXTS = new LanguageGenerator("zh_cn");
    public static final ModelGenerator MODELS = new ModelGenerator();
    public static final LootGenerator EMPTY_LOOTS = new LootGenerator(LootContextTypes.EMPTY);
    public static final BlockLootGenerator BLOCK_LOOTS = new BlockLootGenerator();
    public static final TagGenerator<Item> ITEM_TAGS = new TagGenerator<>(RegistryKeys.ITEM);
    public static final TagGenerator<Block> BLOCK_TAGS = new TagGenerator<>(RegistryKeys.BLOCK);
    public static final TagGenerator<MobTrait> TRAIT_TAGS = new TagGenerator<>(LHTraits.TRAIT_KEY);
    public static final TagGenerator<EntityType<?>> ENTITY_TYPE_TAGS = new TagGenerator<>(RegistryKeys.ENTITY_TYPE);
    public static final TagGenerator<Enchantment> ENCHANTMENT_TAGS = new TagGenerator<>(RegistryKeys.ENCHANTMENT);
    public static final TagGenerator<StatusEffect> STATUS_EFFECT_TAGS = new TagGenerator<>(RegistryKeys.STATUS_EFFECT);
    public static final DynamicTagGenerator<DamageType> DAMAGE_TYPE_TAGS = new DynamicTagGenerator<>(RegistryKeys.DAMAGE_TYPE);

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
        ITEM_TAGS.add(
                LHTags.DELICATE_BONE,
                Items.SCULK_CATALYST,
                Items.SCULK_SHRIEKER
        );
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
        EMPTY_LOOTS.addLoot(MiscItems.GUIDE_BOOK, PatchouliHelper.loot(MiscItems.GUIDE_BOOK));
        
        generateConfigTexts();

        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(EntityConfigProvider::new);
        pack.addProvider(TraitConfigProvider::new);
        pack.addProvider(WeaponConfigProvider::new);
        pack.addProvider(WorldDifficultyConfigProvider::new);

        DYNAMICS.generate(pack);
        EN_TEXTS.generate(pack);
        ZH_TEXTS.generate(pack);
        MODELS.generate(pack);
        EMPTY_LOOTS.generate(pack);
        BLOCK_LOOTS.generate(pack);
        ITEM_TAGS.generate(pack);
        BLOCK_TAGS.generate(pack);
        TRAIT_TAGS.generate(pack);
        ENTITY_TYPE_TAGS.generate(pack);
        ENCHANTMENT_TAGS.generate(pack);
        STATUS_EFFECT_TAGS.generate(pack);
        DAMAGE_TYPE_TAGS.generate(pack);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(AdvancementProvider::new);
        pack.addProvider(TraitGLMProvider::new);
        PatchouliHelper.model(pack, MiscItems.GUIDE_BOOK);

//        if (ModList.get().isLoaded(TwilightForestMod.ID))
//            TFData.genConfig(collector);
//        if (ModList.get().isLoaded("bosses_of_mass_destruction"))
//            BoMDData.genConfig(collector);

    }

    private static void generateConfigTexts()
    {
        IdUtil configId = IdUtil.of("text.autoconfig." + L2Hostility.MOD_ID);
        configId.pushAndPop("category.", () ->
        {
            EN_TEXTS.addText(configId.get("common"), "Common");
            EN_TEXTS.addText(configId.get("client"), "Client");
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
            EN_TEXTS.addText(prefix.getNamespace() + "." + prefix.getPrefix(), name);
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
        DYNAMICS.register(registryBuilder);
    }
}
