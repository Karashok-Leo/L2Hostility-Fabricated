package karashokleo.l2hostility.content.trait;

import karashokleo.l2hostility.content.item.traits.TraitSymbol;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.data.config.TraitConfig;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.builder.ItemBuilder;
import karashokleo.leobrary.datagen.builder.NamedEntryBuilder;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.ModelGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import static karashokleo.l2hostility.init.LHTraits.TRAIT_KEY;

public abstract class MobTraitBuilder<T extends MobTrait>
    extends NamedEntryBuilder<T>
    implements DefaultLanguageGeneratorProvider, TagGeneratorProvider, ModelGeneratorProvider
{
    protected final TraitConfig.Config config;
    protected String translationKey;

    protected MobTraitBuilder(String name, T trait, TraitConfig.Config config)
    {
        super(name, trait);
        this.config = config;
        this.addBlacklist().addWhitelist();
    }

    public abstract <I extends Item> BiFunction<String, I, ItemBuilder<I>> getItemBuilder();

    public abstract void generateConfig(MobTrait trait, TraitConfig.Config config);

    public T register()
    {
        Identifier id = getId();
        this.getItemBuilder()
            .apply(name, new TraitSymbol(new FabricItemSettings()))
            .addModel()
            .addTag(LHTags.TRAIT_ITEM)
            .register();
        this.generateConfig(content, config);
        return Registry.register(LHTraits.TRAIT, id, content);
    }

    public MobTraitBuilder<T> addEN()
    {
        return addEN(StringUtil.defaultName(name));
    }

    public MobTraitBuilder<T> addEN(String en)
    {
        this.getEnglishGenerator().addText(getTranslationKey(), en);
        return this;
    }

    public MobTraitBuilder<T> addENDesc(String en)
    {
        this.getEnglishGenerator().addText(getTranslationKey() + ".desc", en);
        return this;
    }

    public MobTraitBuilder<T> addZH(String zh)
    {
        this.getChineseGenerator().addText(getTranslationKey(), zh);
        return this;
    }

    public MobTraitBuilder<T> addZHDesc(String zh)
    {
        this.getChineseGenerator().addText(getTranslationKey() + ".desc", zh);
        return this;
    }

    public MobTraitBuilder<T> addTag(TagKey<MobTrait> key)
    {
        this.getTagGenerator(TRAIT_KEY).getOrCreateContainer(key).add(getId());
        return this;
    }

    @SafeVarargs
    public final MobTraitBuilder<T> addTag(TagKey<MobTrait>... keys)
    {
        TagGenerator<MobTrait> tagGenerator = this.getTagGenerator(TRAIT_KEY);
        for (TagKey<MobTrait> key : keys)
        {
            tagGenerator.getOrCreateContainer(key).add(getId());
        }
        return this;
    }

    public String getTranslationKey()
    {
        if (translationKey == null)
        {
            translationKey = getId().toTranslationKey(TRAIT_KEY.getValue().getPath());
        }
        return translationKey;
    }

    @Deprecated
    public MobTraitBuilder<T> configure(Consumer<TraitConfig.Config> consumer)
    {
        consumer.accept(config);
        return this;
    }

    public MobTraitBuilder<T> addBlacklist(TagKey<EntityType<?>> tag)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getBlacklistTag()).addTag(tag);
        return this;
    }

    public MobTraitBuilder<T> addBlacklistOptional(TagKey<EntityType<?>> tag)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getBlacklistTag()).addOptionalTag(tag);
        return this;
    }

    public MobTraitBuilder<T> addBlacklist(EntityType<?>... types)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getBlacklistTag()).add(types);
        return this;
    }

    public MobTraitBuilder<T> addBlacklistOptional(Identifier... types)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getBlacklistTag()).addOptional(types);
        return this;
    }

    public MobTraitBuilder<T> addWhitelist(TagKey<EntityType<?>> tag)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getWhitelistTag()).addTag(tag);
        return this;
    }

    public MobTraitBuilder<T> addWhitelistOptional(TagKey<EntityType<?>> tag)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getWhitelistTag()).addOptionalTag(tag);
        return this;
    }

    public MobTraitBuilder<T> addWhitelist(EntityType<?>... types)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getWhitelistTag()).add(types);
        return this;
    }

    public MobTraitBuilder<T> addWhitelistOptional(Identifier... types)
    {
        this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(config.getWhitelistTag()).addOptional(types);
        return this;
    }
}
