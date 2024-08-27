package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.compat.data.BoMDData;
import karashokleo.l2hostility.compat.data.IaFData;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.leobrary.datagen.generator.TagGenerator;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellPowerTags;

public class LHTags
{
    public static final TagKey<MobTrait> POTION = createTraitTag("potion_trait");
    public static final TagKey<Item> CHAOS = createItemTag("chaos_equipment");
    public static final TagKey<Item> FACE_SLOT = createItemTag(new Identifier("trinkets", "head/face"));
    public static final TagKey<Item> BACK_SLOT = createItemTag(new Identifier("trinkets", "chest/back"));
    public static final TagKey<Item> CHARM_SLOT = createItemTag(new Identifier("trinkets", "chest/charm"));
    public static final TagKey<Item> CURSE_SLOT = createItemTag(new Identifier("trinkets", "chest/hostility_curse"));
    public static final TagKey<Item> RING_SLOT = createItemTag(new Identifier("trinkets", "hand/ring"));
    public static final TagKey<Item> HAND_SLOT = createItemTag(new Identifier("trinkets", "hand/glove"));
    public static final TagKey<Item> OFF_RING_SLOT = createItemTag(new Identifier("trinkets", "offhand/ring"));
    public static final TagKey<Item> OFF_HAND_SLOT = createItemTag(new Identifier("trinkets", "offhand/glove"));
    public static final TagKey<Item> TRAIT_ITEM = createItemTag("trait_item");
    public static final TagKey<Item> NO_SEAL = createItemTag("no_seal");
    public static final TagKey<Item> ANTIBUILD_BAN = createItemTag("antibuild_ban");
    public static final TagKey<Item> DELICATE_BONE = createItemTag("delicate_bone");

    public static final TagKey<Enchantment> NO_DISPELL = createEnchantmentTag("no_dispell");

    public static final TagKey<StatusEffect> CLEANSE_BLACKLIST = createEffectTag("cleanse_blacklist");
    public static final TagKey<StatusEffect> WRATH_INVULNERABILITY = createEffectTag("wrath_invulnerability");

    public static final TagKey<DamageType> MAGIC = createDamageTypeTag("magic");

    public static final TagKey<EntityType<?>> BLACKLIST = createEntityTag("blacklist");
    public static final TagKey<EntityType<?>> WHITELIST = createEntityTag("whitelist");
    public static final TagKey<EntityType<?>> NO_SCALING = createEntityTag("no_scaling");
    public static final TagKey<EntityType<?>> NO_TRAIT = createEntityTag("no_trait");

    public static final TagKey<EntityType<?>> SEMIBOSS = createEntityTag("semiboss");
    public static final TagKey<EntityType<?>> NO_DROP = createEntityTag("no_drop");

    public static final TagKey<EntityType<?>> ARMOR_TARGET = createEntityTag("armor_target");
    public static final TagKey<EntityType<?>> MELEE_WEAPON_TARGET = createEntityTag("melee_weapon_target");
    public static final TagKey<EntityType<?>> RANGED_WEAPON_TARGET = createEntityTag("ranged_weapon_target");

