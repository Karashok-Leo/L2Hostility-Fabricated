package net.karashokleo.l2hostility.data.generate;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ZH_CN_LangProvider extends FabricLanguageProvider
{
    private static final Map<Item, String> items = new HashMap<>();
    private static final Map<StatusEffect, String> effects = new HashMap<>();
    private static final Map<StatusEffect, String> effects_descs = new HashMap<>();
    private static final Map<Enchantment, String> enchantments = new HashMap<>();
    private static final Map<Enchantment, String> enchantments_descs = new HashMap<>();
    private static final Map<MobTrait, String> traits = new HashMap<>();
    private static final Map<MobTrait, String> traits_descs = new HashMap<>();

    public static void addItem(Item item, String s)
    {
        items.put(item, s);
    }

    public static void addEffect(StatusEffect effect, String s)
    {
        effects.put(effect, s);
    }

    public static void addEffectDesc(StatusEffect effect, String s)
    {
        effects_descs.put(effect, s);
    }

    public static void addEnchantment(Enchantment enchantment, String s)
    {
        enchantments.put(enchantment, s);
    }

    public static void addEnchantmentDesc(Enchantment enchantment, String s)
    {
        enchantments_descs.put(enchantment, s);
    }

    public static void addTrait(MobTrait trait, String s)
    {
        traits.put(trait, s);
    }

    public static void addTraitDesc(MobTrait trait, String s)
    {
        traits_descs.put(trait, s);
    }

    public ZH_CN_LangProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder)
    {
        for (Map.Entry<Item, String> entry : items.entrySet())
            builder.add(entry.getKey(), entry.getValue());
        for (Map.Entry<StatusEffect, String> entry : effects.entrySet())
            builder.add(entry.getKey(), entry.getValue());
        for (Map.Entry<StatusEffect, String> entry : effects_descs.entrySet())
            builder.add(entry.getKey().getTranslationKey() + ".desc", entry.getValue());
        for (Map.Entry<Enchantment, String> entry : enchantments.entrySet())
            builder.add(entry.getKey(), entry.getValue());
        for (Map.Entry<Enchantment, String> entry : enchantments_descs.entrySet())
            builder.add(entry.getKey().getTranslationKey() + ".desc", entry.getValue());
        for (Map.Entry<MobTrait, String> entry : traits.entrySet())
            builder.add(entry.getKey().getNameKey(), entry.getValue());
        for (Map.Entry<MobTrait, String> entry : traits_descs.entrySet())
            builder.add(entry.getKey().getDescKey(), entry.getValue());
        generateTexts(builder);
    }

    private static void generateTexts(TranslationBuilder builder)
    {
        for (LHTexts text : LHTexts.values())
            builder.add(L2Hostility.MOD_ID + "." + text.id, text.defZh);
        builder.add("config.jade.plugin_l2hostility.mob", "L2Hostility");
        builder.add("death.attack.killer_aura", "%s was killed by killer aura");
        builder.add("death.attack.killer_aura.player", "%s was killed by %s's killer aura");
        builder.add("curios.identifier.hostility_curse", "L2Hostility - Curse");
        builder.add("curios.modifiers.hostility_curse", "When worn as Curse:");
    }
}
