package net.karashokleo.l2hostility.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.karashokleo.l2hostility.L2Hostility;
import net.karashokleo.l2hostility.init.registry.LHItems;
import net.karashokleo.l2hostility.init.registry.LHTraits;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class AdvancementProvider extends FabricAdvancementProvider
{
    public AdvancementProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer)
    {
//        consumer.accept();
//
//        Advancement root = Advancement.Builder.create()
//                .display(
//                        LHTraits.ENDER.asItem(), // 显示的图标
//                        Text.translatable("advancements.l2hostility.advancements.hostility.root.title"), // 标题
//                        Text.translatable("advancements.l2hostility.advancements.hostility.root.description"), // 描述
//                        L2Hostility.id("textures/gui/advancements/backgrounds/hostility.png"), // 使用的背景图片
//                        AdvancementFrame.TASK, // 选项: TASK, CHALLENGE, GOAL
//                        false, // 在右上角显示
//                        false, // 在聊天框中提示
//                        false // 在进度页面里隐藏
//                )
//                // Criterion 中使用的第一个字符串是其他进度在需要 'requirements' 时引用的名字
//                .criterion("got_orb", InventoryChangedCriterion.Conditions.items(LHItems.HOSTILITY_ORB))
//                .build(consumer, "hostility/root");
//
//
//        var root = gen.new TabBuilder("hostility").root("root", LHTraits.ENDER.asItem(),
//                CriterionBuilder.item(LHItems.HOSTILITY_ORB),
//                "Welcome to L2Hostility", "Your survival guide").root();
//        root.root().patchouli(L2Hostility.REGISTRATE, CriterionBuilder.item(LHItems.HOSTILITY_ORB),
//                L2Hostility.id("hostility_guide"),
//                        "Intro to L2Hostility", "Read the hostility guide")
//
//                .root()
//
//                .create("detector", LHItems.DETECTOR,
//                        CriterionBuilder.item(LHItems.DETECTOR),
//                        "Safety Compass", "Obtain Hostility Detector")
//
//                .create("glasses", LHItems.DETECTOR_GLASSES,
//                        CriterionBuilder.item(LHItems.DETECTOR_GLASSES),
//                        "The Invisible Threats", "Obtain Detector Glasses to find out invisible mobs")
//
//                .root()
//
//                .create("kill_first", Items.IRON_SWORD,
//                        CriterionBuilder.one(KillTraitCountTrigger.ins(1)),
//                        "Worthy Opponent", "Kill a mob with traits")
//
//                .create("kill_5_traits", Items.DIAMOND_SWORD,
//                        CriterionBuilder.one(KillTraitCountTrigger.ins(5)),
//                        "Legendary Battle", "Kill a mob with 5 traits")
//                .type(FrameType.GOAL)
//
//                .create("kill_10_traits", Items.NETHERITE_SWORD,
//                        CriterionBuilder.one(KillTraitCountTrigger.ins(10)),
//                        "Legend Slayer", "Kill a mob with 10 traits")
//                .type(FrameType.CHALLENGE)
//                .root().enter()
//
//                .create("kill_tanky", LHTraits.PROTECTION.asItem(),
//                        CriterionBuilder.one(KillTraitsTrigger.ins(
//                                LHTraits.PROTECTION, LHTraits.TANK)),
//                        "Can Opener", "Kill a mob with Protection and Tanky Trait")
//                .type(FrameType.GOAL)
//
//                .create("kill_adaptive", LHTraits.ADAPTIVE.asItem(),
//                        CriterionBuilder.one(KillTraitsTrigger.ins(
//                                LHTraits.PROTECTION, LHTraits.REGEN, LHTraits.TANK, LHTraits.ADAPTIVE)),
//                        "Counter-Defensive Measure", "Kill a mob with Protection, Regeneration, Tanky, and Adaptive Trait")
//                .type(FrameType.CHALLENGE)
//
//                .create("kill_dementor", LHTraits.DISPELL.asItem(),
//                        CriterionBuilder.one(KillTraitsTrigger.ins(
//                                LHTraits.DEMENTOR, LHTraits.DISPELL)),
//                        "Immunity Invalidator", "Kill a mob with Dementor and Dispell Trait")
//                .type(FrameType.CHALLENGE)
//
//                .create("kill_ragnarok", LHTraits.RAGNAROK.asItem(),
//                        CriterionBuilder.one(KillTraitsTrigger.ins(
//                                LHTraits.KILLER_AURA, LHTraits.RAGNAROK)),
//                        "The Final Battle", "Kill a mob with Killer Aura and Ragnarok Trait")
//                .type(FrameType.CHALLENGE)
//                .root().enter()
//
//                .create("effect_kill_regen", LHTraits.REGEN.asItem(),
//                        CriterionBuilder.one(KillTraitEffectTrigger.ins(
//                                LHTraits.REGEN, LCEffects.CURSE)),
//                        "Prevent Healing", "Use curse effect on mobs with Regeneration and kill it")
//                .type(FrameType.GOAL)
//
//                .create("effect_kill_adaptive", LHTraits.ADAPTIVE.asItem(),
//                        CriterionBuilder.or().add(KillTraitEffectTrigger.ins(
//                                        LHTraits.ADAPTIVE, LCEffects.FLAME))
//                                .add(KillTraitEffectTrigger.ins(
//                                        LHTraits.ADAPTIVE, MobEffects.POISON))
//                                .add(KillTraitEffectTrigger.ins(
//                                        LHTraits.ADAPTIVE, MobEffects.WITHER))
//                                .add(KillTraitFlameTrigger.ins(
//                                        LHTraits.ADAPTIVE, KillTraitFlameTrigger.Type.FLAME)),
//                        "Prevent Adaption", "Use poison/wither/soul flame effect or fire on mobs with Adaptive and kill it")
//                .type(FrameType.GOAL)
//
//                .create("effect_kill_undead", LHTraits.UNDYING.asItem(),
//                        CriterionBuilder.one(KillTraitEffectTrigger.ins(
//                                LHTraits.UNDYING, LCEffects.CURSE)),
//                        "Prevent Reviving", "Use curse effect on mobs with Undying and kill it")
//                .type(FrameType.CHALLENGE)
//
//                .create("effect_kill_teleport", LHTraits.ENDER.asItem(),
//                        CriterionBuilder.one(KillTraitEffectTrigger.ins(
//                                LHTraits.ENDER, LCEffects.STONE_CAGE)),
//                        "Prevent Teleporting", "Use incarceration effect on mobs with Teleport and kill it")
//                .type(FrameType.CHALLENGE);
//        var ingot = root.root().enter()
//
//                .create("ingot", LHItems.CHAOS_INGOT,
//                CriterionBuilder.item(LHItems.CHAOS_INGOT),
//                "Pandora's Box", "Obtain a Chaos Ingot");
//        ingot
//                .create("sloth", LHItems.CURSE_SLOTH,
//                CriterionBuilder.item(LHItems.CURSE_SLOTH),
//                "I want a break", "Obtain Curse of Sloth").type(FrameType.GOAL);
//
//        var trait = ingot
//                .create("envy", LHItems.CURSE_ENVY,
//                        CriterionBuilder.item(LHItems.CURSE_ENVY),
//                        "I want that!", "Obtain Curse of Envy")
//                .type(FrameType.GOAL)
//
//                .create("trait", LHTraits.TANK.asItem(),
//                        CriterionBuilder.item(LHTagGen.TRAIT_ITEM),
//                        "Gate to the New World", "Obtain a trait item");
//        trait
//                .create("greed", LHItems.CURSE_GREED,
//                CriterionBuilder.item(LHItems.CURSE_GREED),
//                "The More the Better", "Obtain Curse of Greed").type(FrameType.GOAL);
//        trait
//                .create("lust", LHItems.CURSE_LUST,
//                CriterionBuilder.item(LHItems.CURSE_LUST),
//                "Naked Corpse", "Obtain Curse of Lust").type(FrameType.GOAL);
//
//        var miracle = trait
//                .create("gluttony", LHItems.CURSE_GLUTTONY,
//                        CriterionBuilder.item(LHItems.CURSE_GLUTTONY),
//                        "Hostility Unlimited", "Obtain Curse of Gluttony")
//
//                .create("miracle", LHItems.MIRACLE_INGOT,
//                        CriterionBuilder.item(LHItems.MIRACLE_INGOT),
//                        "Miracle of the World", "Obtain Miracle Ingot");
//
//        trait
//                .create("breed", LHTraits.REGEN.asItem(),
//                        CriterionBuilder.one(ConsumeItemTrigger.TriggerInstance.usedItem(
//                                ItemPredicate.Builder.item().of(LHTagGen.TRAIT_ITEM).build())),
//                        "Breeding Mobs", "Use a trait item on mobs")
//
//                .create("imagine_breaker", LHItems.IMAGINE_BREAKER.asStack(),
//                        CriterionBuilder.item(LHItems.IMAGINE_BREAKER),
//                        "Reality Breakthrough", "Obtain Imagine Breaker").type(FrameType.CHALLENGE);
//
//        miracle
//                .create("wrath", LHItems.CURSE_WRATH,
//                CriterionBuilder.item(LHItems.CURSE_WRATH),
//                "Revenge Time", "Obtain Curse of Wrath").type(FrameType.CHALLENGE);
//
//        miracle
//                .create("pride", LHItems.CURSE_PRIDE,
//                CriterionBuilder.item(LHItems.CURSE_PRIDE),
//                "King of Hostility", "Obtain Curse of Pride").type(FrameType.CHALLENGE);
//
//        miracle
//                .create("abrahadabra", LHItems.ABRAHADABRA,
//                CriterionBuilder.item(LHItems.ABRAHADABRA),
//                "The Finale", "Obtain Abrahadabra").type(FrameType.CHALLENGE);
//
//        root.finish();
    }
}
