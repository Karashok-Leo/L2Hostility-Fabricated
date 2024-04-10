package net.karashokleo.l2hostility.content.trait.legendary;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.compat.trinket.TrinketCompat;
import net.karashokleo.l2hostility.compat.trinket.slot.EntitySlotAccess;
import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.traits.SealedItem;
import net.karashokleo.l2hostility.init.data.LHTags;
import net.karashokleo.l2hostility.init.registry.LHItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class RagnarokTrait extends LegendaryTrait
{
    private static boolean allowSeal(EntitySlotAccess access)
    {
        ItemStack stack = access.get();
        if (stack.isEmpty()) return false;
        if (stack.isOf(LHItems.SEAL)) return false;
        if (stack.isIn(LHTags.NO_SEAL)) return false;
        if (!LHConfig.common().traits.ragnarokSealBackpack)
        {
            var rl = Registries.ITEM.getId(stack.getItem());
            if (rl == null) return false;
            if (rl.toString().contains("backpack")) return false;
        }
        if (!LHConfig.common().traits.ragnarokSealSlotAdder)
            return !TrinketCompat.isSlotAdder(access);
        return true;
    }

    // 传奇词条，使怪物能够封印你的装备。
    // 怪物击中目标时随机封印【词条等级】个饰品和装备，需要像吃食物一样使用【词条等级】秒来解封，不会封印部分饰品与所有能够提供饰品栏位的装备和饰品。
    public RagnarokTrait()
    {
        super(Formatting.DARK_BLUE);
    }

    @Override
    public void onHurting(int level, LivingEntity entity, LivingHurtEvent event)
    {
        List<EntitySlotAccess> list = new ArrayList<>(TrinketCompat.getItemAccess(event.getEntity())
                .stream().filter(RagnarokTrait::allowSeal).toList());
        int count = Math.min(level, list.size());
        int time = LHConfig.common().traits.ragnarokTime * level;
        for (int i = 0; i < count; i++)
        {
            int index = entity.getRandom().nextInt(list.size());
            EntitySlotAccess slot = list.remove(index);
            slot.modify(e -> SealedItem.sealItem(e, time));
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                        mapLevel(i -> Text.literal(i + "")
                                .formatted(Formatting.AQUA)),
                        mapLevel(i -> Text.literal("" + Math.round(LHConfig.common().traits.ragnarokTime * i / 20f))
                                .formatted(Formatting.AQUA)))
                .formatted(Formatting.GRAY));
    }
}
