package karashokleo.l2hostility.data.generate;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.advancement.KillTraitCountTrigger;
import karashokleo.l2hostility.content.advancement.KillTraitEffectTrigger;
import karashokleo.l2hostility.content.advancement.KillTraitFlameTrigger;
import karashokleo.l2hostility.content.advancement.KillTraitsTrigger;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.l2hostility.init.LHAdvTexts;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.compat.patchouli.PatchouliHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;

import java.util.function.Consumer;

public class AdvancementProvider extends FabricAdvancementProvider
{
    public AdvancementProvider(FabricDataOutput output)
    {
        super(output);
    }

    public static Advancement simpleGet(Advancement parent, Item item, LHAdvTexts text, String path)
    {
        return simpleGet(parent, item, text, AdvancementFrame.TASK, path);
    }

    public static Advancement simpleGet(Advancement parent, Item item, LHAdvTexts text, AdvancementFrame frame, String path)
    {
        return Advancement.Builder.create()
                .parent(parent)
                .display(
                        item,
                        text.getTitle(),
                        text.getDesc(),
                        null,
                        frame,
                        true,
                        true,
                        false
                )
                .criterion("0", InventoryChangedCriterion.Conditions.items(item))
                .build(L2Hostility.id("hostility/" + path));
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer)
    {
        Advancement root = Advancement.Builder.create()
                .display(
                        LHTraits.ENDER.asItem(),
                        LHAdvTexts.ROOT.getTitle(),
                        LHAdvTexts.ROOT.getDesc(),
                        L2Hostility.id("textures/gui/advancements/backgrounds/hostility.png"),
                        AdvancementFrame.TASK,
                        false,
                        false,
                        false
                )
                .criterion("0", InventoryChangedCriterion.Conditions.items(ConsumableItems.HOSTILITY_ORB))
                .build(L2Hostility.id("hostility/root"));

        Advancement patchouli = Advancement.Builder.create()
                .parent(root)
                .display(
                        PatchouliHelper.book(MiscItems.GUIDE_BOOK),
                        LHAdvTexts.PATCHOULI.getTitle(),
                        LHAdvTexts.PATCHOULI.getDesc(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("0", InventoryChangedCriterion.Conditions.items(ConsumableItems.HOSTILITY_ORB))
                .rewards(new AdvancementRewards.Builder().addLoot(MiscItems.GUIDE_BOOK))
                .build(L2Hostility.id("hostility/patchouli"));

        Advancement detector = simpleGet(root, MiscItems.DETECTOR, LHAdvTexts.DETECTOR, "detector");

        Advancement glasses = simpleGet(detector, MiscItems.DETECTOR_GLASSES, LHAdvTexts.GLASSES, "glasses");

        Advancement kill_first = Advancement.Builder.create()
                .parent(root)
                .display(
                        Items.IRON_SWORD,
                        LHAdvTexts.KILL_FIRST.getTitle(),
                        LHAdvTexts.KILL_FIRST.getDesc(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitCountTrigger.condition(1))
                .build(L2Hostility.id("hostility/kill_first"));

        Advancement kill_5_traits = Advancement.Builder.create()
                .parent(kill_first)
                .display(
                        Items.DIAMOND_SWORD,
                        LHAdvTexts.KILL_5_TRAITS.getTitle(),
                        LHAdvTexts.KILL_5_TRAITS.getDesc(),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitCountTrigger.condition(5))
                .build(L2Hostility.id("hostility/kill_5_traits"));

        Advancement kill_10_traits = Advancement.Builder.create()
                .parent(kill_5_traits)
                .display(
                        Items.NETHERITE_SWORD,
                        LHAdvTexts.KILL_10_TRAITS.getTitle(),
                        LHAdvTexts.KILL_10_TRAITS.getDesc(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitCountTrigger.condition(10))
                .build(L2Hostility.id("hostility/kill_10_traits"));

        Advancement kill_tanky = Advancement.Builder.create()
                .parent(kill_first)
                .display(
                        LHTraits.PROTECTION.asItem(),
                        LHAdvTexts.KILL_TANKY.getTitle(),
                        LHAdvTexts.KILL_TANKY.getDesc(),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitsTrigger.condition(LHTraits.PROTECTION, LHTraits.TANK))
                .build(L2Hostility.id("hostility/kill_tanky"));

        Advancement kill_adaptive = Advancement.Builder.create()
                .parent(kill_tanky)
                .display(
                        LHTraits.ADAPTIVE.asItem(),
                        LHAdvTexts.KILL_ADAPTIVE.getTitle(),
                        LHAdvTexts.KILL_ADAPTIVE.getDesc(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitsTrigger.condition(LHTraits.PROTECTION, LHTraits.REGEN, LHTraits.TANK, LHTraits.ADAPTIVE))
                .build(L2Hostility.id("hostility/kill_adaptive"));

        Advancement kill_dementor = Advancement.Builder.create()
                .parent(kill_adaptive)
                .display(
                        LHTraits.DISPELL.asItem(),
                        LHAdvTexts.KILL_DEMENTOR.getTitle(),
                        LHAdvTexts.KILL_DEMENTOR.getDesc(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitsTrigger.condition(LHTraits.DEMENTOR, LHTraits.DISPELL))
                .build(L2Hostility.id("hostility/kill_dementor"));

        Advancement kill_ragnarok = Advancement.Builder.create()
                .parent(kill_dementor)
                .display(
                        LHTraits.RAGNAROK.asItem(),
                        LHAdvTexts.KILL_RAGNAROK.getTitle(),
                        LHAdvTexts.KILL_RAGNAROK.getDesc(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitsTrigger.condition(LHTraits.KILLER_AURA, LHTraits.RAGNAROK))
                .build(L2Hostility.id("hostility/kill_ragnarok"));

        Advancement effect_kill_regen = Advancement.Builder.create()
                .parent(kill_first)
                .display(
                        LHTraits.REGEN.asItem(),
                        LHAdvTexts.EFFECT_KILL_REGEN.getTitle(),
                        LHAdvTexts.EFFECT_KILL_REGEN.getDesc(),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitEffectTrigger.condition(LHTraits.REGEN, LHEffects.CURSE))
                .build(L2Hostility.id("hostility/effect_kill_regen"));

        Advancement effect_kill_adaptive = Advancement.Builder.create()
                .parent(effect_kill_regen)
                .display(
                        LHTraits.ADAPTIVE.asItem(),
                        LHAdvTexts.EFFECT_KILL_ADAPTIVE.getTitle(),
                        LHAdvTexts.EFFECT_KILL_ADAPTIVE.getDesc(),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitEffectTrigger.condition(LHTraits.ADAPTIVE, LHEffects.FLAME))
                .criterion("1", KillTraitEffectTrigger.condition(LHTraits.ADAPTIVE, StatusEffects.POISON))
                .criterion("2", KillTraitEffectTrigger.condition(LHTraits.ADAPTIVE, StatusEffects.WITHER))
                .criterion("3", KillTraitFlameTrigger.condition(LHTraits.ADAPTIVE, KillTraitFlameTrigger.Type.FLAME))
                .criteriaMerger(CriterionMerger.OR)
                .build(L2Hostility.id("hostility/effect_kill_adaptive"));

        Advancement effect_kill_undead = Advancement.Builder.create()
                .parent(effect_kill_adaptive)
                .display(
                        LHTraits.UNDYING.asItem(),
                        LHAdvTexts.EFFECT_KILL_ADAPTIVE.getTitle(),
                        LHAdvTexts.EFFECT_KILL_ADAPTIVE.getDesc(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitEffectTrigger.condition(LHTraits.UNDYING, LHEffects.CURSE))
                .build(L2Hostility.id("hostility/effect_kill_undead"));

        Advancement effect_kill_teleport = Advancement.Builder.create()
                .parent(effect_kill_undead)
                .display(
                        LHTraits.ENDER.asItem(),
                        LHAdvTexts.EFFECT_KILL_TELEPORT.getTitle(),
                        LHAdvTexts.EFFECT_KILL_TELEPORT.getDesc(),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("0", KillTraitEffectTrigger.condition(LHTraits.ENDER, LHEffects.STONE_CAGE))
                .build(L2Hostility.id("hostility/effect_kill_teleport"));

        Advancement ingot = simpleGet(kill_first, MiscItems.CHAOS.ingot(), LHAdvTexts.INGOT, "ingot");

        Advancement sloth = simpleGet(ingot, TrinketItems.CURSE_SLOTH, LHAdvTexts.SLOTH, AdvancementFrame.GOAL, "sloth");

        Advancement envy = simpleGet(ingot, TrinketItems.CURSE_ENVY, LHAdvTexts.ENVY, AdvancementFrame.GOAL, "envy");

        Advancement trait = Advancement.Builder.create()
                .parent(envy)
                .display(
                        LHTraits.TANK.asItem(),
                        LHAdvTexts.TRAIT.getTitle(),
                        LHAdvTexts.TRAIT.getDesc(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("0", InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(LHTags.TRAIT_ITEM).build()))
                .build(L2Hostility.id("hostility/trait"));

        Advancement greed = simpleGet(trait, TrinketItems.CURSE_GREED, LHAdvTexts.GREED, AdvancementFrame.GOAL, "greed");

        Advancement lust = simpleGet(trait, TrinketItems.CURSE_LUST, LHAdvTexts.LUST, AdvancementFrame.GOAL, "lust");

        Advancement gluttony = simpleGet(trait, TrinketItems.CURSE_GLUTTONY, LHAdvTexts.GLUTTONY, "gluttony");

        Advancement miracle = simpleGet(gluttony, MiscItems.MIRACLE.ingot(), LHAdvTexts.MIRACLE, "miracle");

        Advancement breed = Advancement.Builder.create()
                .parent(trait)
                .display(
                        LHTraits.REGEN.asItem(),
                        LHAdvTexts.BREED.getTitle(),
                        LHAdvTexts.BREED.getDesc(),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("0", ConsumeItemCriterion.Conditions.predicate(ItemPredicate.Builder.create().tag(LHTags.TRAIT_ITEM).build()))
                .build(L2Hostility.id("hostility/breed"));

        Advancement imagine_breaker = simpleGet(breed, TrinketItems.IMAGINE_BREAKER, LHAdvTexts.IMAGINE_BREAKER, AdvancementFrame.CHALLENGE, "imagine_breaker");

        Advancement wrath = simpleGet(miracle, TrinketItems.CURSE_WRATH, LHAdvTexts.WRATH, AdvancementFrame.CHALLENGE, "wrath");

        Advancement pride = simpleGet(miracle, TrinketItems.CURSE_PRIDE, LHAdvTexts.PRIDE, AdvancementFrame.CHALLENGE, "pride");

        Advancement abrahadabra = simpleGet(miracle, TrinketItems.ABRAHADABRA, LHAdvTexts.ABRAHADABRA, AdvancementFrame.CHALLENGE, "abrahadabra");

        consumer.accept(root);
        consumer.accept(patchouli);
        consumer.accept(detector);
        consumer.accept(glasses);
        consumer.accept(kill_first);
        consumer.accept(kill_5_traits);
        consumer.accept(kill_10_traits);
        consumer.accept(kill_tanky);
        consumer.accept(kill_adaptive);
        consumer.accept(kill_dementor);
        consumer.accept(kill_ragnarok);
        consumer.accept(effect_kill_regen);
        consumer.accept(effect_kill_adaptive);
        consumer.accept(effect_kill_undead);
        consumer.accept(effect_kill_teleport);
        consumer.accept(ingot);
        consumer.accept(sloth);
        consumer.accept(envy);
        consumer.accept(trait);
        consumer.accept(greed);
        consumer.accept(lust);
        consumer.accept(gluttony);
        consumer.accept(miracle);
        consumer.accept(breed);
        consumer.accept(imagine_breaker);
        consumer.accept(wrath);
        consumer.accept(pride);
        consumer.accept(abrahadabra);
    }
}
