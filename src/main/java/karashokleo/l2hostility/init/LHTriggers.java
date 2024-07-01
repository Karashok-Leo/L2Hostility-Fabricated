package karashokleo.l2hostility.init;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.advancement.*;
import net.minecraft.advancement.criterion.Criteria;

public class LHTriggers
{
    public static final KillTraitsTrigger KILL_TRAITS = new KillTraitsTrigger(L2Hostility.id("kill_trait"));
    public static final KillTraitLevelTrigger TRAIT_LEVEL = new KillTraitLevelTrigger(L2Hostility.id("trait_level"));
    public static final KillTraitCountTrigger TRAIT_COUNT = new KillTraitCountTrigger(L2Hostility.id("trait_count"));
    public static final KillTraitEffectTrigger TRAIT_EFFECT = new KillTraitEffectTrigger(L2Hostility.id("trait_effect"));
    public static final KillTraitFlameTrigger TRAIT_FLAME = new KillTraitFlameTrigger(L2Hostility.id("trait_flame"));

    public static void register()
    {
        Criteria.register(KILL_TRAITS);
        Criteria.register(TRAIT_LEVEL);
        Criteria.register(TRAIT_COUNT);
        Criteria.register(TRAIT_EFFECT);
        Criteria.register(TRAIT_FLAME);
    }
}
