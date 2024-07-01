package karashokleo.l2hostility.init;

import karashokleo.leobrary.compat.patchouli.PatchouliHelper;
import karashokleo.leobrary.datagen.generator.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.DifficultyConfig;
import karashokleo.l2hostility.data.config.EntityConfig;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.data.config.WeaponConfig;
import karashokleo.l2hostility.data.config.loader.EntityConfigLoader;
import karashokleo.l2hostility.data.config.loader.TraitConfigLoader;
import karashokleo.l2hostility.data.config.loader.WeaponConfigLoader;
import karashokleo.l2hostility.data.config.loader.DifficultyConfigLoader;
import karashokleo.l2hostility.data.generate.*;
import karashokleo.l2hostility.data.config.provider.EntityConfigProvider;
import karashokleo.l2hostility.data.config.provider.TraitConfigProvider;
import karashokleo.l2hostility.data.config.provider.WeaponConfigProvider;
import karashokleo.l2hostility.data.config.provider.WorldDifficultyConfigProvider;
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

//        collector.add(L2DamageTracker.ARMOR, new ResourceLocation(L2Hostility.MODID, "equipments"), new ArmorEffectConfig()
//                .add(LHItems.CURSE_WRATH.getId().toString(),
//                        MobEffects.BLINDNESS, MobEffects.DARKNESS, MobEffects.CONFUSION,
//                        MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN, MobEffects.WEAKNESS));
//
//        if (ModList.get().isLoaded(TwilightForestMod.ID))
//            TFData.genConfig(collector);
//        if (ModList.get().isLoaded("bosses_of_mass_destruction"))
//            BoMDData.genConfig(collector);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder)
    {
        DYNAMICS.register(registryBuilder);
    }
}
