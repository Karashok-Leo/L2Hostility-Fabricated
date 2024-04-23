package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEntityTypeTags;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class LHTags
{
    public static final TagKey<MobTrait> POTION = createTraitTag("potion_trait");
    public static final TagKey<Item> CHAOS = createItemTag("chaos_equipment");
    public static final TagKey<Item> FACE_SLOT = createItemTag(new Identifier("trinkets", "face"));
    public static final TagKey<Item> CHARM_SLOT = createItemTag(new Identifier("trinkets", "charm"));
    public static final TagKey<Item> RING_SLOT = createItemTag(new Identifier("trinkets", "ring"));
    public static final TagKey<Item> HAND_SLOT = createItemTag(new Identifier("trinkets", "hand"));
    public static final TagKey<Item> CURSE_SLOT = createItemTag(new Identifier("trinkets", "hostility_curse"));
    public static final TagKey<Item> BACK_SLOT = createItemTag(new Identifier("trinkets", "back"));
    public static final TagKey<Item> TRAIT_ITEM = createItemTag("trait_item");
    public static final TagKey<Item> NO_SEAL = createItemTag("no_seal");
    public static final TagKey<Item> ANTIBUILD_BAN = createItemTag("antibuild_ban");

    public static final TagKey<Enchantment> NO_DISPELL = createEnchantmentTag("no_dispell");

    public static final TagKey<StatusEffect> SKILL_EFFECT = createEffectTag("skill_effect");

    public static final TagKey<EntityType<?>> BLACKLIST = createEntityTag("blacklist");
    public static final TagKey<EntityType<?>> WHITELIST = createEntityTag("whitelist");
    public static final TagKey<EntityType<?>> NO_SCALING = createEntityTag("no_scaling");
    public static final TagKey<EntityType<?>> NO_TRAIT = createEntityTag("no_trait");

    public static final TagKey<EntityType<?>> SEMIBOSS = createEntityTag("semiboss");
    public static final TagKey<EntityType<?>> NO_DROP = createEntityTag("no_drop");

    public static final TagKey<EntityType<?>> ARMOR_TARGET = createEntityTag("armor_target");
    public static final TagKey<EntityType<?>> MELEE_WEAPON_TARGET = createEntityTag("melee_weapon_target");
    public static final TagKey<EntityType<?>> RANGED_WEAPON_TARGET = createEntityTag("ranged_weapon_target");

    public static void register()
    {
        LHData.ENTITY_TYPE_TAGS.add(
                BLACKLIST
        );
        LHData.ENTITY_TYPE_TAGS.add(
                WHITELIST
        );
        LHData.ENTITY_TYPE_TAGS.add(
                NO_SCALING,
                WHITELIST
        );
        LHData.ENTITY_TYPE_TAGS.add(
                NO_TRAIT,
                BLACKLIST
        );
        LHData.ENTITY_TYPE_TAGS.add(
                NO_TRAIT,
                EntityType.ENDERMITE
        );
        LHData.ENTITY_TYPE_TAGS.add(
                ARMOR_TARGET,
                EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK, EntityType.ZOMBIE_VILLAGER,
                EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON,
                EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN_BRUTE
        );
        LHData.ENTITY_TYPE_TAGS.add(
                SEMIBOSS,
                ConventionalEntityTypeTags.BOSSES
        );
        LHData.ENTITY_TYPE_TAGS.add(
                SEMIBOSS,
                EntityType.WARDEN, EntityType.ELDER_GUARDIAN, EntityType.RAVAGER
        );
        LHData.ENTITY_TYPE_TAGS.add(
                NO_DROP
        );
        LHData.ENTITY_TYPE_TAGS.add(
                MELEE_WEAPON_TARGET,
                EntityType.ZOMBIE, EntityType.DROWNED, EntityType.HUSK, EntityType.ZOMBIE_VILLAGER,
                EntityType.PIGLIN, EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN_BRUTE,
                EntityType.WITHER_SKELETON, EntityType.VINDICATOR
        );
        LHData.ENTITY_TYPE_TAGS.add(
                RANGED_WEAPON_TARGET,
                EntityType.SKELETON, EntityType.STRAY
        );
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
//        if (ModList.get().isLoaded(IceAndFire.MOD_ID)) {
//            pvd.addTag(SEMIBOSS)
//                    .addOptional(IafEntityRegistry.ICE_DRAGON.getId())
//                    .addOptional(IafEntityRegistry.FIRE_DRAGON.getId())
//                    .addOptional(IafEntityRegistry.LIGHTNING_DRAGON.getId())
//                    .addOptional(IafEntityRegistry.DEATH_WORM.getId())
//                    .addOptional(IafEntityRegistry.SEA_SERPENT.getId());
//
//            pvd.addTag(WHITELIST)
//                    .addOptional(IafEntityRegistry.ICE_DRAGON.getId())
//                    .addOptional(IafEntityRegistry.FIRE_DRAGON.getId())
//                    .addOptional(IafEntityRegistry.LIGHTNING_DRAGON.getId())
//                    .addOptional(IafEntityRegistry.DEATH_WORM.getId())
//                    .addOptional(IafEntityRegistry.SEA_SERPENT.getId());
//        }
//
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
//
//        if (ModList.get().isLoaded("bosses_of_mass_destruction")) {
//            pvd.addTag(SEMIBOSS)
//                    .addOptional(BMDEntities.LICH.getId())
//                    .addOptional(BMDEntities.GAUNTLET.getId())
//                    .addOptional(BMDEntities.OBSIDILITH.getId())
//                    .addOptional(BMDEntities.VOID_BLOSSOM.getId());
//
//            pvd.addTag(WHITELIST)
//                    .addOptional(BMDEntities.LICH.getId())
//                    .addOptional(BMDEntities.GAUNTLET.getId())
//                    .addOptional(BMDEntities.OBSIDILITH.getId())
//                    .addOptional(BMDEntities.VOID_BLOSSOM.getId());
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

    public static TagKey<MobTrait> createTraitTag(String id)
    {
        return TagKey.of(LHTraits.TRAIT_KEY, L2Hostility.id(id));
    }

//    public static void onEffTagGen(RegistrateTagsProvider.IntrinsicImpl<MobEffect> pvd)
//    {
//        pvd.addTag(TagGen.SKILL_EFFECT).add(LHEffects.ANTIBUILD.get());
//    }
}
