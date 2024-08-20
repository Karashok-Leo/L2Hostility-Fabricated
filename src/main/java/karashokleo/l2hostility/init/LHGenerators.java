package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.leobrary.datagen.generator.*;
import karashokleo.leobrary.datagen.generator.init.GeneratorStorage;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.RegistryKeys;

public class LHGenerators implements GeneratorStorage
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
    public static final TagGenerator<DamageType> DAMAGE_TYPE_TAGS = new TagGenerator<>(RegistryKeys.DAMAGE_TYPE);

    @Override
    public String getModId()
    {
        return L2Hostility.MOD_ID;
    }
}
