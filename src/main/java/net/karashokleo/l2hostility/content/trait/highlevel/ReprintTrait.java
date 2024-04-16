package net.karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.item.traits.ReprintHandler;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ReprintTrait extends MobTrait
{
    // 使怪物能够重创拥有大量附魔装备的目标。
    // 怪物会复制目标身上装备的所有附魔，目标的每级附魔提升 2% 的伤害（ x级附魔提供 2x 的附魔点数）。
    public ReprintTrait()
    {
        super(Formatting.LIGHT_PURPLE);
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        long total = 0;
        int maxLv = 0;
        for (var slot : EquipmentSlot.values())
        {
            ItemStack dst = entity.getEquippedStack(slot);
            ItemStack src = event.getEntity().getEquippedStack(slot);
            var targetEnch = EnchantmentHelper.get(src);
            for (var e : targetEnch.entrySet())
            {
                int lv = e.getValue();
                maxLv = Math.max(maxLv, lv);
                if (lv >= 30) total = -1;
                else if (total >= 0) total += 1L << (lv - 1);
            }
            if (event.getSource().getSource() == entity)
                ReprintHandler.reprint(dst, src);
        }
        int bypass = LHConfig.common().traits.reprintBypass;
        if (maxLv >= bypass)
        {
            ItemStack weapon = entity.getEquippedStack(EquipmentSlot.MAINHAND);
            if (!weapon.isEmpty() &&
                    (weapon.hasEnchantments() || weapon.isEnchantable()))
            {
                var map = EnchantmentHelper.get(weapon);
                // NYI 虚空之触
                map.compute(Enchantments.VANISHING_CURSE, (k, v) -> v == null ? 1 : Math.max(v, 1));
                EnchantmentHelper.set(map, weapon);
            }
        }

        float factor = total >= 0 ? total : (float) Math.pow(2, maxLv - 1);
        event.setAmount(event.getAmount() * 1 + (float) (LHConfig.common().traits.reprintDamage * factor));
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.reprintDamage * i * 100) + "%")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
