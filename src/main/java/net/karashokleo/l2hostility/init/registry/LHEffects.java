package net.karashokleo.l2hostility.init.registry;

import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.effect.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class LHEffects
{
    public static final GravityEffect GRAVITY = new GravityEffect();
    public static final MoonwalkEffect MOONWALK = new MoonwalkEffect();
    public static final AntiBuildEffect ANTI_BUILD = new AntiBuildEffect();
    public static final FlameEffect FLAME = new FlameEffect();
    public static final IceEffect ICE = new IceEffect();
    public static final CurseEffect CURSE = new CurseEffect();
    public static final CleanseEffect CLEANSE = new CleanseEffect();
    public static final StoneCageEffect STONE_CAGE = new StoneCageEffect();

    public static void register()
    {
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("gravity"), GRAVITY);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("moonwalk"), MOONWALK);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("anti_build"), ANTI_BUILD);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("flame"), FLAME);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("ice"), ICE);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("curse"), CURSE);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("cleanse"), CLEANSE);
        Registry.register(Registries.STATUS_EFFECT, L2Hostility.id("stone_cage"), STONE_CAGE);
    }
}
