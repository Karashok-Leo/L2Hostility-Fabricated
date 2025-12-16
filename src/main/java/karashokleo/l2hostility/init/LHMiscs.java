package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.screen.equipment.EquipmentScreenHandler;
import karashokleo.leobrary.datagen.builder.ItemGroupBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class LHMiscs
{
    /// 这里的Group id加上一个前缀是为了在ItemGroups排序将两个Group排在相邻位置
    /// 因为ItemGroup的排序是根据Identifier来排序的，而Identifier优先比较Path的字典序
    public static final ItemGroupBuilder GROUP = GroupEntry.of("l2h_hostility");
    public static final ItemGroupBuilder TRAITS = GroupEntry.of("l2h_traits");

    public static final EntityAttribute ADD_LEVEL = new ClampedEntityAttribute("attribute.name.generic.extra_difficulty", 0, 0, 1000).setTracked(true);

    public static final ScreenHandlerType<EquipmentScreenHandler> EQUIPMENTS = new ExtendedScreenHandlerType<>(EquipmentScreenHandler::fromNetwork);

    public static void register()
    {
        GROUP.setIcon(() -> LHTraits.ENDER.asItem().getDefaultStack())
            .addEN("L2 Hostility")
            .addZH("莱特兰-恶意")
            .register();

        TRAITS.setIcon(() -> LHTraits.FIERY.asItem().getDefaultStack())
            .addEN("L2 Hostility - Traits")
            .addZH("莱特兰-恶意-词条")
            .register();

        Registry.register(
            Registries.ATTRIBUTE,
            L2Hostility.id("level"),
            ADD_LEVEL
        );

        Registry.register(Registries.SCREEN_HANDLER, L2Hostility.id("equipments"), EQUIPMENTS);
//        Registry.register(Registries.SCREEN_HANDLER, L2Hostility.id("trinkets"), TRINKETS);

        LHGenerators.EN_TEXTS.addAttribute(ADD_LEVEL, "Extra Difficulty");
        LHGenerators.ZH_TEXTS.addAttribute(ADD_LEVEL, "额外难度");
    }

    private static class GroupEntry extends ItemGroupBuilder
    {
        public GroupEntry(String name)
        {
            super(name);
        }

        public static GroupEntry of(String name)
        {
            return new GroupEntry(name);
        }

        @Override
        public String getNameSpace()
        {
            return L2Hostility.MOD_ID;
        }
    }
}
