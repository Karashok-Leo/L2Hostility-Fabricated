package net.karashokleo.l2hostility.content.trait.legendary;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import net.karashokleo.l2hostility.init.data.LHDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Formatting;

public class DementorTrait extends LegendaryTrait
{
    // 传奇词条，使怪物免疫物理伤害并无视护甲。
    // 怪物将免疫物理攻击，攻击时造成无视护甲的魔法伤害。
    public DementorTrait()
    {
        super(Formatting.DARK_GRAY);
    }

//    @Override
//    public void onCreateSource(int level, LivingEntity attacker, CreateSourceEvent event)
//    {
//        if (event.getResult() == L2DamageTypes.MOB_ATTACK)
//            event.enable(DefaultDamageState.BYPASS_ARMOR);
//    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        DamageSource source = event.getSource();
        if (LHDamageTypes.isMagic(source) ||
                LHDamageTypes.bypasses(source)) return;
        event.setCanceled(true);
    }
}
