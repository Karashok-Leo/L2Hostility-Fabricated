package net.karashokleo.l2hostility.init.registry;

import net.karashokleo.l2hostility.L2Hostility;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHMiscs
{
    //    public static final MenuEntry<EquipmentsMenu> EQUIPMENTS =
//            REGISTRATE.menu("equipments", EquipmentsMenu::fromNetwork, () -> EquipmentsScreen::new)
//                    .register();
//
//    public static final MenuEntry<EntityCuriosListMenu> CURIOS =
//            REGISTRATE.menu("curios", EntityCuriosListMenu::fromNetwork, () -> EntityCuriosListScreen::new)
//                    .register();
//
    public static final EntityAttribute ADD_LEVEL = new ClampedEntityAttribute("extra_difficulty", 0, 0, 1000).setTracked(true);

    public static void register()
    {
        Registry.register(Registries.ATTRIBUTE, L2Hostility.id("level"), ADD_LEVEL);
    }
}
