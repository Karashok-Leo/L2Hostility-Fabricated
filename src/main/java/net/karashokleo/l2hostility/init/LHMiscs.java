package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.recipe.EnchantmentIngredient;
import net.karashokleo.l2hostility.content.screen.equipment.EquipmentScreenHandler;
import net.karashokleo.leobrary.datagen.builder.ItemGroupBuilder;
import net.karashokleo.leobrary.datagen.generator.LanguageGenerator;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class LHMiscs
{
    public static final ItemGroupBuilder GROUP = GroupEntry.of("hostility");
    public static final ItemGroupBuilder TRAITS = GroupEntry.of("traits");

    public static final ScreenHandlerType<EquipmentScreenHandler> EQUIPMENTS = new ExtendedScreenHandlerType<>(EquipmentScreenHandler::fromNetwork);

    public static EntityAttribute ADD_LEVEL = new ClampedEntityAttribute("extra_difficulty", 0, 0, 1000).setTracked(true);

    public static void register()
    {
        CustomIngredientSerializer.register(EnchantmentIngredient.SERIALIZER);
        Registry.register(
                Registries.ATTRIBUTE,
                L2Hostility.id("level"),
                ADD_LEVEL
        );
        GROUP.setIcon(() -> LHTraits.ENDER.asItem().getDefaultStack())
                .addEN("L2 Hostility")
                .addZH("莱特兰-恶意")
                .register();
        TRAITS.setIcon(() -> LHTraits.FIERY.asItem().getDefaultStack())
                .addEN("L2 Hostility - Traits")
                .addZH("莱特兰-恶意-词条")
                .register();
        Registry.register(Registries.SCREEN_HANDLER, L2Hostility.id("equipments"), EQUIPMENTS);
//        Registry.register(Registries.SCREEN_HANDLER, L2Hostility.id("trinkets"), TRINKETS);
    }

    static class GroupEntry extends ItemGroupBuilder
    {
        public static GroupEntry of(String name)
        {
            return new GroupEntry(name);
        }

        public GroupEntry(String name)
        {
            super(name);
        }

        @Override
        public @Nullable LanguageGenerator getEnglishGenerator()
        {
            return LHData.EN_TEXTS;
        }

        @Override
        public @Nullable LanguageGenerator getChineseGenerator()
        {
            return LHData.ZH_TEXTS;
        }

        @Override
        protected String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
