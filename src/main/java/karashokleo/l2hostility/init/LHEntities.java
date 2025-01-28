package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.entity.HostilityBulletEntity;
import karashokleo.l2hostility.content.entity.HostilityFireBallEntity;
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
    public static EntityType<HostilityFireBallEntity> HOSTILITY_FIREBALL;

    public static void register()
    {
        HOSTILITY_BULLET = Entry.of(
                        "hostility_bullet",
                        FabricEntityTypeBuilder.create()
                                .entityFactory(HostilityBulletEntity::typeFactory)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(0.3125f, 0.3125f))
                                .trackRangeChunks(8)
                                .build()
                )
                .addZH("恶意潜影弹")
                .register();

        HOSTILITY_FIREBALL = Entry.of(
                        "hostility_fireball",
                        FabricEntityTypeBuilder.create()
                                .entityFactory(HostilityFireBallEntity::typeFactory)
                                .spawnGroup(SpawnGroup.MISC)
                                .dimensions(EntityDimensions.changing(1.0f, 1.0f))
                                .trackRangeChunks(4)
                                .trackedUpdateRate(10)
                                .build()
                )
                .addZH("恶意火球")
                .addTag(EntityTypeTags.IMPACT_PROJECTILES)
                .register();
    }

    static class Entry<T extends EntityType<?>>
            extends NamedEntryBuilder<T>
            implements DefaultLanguageGeneratorProvider, TagGeneratorProvider
    {
        public static <T extends EntityType<?>> Entry<T> of(String name, T entity)
        {
            return new Entry<>(name, entity);
        }

        private Entry(String name, T entity)
        {
            super(name, entity);
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
