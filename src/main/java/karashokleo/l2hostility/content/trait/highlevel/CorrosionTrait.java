package karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.content.item.traits.DurabilityEater;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CorrosionTrait extends SlotIterateDamageTrait
{
    // 使怪物能够快速破坏目标的装备。
    // 怪物会随机选择【词条等级】个玩家的装备，消耗其【词条等级】* 30% 的已损失耐久，每少一件提升【词条等级】* 25% 的伤害。
    public CorrosionTrait()
    {
        super(Formatting.DARK_RED);
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        int count = process(level, entity, event.getEntity());
        if (count < level)
            event.setAmount(event.getAmount() * (float) (LHConfig.common().traits.corrosionDamage * level * (level - count)));
    }

    @Override
    protected void perform(LivingEntity target, EquipmentSlot slot)
    {
        DurabilityEater.corrosion(target, slot);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal(i + "")
                                .formatted(Formatting.AQUA)),
                        mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.corrosionDurability * i * 100) + "%")
                                .formatted(Formatting.AQUA)),
                        mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.corrosionDamage * i * 100) + "%")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
