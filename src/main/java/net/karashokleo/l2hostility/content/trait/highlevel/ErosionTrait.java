package net.karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.content.item.TrinketItems;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.item.traits.DurabilityEater;
import net.karashokleo.l2hostility.init.LHItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ErosionTrait extends SlotIterateDamageTrait
{
    // 使怪物能够快速破坏目标的装备。
    // 怪物会随机选择【词条等级】个玩家的装备，消耗其【词条等级】* 10% 的当前耐久，每少一件提升【词条等级】* 25% 的伤害。
    public ErosionTrait()
    {
        super(Formatting.DARK_BLUE);
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        if (TrinketCompat.hasItemInTrinket(event.getEntity(), TrinketItems.ABRAHADABRA)) return;
        int count = process(level, entity, event.getEntity());
        if (count < level)
            event.setAmount(event.getAmount() * (float) (LHConfig.common().traits.erosionDamage * level * (level - count)));
    }

    @Override
    protected void perform(LivingEntity target, EquipmentSlot slot)
    {
        DurabilityEater.erosion(target, slot);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal(i + "")
                                .formatted(Formatting.AQUA)),
                        mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.erosionDurability * i * 100) + "%")
                                .formatted(Formatting.AQUA)),
                        mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.erosionDamage * i * 100) + "%")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
