package net.karashokleo.l2hostility.content.trait.legendary;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import net.karashokleo.l2hostility.init.data.LHDamageTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class DispellTrait extends LegendaryTrait
{
    // 传奇词条，使怪物免疫魔法伤害并封印你的附魔。
    // 怪物免疫魔法伤害，伤害穿透魔法保护（比如摄魂），攻击时随机选择【词条等级】个装备附魔其封印【词条等级】* 10秒（大部分提供耐久和保护物品的附魔不会受到影响）。
    public DispellTrait()
    {
        super(Formatting.DARK_PURPLE);
    }

//    @Override
//    public void onCreateSource(int level, LivingEntity attacker, CreateSourceEvent event)
//    {
//        if (event.getResult() == L2DamageTypes.MOB_ATTACK)
//            event.enable(DefaultDamageState.BYPASS_MAGIC);
//    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        List<ItemStack> list = new ArrayList<>();
        for (EquipmentSlot slot : EquipmentSlot.values())
        {
            ItemStack stack = event.getEntity().getEquippedStack(slot);
            if (stack.hasEnchantments() && !(stack.getOrCreateNbt().contains("l2hostility_enchantment")))
                list.add(stack);
        }
        if (list.isEmpty()) return;
        int time = LHConfig.common().traits.dispellTime * level;
        int count = Math.min(level, list.size());
        for (int i = 0; i < count; i++)
        {
            int index = entity.getRandom().nextInt(list.size());
            EnchantmentDisabler.disableEnchantment(entity.getWorld(), list.remove(index), time);
        }
    }

    @Override
    public void onAttacked(int level, LivingEntity entity, LivingAttackEvent event)
    {
        DamageSource source = event.getSource();
        if (LHDamageTypes.isMagic(source) && !LHDamageTypes.bypasses(source))
            event.setCanceled(true);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal(i + "")
                                .formatted(Formatting.AQUA)),
                        mapLevel(i -> Text.literal(LHConfig.common().traits.dispellTime * i / 20 + "")
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
