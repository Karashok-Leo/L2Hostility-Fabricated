package karashokleo.l2hostility.data.generate;

import com.mojang.serialization.Codec;
import io.github.fabricators_of_create.porting_lib.loot.GlobalLootModifierProvider;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ComplementItems;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.content.loot.TraitSerializer;
import karashokleo.l2hostility.content.loot.condition.MobHealthLootCondition;
import karashokleo.l2hostility.content.loot.condition.MobLevelLootCondition;
import karashokleo.l2hostility.content.loot.condition.PlayerHasItemLootCondition;
import karashokleo.l2hostility.content.loot.condition.TraitLootCondition;
import karashokleo.l2hostility.content.loot.modifier.EnvyLootModifier;
import karashokleo.l2hostility.content.loot.modifier.GluttonyLootModifier;
import karashokleo.l2hostility.content.loot.modifier.TraitLootModifier;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHPotions;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.datagen.util.LootTableTemplate;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class TraitGLMProvider extends GlobalLootModifierProvider
{
    public static LootConditionType TRAIT_AND_LEVEL;
    public static LootConditionType MOB_LEVEL;
    public static LootConditionType HAS_ITEM;
    public static LootConditionType MIN_HEALTH;
    public static Codec<TraitLootModifier> TRAIT_SCALED;
    public static Codec<EnvyLootModifier> LOOT_ENVY;
    public static Codec<GluttonyLootModifier> LOOT_GLUTTONY;

    public TraitGLMProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, L2Hostility.MOD_ID);
    }

    public static void register()
    {
        TRAIT_AND_LEVEL = Registry.register(Registries.LOOT_CONDITION_TYPE, L2Hostility.id("trait_and_level"), new LootConditionType(new TraitSerializer<>(TraitLootCondition.class)));
        MOB_LEVEL = Registry.register(Registries.LOOT_CONDITION_TYPE, L2Hostility.id("mob_level"), new LootConditionType(new TraitSerializer<>(MobLevelLootCondition.class)));
        HAS_ITEM = Registry.register(Registries.LOOT_CONDITION_TYPE, L2Hostility.id("player_has_item"), new LootConditionType(new TraitSerializer<>(PlayerHasItemLootCondition.class)));
        MIN_HEALTH = Registry.register(Registries.LOOT_CONDITION_TYPE, L2Hostility.id("min_health"), new LootConditionType(new TraitSerializer<>(MobHealthLootCondition.class)));

        TRAIT_SCALED = Registry.register(PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), L2Hostility.id("trait_scaled"), TraitLootModifier.CODEC);
        LOOT_ENVY = Registry.register(PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), L2Hostility.id("loot_envy"), EnvyLootModifier.CODEC);
        LOOT_GLUTTONY = Registry.register(PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(), L2Hostility.id("loot_gluttony"), GluttonyLootModifier.CODEC);
    }

    @Override
    protected void start()
    {
        var loot1 = TrinketItems.LOOT_1;
        var loot2 = TrinketItems.LOOT_2;
        var loot3 = TrinketItems.LOOT_3;
        var loot4 = TrinketItems.LOOT_4;

        add("loot_envy", new EnvyLootModifier(LootTableTemplate.byPlayer().build(),
            new PlayerHasItemLootCondition(TrinketItems.CURSE_ENVY)));
        add("loot_gluttony", new GluttonyLootModifier(LootTableTemplate.byPlayer().build(),
            new PlayerHasItemLootCondition(TrinketItems.CURSE_GLUTTONY)));

        add(LHTraits.TANK, loot1, new ItemStack(Items.DIAMOND, 4), 1, 0, 0.1);
        add(LHTraits.TANK, loot1, new ItemStack(Items.NETHERITE_SCRAP, 1), 3, 0, 0.1);
        add(LHTraits.SPEEDY, loot1, new ItemStack(Items.RABBIT_FOOT, 2), 1, 0, 0.1);
        add(LHTraits.SPEEDY, loot3, new ItemStack(ComplementItems.CAPTURED_WIND, 1), 3, 0, 0.1, 100);
        add(LHTraits.PROTECTION, loot1, new ItemStack(Items.SCUTE, 4), 1, 0, 0.1);
        add(LHTraits.PROTECTION, loot1, new ItemStack(Items.SHULKER_SHELL, 1), 3, 0, 0.1);
        add(LHTraits.INVISIBLE, loot1, new ItemStack(Items.PHANTOM_MEMBRANE, 4), 1, 0.25, 0);
        add(LHTraits.FIERY, loot1, new ItemStack(Items.BLAZE_ROD, 8), 1, 0.25, 0);
        add(LHTraits.REGEN, loot1, new ItemStack(Items.GHAST_TEAR, 4), 1, 0, 0.1);
        add(LHTraits.REGEN, loot3, new ItemStack(ComplementItems.TOTEMIC_GOLD.nugget(), 4), 3, 0, 0.1);
        add(LHTraits.REGEN, loot2, new ItemStack(ComplementItems.LIFE_ESSENCE, 1), 3, -0.2, 0.08);
        add(LHTraits.ADAPTIVE, loot2, new ItemStack(ComplementItems.CURSED_DROPLET, 1), 1, 0, 0.1);
        add(LHTraits.REFLECT, loot2, new ItemStack(ComplementItems.EXPLOSION_SHARD, 1), 1, 0, 0.1);
        add(LHTraits.DEMENTOR, loot4, new ItemStack(ComplementItems.SUN_MEMBRANE, 1), 1, 0.2, 0.1);
        add(LHTraits.DISPELL, loot4, new ItemStack(ComplementItems.RESONANT_FEATHER, 1), 1, 0.2, 0.1);
        add(LHTraits.UNDYING, loot1, new ItemStack(Items.TOTEM_OF_UNDYING, 1), 1, 1, 0);
        add(LHTraits.UNDYING, loot2, new ItemStack(ComplementItems.LIFE_ESSENCE, 1), 1, 0.5, 0);
        add(LHTraits.ENDER, loot4, new ItemStack(ComplementItems.VOID_EYE, 1), 1, 0.2, 0.1);
        add(LHTraits.REPELLING, loot3, new ItemStack(ComplementItems.FORCE_FIELD, 1), 1, 0.2, 0.1);

        add(LHTraits.WEAKNESS, loot1, new ItemStack(Items.FERMENTED_SPIDER_EYE, 8), 1, 0, 0.1);
        add(LHTraits.SLOWNESS, loot1, new ItemStack(Items.COBWEB, 4), 1, 0, 0.1);
        add(LHTraits.POISON, loot1, new ItemStack(Items.SPIDER_EYE, 8), 1, 0, 0.1);
        add(LHTraits.WITHER, loot1, new ItemStack(Items.WITHER_ROSE, 8), 1, 0, 0.1);
        add(LHTraits.WITHER, loot1, new ItemStack(Items.WITHER_SKELETON_SKULL, 1), 3, 0, 0.1);
        add(LHTraits.LEVITATION, loot2, new ItemStack(ComplementItems.CAPTURED_BULLET, 1), 1, 0, 0.1);
        add(LHTraits.BLIND, loot1, new ItemStack(Items.INK_SAC, 8), 1, 0, 0.1);
        add(LHTraits.CONFUSION, loot1, new ItemStack(Items.PUFFERFISH, 4), 1, 0, 0.1);
        add(LHTraits.SOUL_BURNER, loot2, new ItemStack(ComplementItems.SOUL_FLAME, 2), 1, 0, 0.1);
        add(LHTraits.FREEZING, loot2, new ItemStack(ComplementItems.HARD_ICE, 2), 1, 0, 0.1);
        add(LHTraits.CURSED, loot1, PotionUtil.setPotion(Items.POTION.getDefaultStack(), LHPotions.CURSE.potion()), 1, 0, 0.2);
        add(LHTraits.CURSED, loot2, new ItemStack(ComplementItems.CURSED_DROPLET, 1), 3, 0, 0.05);
        add(LHTraits.CORROSION, loot2, new ItemStack(ComplementItems.CURSED_DROPLET, 1), 1, 0, 0.1);
        add(LHTraits.EROSION, loot2, new ItemStack(ComplementItems.CURSED_DROPLET, 1), 1, 0, 0.1);
        add(LHTraits.KILLER_AURA, loot4, new ItemStack(ComplementItems.EMERALD, 1), 1, 0, 0.02);
        add(LHTraits.RAGNAROK, loot4, new ItemStack(ComplementItems.ETERNIUM.nugget(), 4), 1, 0, 0.1);
        add(LHTraits.SHULKER, loot2, new ItemStack(ComplementItems.CAPTURED_BULLET, 1), 1, 0, 0.2);
        add(LHTraits.GRENADE, loot3, new ItemStack(ComplementItems.STORM_CORE, 1), 3, 0, 0.1);
        add(LHTraits.GRENADE, loot1, new ItemStack(Items.GUNPOWDER, 4), 1, 0, 0.1);
        add(LHTraits.GRENADE, loot1, new ItemStack(Items.CREEPER_HEAD, 1), 5, 0.25, 0);
        add(LHTraits.DRAIN, loot2, new ItemStack(MiscItems.WITCH_DROPLET, 1), 1, 0, 0.1);
        add(LHTraits.GROWTH, loot1, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1), 1, 0, 0.02);
        add(LHTraits.SPLIT, loot2, new ItemStack(ComplementItems.GUARDIAN_EYE, 1), 1, 0, 0.05);
        add(LHTraits.GRAVITY, loot1, new ItemStack(Items.DRAGON_BREATH, 4), 1, 0, 0.1);
        add(LHTraits.MOONWALK, loot1, new ItemStack(Items.DRAGON_BREATH, 4), 1, 0, 0.1);
        add(LHTraits.STRIKE, loot2, new ItemStack(ComplementItems.EXPLOSION_SHARD, 1), 1, 0, 0.1);
        add(LHTraits.PULLING, loot3, new ItemStack(ComplementItems.BLACKSTONE_CORE, 1), 1, 0, 0.05);

        add(LHTraits.REPRINT, loot3, new ItemStack(ConsumableItems.BOOK_COPY), 1, 1, 0);
        add(LHTraits.DISPELL, loot4, new ItemStack(TrinketItems.IMAGINE_BREAKER), 3, 1, 0);

        add(LHTraits.TANK, new ItemStack(ComplementItems.SHULKERATE.nugget(), 6), 0, 0.1,
            LootTableTemplate.byPlayer().build(),
            new TraitLootCondition(LHTraits.TANK, 3, 5),
            new TraitLootCondition(LHTraits.PROTECTION, 1, 3),
            new PlayerHasItemLootCondition(loot2)
        );
        add(LHTraits.TANK, new ItemStack(ComplementItems.SHULKERATE.ingot(), 2), 0, 0.1,
            LootTableTemplate.byPlayer().build(),
            new TraitLootCondition(LHTraits.TANK, 3, 5),
            new TraitLootCondition(LHTraits.PROTECTION, 4, 5),
            new PlayerHasItemLootCondition(loot2)
        );
        add(LHTraits.SPEEDY, new ItemStack(ComplementItems.SCULKIUM.nugget(), 4), 0, 0.1,
            LootTableTemplate.byPlayer().build(),
            new TraitLootCondition(LHTraits.SPEEDY, 3, 5),
            new TraitLootCondition(LHTraits.TANK, 3, 5),
            new PlayerHasItemLootCondition(loot3)
        );

        add(LHTraits.DEMENTOR, new ItemStack(MiscItems.CHAOS.ingot(), 1), 1, 0,
            LootTableTemplate.byPlayer().build(),
            new TraitLootCondition(LHTraits.KILLER_AURA, 1, 5),
            new TraitLootCondition(LHTraits.RAGNAROK, 1, 5),
            new PlayerHasItemLootCondition(loot4)
        );
    }

    @SuppressWarnings("SameParameterValue")
    private void add(MobTrait trait, Item curio, ItemStack stack, int start, double chance, double bonus, int min)
    {
        add(
            trait, stack, chance, bonus,
            LootTableTemplate.byPlayer().build(),
            new TraitLootCondition(trait, start, 5),
            new MobLevelLootCondition(min),
            new PlayerHasItemLootCondition(curio)
        );
    }

    private void add(MobTrait trait, Item curio, ItemStack stack, int start, double chance, double bonus)
    {
        add(
            trait, stack, chance, bonus,
            LootTableTemplate.byPlayer().build(),
            new TraitLootCondition(trait, start, 5),
            new PlayerHasItemLootCondition(curio)
        );
    }

    private void add(MobTrait trait, ItemStack stack, double chance, double bonus, LootCondition... conditions)
    {
        add(
            trait.getNonNullId().getPath() + "_drop_" + Registries.ITEM.getId(stack.getItem()).getPath(),
            new TraitLootModifier(trait, chance, bonus, stack, conditions)
        );
    }
}
