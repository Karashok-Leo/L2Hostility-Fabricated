package net.karashokleo.l2hostility.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class LHMiscs
{
    private static ItemGroup HOSTILITY;

    //    public static final MenuEntry<EquipmentsMenu> EQUIPMENTS =
//            REGISTRATE.menu("equipments", EquipmentsMenu::fromNetwork, () -> EquipmentsScreen::new)
//                    .register();
//
//    public static final MenuEntry<EntityCuriosListMenu> CURIOS =
//            REGISTRATE.menu("curios", EntityCuriosListMenu::fromNetwork, () -> EntityCuriosListScreen::new)
//                    .register();
//
    public static EntityAttribute ADD_LEVEL = new ClampedEntityAttribute("extra_difficulty", 0, 0, 1000).setTracked(true);

    public static void register()
    {
        ADD_LEVEL = Registry.register(
                Registries.ATTRIBUTE,
                L2Hostility.id("level"),
                ADD_LEVEL
        );
        HOSTILITY = Registry.register(
                Registries.ITEM_GROUP,
                L2Hostility.id("hostility"),
                FabricItemGroup.builder()
                        .icon(() -> LHTraits.ENDER.asItem().getDefaultStack())
                        .displayName(Text.translatable("itemGroup.l2hostility.hostility"))
                        .entries((displayContext, entries) -> entries.addAll(LHItems.TAB_ITEMS))
                        .build()
        );
    }
}
