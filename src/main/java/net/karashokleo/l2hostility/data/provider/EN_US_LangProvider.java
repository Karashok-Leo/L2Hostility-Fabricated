package net.karashokleo.l2hostility.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class EN_US_LangProvider extends FabricLanguageProvider
{
    private static final Map<Item, String> items = new HashMap<>();
    private static final Map<MobTrait, String> traits = new HashMap<>();
    private static final Map<MobTrait, String> descs = new HashMap<>();

    public static void addItem(Item item, String s)
    {
        items.put(item, s);
    }

    public static void addTrait(MobTrait trait, String s)
    {
        traits.put(trait, s);
    }

    public static void addTraitDesc(MobTrait trait, String s)
    {
        descs.put(trait, s);
    }

    public EN_US_LangProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder)
    {
        generateItem(builder);
        generateTraits(builder);
        generateDescs(builder);
        generateTexts(builder);
    }

    private static void generateItem(TranslationBuilder builder)
    {
        for (Map.Entry<Item, String> entry : items.entrySet())
            builder.add(entry.getKey(), entry.getValue());
    }

    private static void generateTraits(TranslationBuilder builder)
    {
        for (Map.Entry<MobTrait, String> entry : traits.entrySet())
            builder.add(entry.getKey().getNameKey(), entry.getValue());
    }

    private static void generateDescs(TranslationBuilder builder)
    {
        for (Map.Entry<MobTrait, String> entry : descs.entrySet())
            builder.add(entry.getKey().getDescKey(), entry.getValue());
    }

    private static void generateTexts(TranslationBuilder builder)
    {
        for (LHTexts text : LHTexts.values())
            builder.add(L2Hostility.MOD_ID + "." + text.id, text.def);
        builder.add("config.jade.plugin_l2hostility.mob", "L2Hostility");
        builder.add("death.attack.killer_aura", "%s was killed by killer aura");
        builder.add("death.attack.killer_aura.player", "%s was killed by %s's killer aura");
        builder.add("curios.identifier.hostility_curse", "L2Hostility - Curse");
        builder.add("curios.modifiers.hostility_curse", "When worn as Curse:");
    }
}
