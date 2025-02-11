package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.entity.HostilityBulletEntity;
import karashokleo.l2hostility.content.entity.fireball.*;
import karashokleo.leobrary.datagen.builder.NamedEntryBuilder;
import karashokleo.leobrary.datagen.builder.provider.DefaultLanguageGeneratorProvider;
import karashokleo.leobrary.datagen.builder.provider.TagGeneratorProvider;
import karashokleo.leobrary.datagen.util.StringUtil;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagKey;

public class LHEntities
{
    public static EntityType<HostilityBulletEntity> HOSTILITY_BULLET;
    public static EntityType<HostilityFireballEntity> HOSTILITY_FIREBALL;
    public static EntityType<SoulFireballEntity> SOUL_FIREBALL;
    public static EntityType<StrongFireballEntity> STRONG_FIREBALL;
    public static EntityType<BlackFireballEntity> BLACK_FIREBALL;
    public static EntityType<WitchFireballEntity> WITCH_FIREBALL;
    public static EntityType<EternalWitchFireballEntity> ETERNAL_WITCH_FIREBALL;

    public static void register()
    {
        HOSTILITY_BULLET = Entry.of(
                        "hostility_bullet",
                        FabricEntityTypeBuilder.<HostilityBulletEntity>create()
                                .entityFactory(HostilityBulletEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(0.3125f, 0.3125f))
                                .trackRangeChunks(8)
                                .build()
                )
                .addEN()
                .addZH("恶意潜影弹")
                .register();
        HOSTILITY_FIREBALL = Entry.of(
                        "hostility_fireball",
                        FabricEntityTypeBuilder.<HostilityFireballEntity>create()
                                .entityFactory(HostilityFireballEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addEN()
                .addZH("恶意火球")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
        SOUL_FIREBALL = Entry.of(
                        "soul_fire_charge",
                        FabricEntityTypeBuilder.<SoulFireballEntity>create()
                                .entityFactory(SoulFireballEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addEN()
                .addZH("魂炎弹")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
        STRONG_FIREBALL = Entry.of(
                        "strong_fire_charge",
                        FabricEntityTypeBuilder.<StrongFireballEntity>create()
                                .entityFactory(StrongFireballEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addEN()
                .addZH("爆炎弹")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
        BLACK_FIREBALL = Entry.of(
                        "black_fire_charge",
                        FabricEntityTypeBuilder.<BlackFireballEntity>create()
                                .entityFactory(BlackFireballEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addEN()
                .addZH("黑炎弹")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
        WITCH_FIREBALL = Entry.of(
                        "witch_charge",
                        FabricEntityTypeBuilder.<WitchFireballEntity>create()
                                .entityFactory(WitchFireballEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addEN()
                .addZH("嗜魔弹")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
        ETERNAL_WITCH_FIREBALL = Entry.of(
                        "eternal_witch_charge",
                        FabricEntityTypeBuilder.<EternalWitchFireballEntity>create()
                                .entityFactory(EternalWitchFireballEntity::new)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addEN()
                .addZH("永恒嗜魔弹")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
    }

    static class Entry<T extends EntityType<?>>
            extends NamedEntryBuilder<T>
            implements DefaultLanguageGeneratorProvider, TagGeneratorProvider
    {
        private Entry(String name, T entity)
        {
            super(name, entity);
        }

        public static <T extends EntityType<?>> Entry<T> of(String name, T entity)
        {
            return new Entry<>(name, entity);
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }

        public T register()
        {
            return Registry.register(Registries.ENTITY_TYPE, getId(), content);
        }

        public Entry<T> addEN()
        {
            return addEN(StringUtil.defaultName(name));
        }

        public Entry<T> addEN(String en)
        {
            this.getEnglishGenerator().addEntityType(content, en);
            return this;
        }

        public Entry<T> addZH(String zh)
        {
            this.getChineseGenerator().addEntityType(content, zh);
            return this;
        }

        public Entry<T> addTag(TagKey<EntityType<?>> key)
        {
            this.getTagGenerator(RegistryKeys.ENTITY_TYPE).getOrCreateContainer(key).add(getId());
            return this;
        }
    }
}
