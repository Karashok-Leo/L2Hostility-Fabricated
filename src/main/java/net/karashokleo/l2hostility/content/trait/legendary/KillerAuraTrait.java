package net.karashokleo.l2hostility.content.trait.legendary;

import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.content.item.TrinketItems;
import net.karashokleo.l2hostility.content.network.S2CKillerAura;
import net.karashokleo.l2hostility.init.*;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;

import java.util.List;

public class KillerAuraTrait extends LegendaryTrait
{
    // 传奇词条，使怪物能够大范围杀伤。
    // 怪物每 6 -【词条等级】秒对周围 6 格范围的玩家造成【词条等级】* 10 的魔法伤害并施加词条效果。
    public KillerAuraTrait()
    {
        super(Formatting.DARK_RED);
    }

    @Override
    public void serverTick(LivingEntity mob, int level)
    {
        int itv = LHConfig.common().traits.killerAuraInterval / level;
        int damage = LHConfig.common().traits.killerAuraDamage * level;
        int range = LHConfig.common().traits.killerAuraRange;
        if (mob.age % itv == 0)
        {
            var diff = MobDifficulty.get(mob);
            if (diff.isEmpty()) return;
            Box box = mob.getBoundingBox().expand(range);
            for (var e : mob.getWorld().getEntitiesByClass(LivingEntity.class, box, EntityPredicates.VALID_ENTITY))
                if (e instanceof PlayerEntity pl && !pl.getAbilities().creativeMode ||
                        e instanceof MobEntity target && target.getTarget() == mob ||
                        mob instanceof MobEntity mobmob && mobmob.getTarget() == e)
                {
                    if (e.distanceTo(mob) > range) continue;
                    if (TrinketCompat.hasItemInTrinket(e, TrinketItems.ABRAHADABRA)) continue;
                    e.damage(e.getDamageSources().create(LHDamageTypes.KILLER_AURA, null, mob), damage);
                }
            LHNetworking.toTracking(mob, new S2CKillerAura(mob));
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                mapLevel(i -> Text.literal(LHConfig.common().traits.killerAuraDamage * i + "")
                        .formatted(Formatting.AQUA)),
                Text.literal("" + LHConfig.common().traits.killerAuraRange)
                        .formatted(Formatting.AQUA),
                mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.killerAuraInterval * 5d / i) * 0.01 + "")
                        .formatted(Formatting.AQUA))
        ).formatted(Formatting.GRAY));
    }
}
