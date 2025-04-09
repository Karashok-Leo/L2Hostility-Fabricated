package karashokleo.l2hostility.content.trait.highlevel;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.traits.ReprintHandler;
import karashokleo.l2hostility.content.trait.base.MobTrait;
import karashokleo.l2hostility.init.LHConfig;
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
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        boolean doReprint = event.getSource().getSource() == entity;
        long total = 0;
        for (var slot : EquipmentSlot.values())
        {
            ItemStack dst = entity.getEquippedStack(slot);
            ItemStack src = event.getEntity().getEquippedStack(slot);
            var targetEnch = EnchantmentHelper.get(src);
            for (var e : targetEnch.entrySet())
            {
                int lv = e.getValue();
                total += lv;
            }
            if (doReprint)
            {
                ReprintHandler.reprint(dst, src);
            }
        }
        int threshold = LHConfig.common().traits.reprintVanishThreshold;

        for (var slot : EquipmentSlot.values())
        {
            ItemStack equipped = entity.getEquippedStack(slot);
            if (equipped.isEmpty() ||
                !equipped.hasEnchantments())
                continue;
            var totalLv = EnchantmentHelper.get(equipped)
                    .values()
                    .stream()
                    .mapToInt(v -> v)
                    .sum();
            if (totalLv <= threshold)
                continue;

            var map = EnchantmentHelper.get(equipped);
            map.compute(Enchantments.VANISHING_CURSE, (k, v) -> v == null ? 1 : Math.max(v, 1));
            EnchantmentHelper.set(map, equipped);
        }

        float factor = 1 + (float) (LHConfig.common().traits.reprintDamage * total);
        event.setAmount(event.getAmount() * factor);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                mapLevel(i -> Text.literal(Math.round(LHConfig.common().traits.reprintDamage * i * 100) + "").formatted(Formatting.AQUA))
        ).formatted(Formatting.GRAY));
    }
}