    public static void init()
    {
        LHGenerators.ITEM_TAGS.getOrCreateContainer(DELICATE_BONE)
                .add(
                        Items.SCULK_CATALYST,
                        Items.SCULK_SHRIEKER
                );
        LHGenerators.ENCHANTMENT_TAGS.getOrCreateContainer(NO_DISPELL)
                .add(
                        Enchantments.UNBREAKING
                );
        LHGenerators.STATUS_EFFECT_TAGS.getOrCreateContainer(CLEANSE_BLACKLIST)
                .add(
                        StatusEffects.NIGHT_VISION,
                        StatusEffects.BAD_OMEN,
                        StatusEffects.HERO_OF_THE_VILLAGE,
                        StatusEffects.DOLPHINS_GRACE,
                        StatusEffects.CONDUIT_POWER,
                        StatusEffects.WATER_BREATHING
                );

        TagGenerator<EntityType<?>> entityTypeTags = LHGenerators.ENTITY_TYPE_TAGS;
        entityTypeTags.getOrCreateContainer(BLACKLIST);
        entityTypeTags.getOrCreateContainer(WHITELIST);
        entityTypeTags.getOrCreateContainer(NO_SCALING)
                .addOptionalTag(WHITELIST);
        entityTypeTags.getOrCreateContainer(NO_TRAIT)
                .addOptionalTag(BLACKLIST);
        entityTypeTags.getOrCreateContainer(NO_TRAIT)
                .add(EntityType.ENDERMITE);
        entityTypeTags.getOrCreateContainer(ARMOR_TARGET)
                .add(
                        EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK, EntityType.ZOMBIE_VILLAGER,
                        EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON,
                        EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN_BRUTE
                );
        entityTypeTags.getOrCreateContainer(SEMIBOSS)
                .add(
                        EntityType.WARDEN, EntityType.ELDER_GUARDIAN, EntityType.RAVAGER
                )
                .addOptionalTag(ConventionalEntityTypeTags.BOSSES);
        entityTypeTags.getOrCreateContainer(NO_DROP);
        entityTypeTags.getOrCreateContainer(MELEE_WEAPON_TARGET)
                .add(
                        EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK, EntityType.ZOMBIE_VILLAGER,
                        EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN_BRUTE,
                        EntityType.WITHER_SKELETON, EntityType.VINDICATOR
                );
        entityTypeTags.getOrCreateContainer(RANGED_WEAPON_TARGET)
                .add(
                        EntityType.SKELETON, EntityType.STRAY
                );
        LHGenerators.STATUS_EFFECT_TAGS.getOrCreateContainer(WRATH_INVULNERABILITY)
                .add(
                        StatusEffects.BLINDNESS,
                        StatusEffects.DARKNESS,
                        StatusEffects.NAUSEA,
                        StatusEffects.SLOWNESS,
                        StatusEffects.MINING_FATIGUE,
                        StatusEffects.WEAKNESS
                );

        LHGenerators.DAMAGE_TYPE_TAGS.getOrCreateContainer(MAGIC)
                .add(DamageTypes.MAGIC)
                .add(DamageTypes.INDIRECT_MAGIC)
                .add(DamageTypes.THORNS)
                .add(DamageTypes.WITHER)
                .add(DamageTypes.WITHER_SKULL)
                .add(DamageTypes.DRAGON_BREATH);

        if (FabricLoader.getInstance().isModLoaded("spell_power"))
            LHGenerators.DAMAGE_TYPE_TAGS.getOrCreateContainer(MAGIC)
                    .addOptionalTag(SpellPowerTags.DamageType.ALL);

        if (FabricLoader.getInstance().isModLoaded(BoMDData.COMPAT_MOD_ID))
        {
            entityTypeTags.getOrCreateContainer(SEMIBOSS)
                    .addOptional(
                            BoMDData.LICH,
                            BoMDData.OBSIDILITH,
                            BoMDData.GAUNTLET,
                            BoMDData.VOID_BLOSSOM
                    );
            entityTypeTags.getOrCreateContainer(WHITELIST)
                    .addOptional(
                            BoMDData.LICH,
                            BoMDData.OBSIDILITH,
                            BoMDData.GAUNTLET,
                            BoMDData.VOID_BLOSSOM
                    );
        }
        if (FabricLoader.getInstance().isModLoaded(IaFData.COMPAT_MOD_ID))
        {
            entityTypeTags.getOrCreateContainer(SEMIBOSS)
                    .addOptional(
                            IaFData.FIRE_DRAGON,
                            IaFData.ICE_DRAGON,
                            IaFData.LIGHTNING_DRAGON,
                            IaFData.DEATH_WORM,
                            IaFData.SEA_SERPENT
                    );
            entityTypeTags.getOrCreateContainer(WHITELIST)
                    .addOptional(
                            IaFData.FIRE_DRAGON,
                            IaFData.ICE_DRAGON,
                            IaFData.LIGHTNING_DRAGON,
                            IaFData.DEATH_WORM,
                            IaFData.SEA_SERPENT
                    );
        }
    }

//        if (ModList.get().isLoaded(TwilightForestMod.ID)) {
//            pvd.addTag(NO_DROP).addOptional(TFEntities.DEATH_TOME.getId());
//        }
//
//        if (ModList.get().isLoaded(Cataclysm.MOD_ID)) {
//            pvd.addTag(SEMIBOSS)
//                    .addOptional(ModEntities.ENDER_GOLEM.getId())
//                    .addOptional(ModEntities.ENDER_GUARDIAN.getId())
//                    .addOptional(ModEntities.NETHERITE_MONSTROSITY.getId())
//                    .addOptional(ModEntities.IGNIS.getId())
//                    .addOptional(ModEntities.THE_HARBINGER.getId())
//                    .addOptional(ModEntities.THE_LEVIATHAN.getId())
//                    .addOptional(ModEntities.AMETHYST_CRAB.getId())
//                    .addOptional(ModEntities.ANCIENT_REMNANT.getId());
//        }
//
//        if (ModList.get().isLoaded(AlexsCaves.MOD_ID)) {
//            pvd.addTag(SEMIBOSS)
//                    .addOptional(ACEntityRegistry.HULLBREAKER.getId());
//
//            pvd.addTag(WHITELIST)
//                    .addOptional(ACEntityRegistry.HULLBREAKER.getId())
//                    .addOptional(ACEntityRegistry.VALLUMRAPTOR.getId())
//                    .addOptional(ACEntityRegistry.GROTTOCERATOPS.getId())
//                    .addOptional(ACEntityRegistry.RELICHEIRUS.getId())
//                    .addOptional(ACEntityRegistry.SUBTERRANODON.getId())
//                    .addOptional(ACEntityRegistry.TREMORSAURUS.getId());
//        }


    public static TagKey<Item> createItemTag(Identifier id)
    {
        return TagKey.of(RegistryKeys.ITEM, id);
    }

    public static TagKey<Item> createItemTag(String id)
    {
        return createItemTag(L2Hostility.id(id));
    }

    public static TagKey<EntityType<?>> createEntityTag(String id)
    {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, L2Hostility.id(id));
    }

    public static TagKey<Enchantment> createEnchantmentTag(String id)
    {
        return TagKey.of(RegistryKeys.ENCHANTMENT, L2Hostility.id(id));
    }

    public static TagKey<StatusEffect> createEffectTag(String id)
    {
        return TagKey.of(RegistryKeys.STATUS_EFFECT, L2Hostility.id(id));
    }

    private static TagKey<DamageType> createDamageTypeTag(String id)
    {
        return TagKey.of(RegistryKeys.DAMAGE_TYPE, L2Hostility.id(id));
    }

    public static TagKey<MobTrait> createTraitTag(String id)
    {
        return TagKey.of(LHTraits.TRAIT_KEY, L2Hostility.id(id));
    }
}
