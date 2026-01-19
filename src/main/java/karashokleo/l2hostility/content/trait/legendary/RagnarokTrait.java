package karashokleo.l2hostility.content.trait.legendary;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.api.event.AllowTraitEffectCallback;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.compat.trinket.slot.EntitySlotAccess;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.MiscItems;
import karashokleo.l2hostility.content.item.traits.SealedItem;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class RagnarokTrait extends LegendaryTrait
{
    // 传奇词条，使怪物能够封印你的装备。
    // 怪物击中目标时随机封印【词条等级】个饰品和装备，需要像吃食物一样使用【词条等级】秒来解封，不会封印部分饰品与所有能够提供饰品栏位的装备和饰品。
    public RagnarokTrait()
    {
        super(Formatting.DARK_BLUE);
    }

    private static boolean allowSeal(EntitySlotAccess access)
    {
        ItemStack stack = access.get();
        if (stack.isEmpty())
        {
            return false;
        }
        if (stack.isOf(MiscItems.SEAL))
        {
            return false;
        }
        if (stack.isIn(LHTags.NO_SEAL))
        {
            return false;
        }
        if (!LHConfig.common().traits.ragnarokSealBackpack)
        {
            if (Registries.ITEM.getId(stack.getItem()).toString().contains("backpack"))
            {
                return false;
            }
        }
        if (!LHConfig.common().traits.ragnarokSealSlotAdder)
        {
            return !TrinketCompat.isSlotAdder(access);
        }
        return true;
    }

    @Override
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        LivingEntity target = event.getEntity();
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.cooldown > 0)
        {
            return;
        }

        List<EntitySlotAccess> list = new ArrayList<>(TrinketCompat.getItemAccess(target)
            .stream().filter(RagnarokTrait::allowSeal).toList());
        if (list.isEmpty())
        {
            return;
        }

        int count = Math.min(level * LHConfig.common().traits.ragnarokCount, list.size());
        if (count <= 0)
        {
            return;
        }
        if (!AllowTraitEffectCallback.EVENT.invoker().allowTraitEffect(difficulty, entity, target, this, level))
        {
            return;
        }
        count = entity.getRandom().nextInt(count) + 1;

        data.cooldown = LHConfig.common().traits.ragnarokCooldown;
        int time = LHConfig.common().traits.ragnarokTime * level;
        for (int i = 0; i < count; i++)
        {
            int index = entity.getRandom().nextInt(list.size());
            EntitySlotAccess slot = list.remove(index);
            slot.modify(e -> SealedItem.sealItem(e, time));
        }
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity mob, int level)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.cooldown > 0)
        {
            data.cooldown--;
        }
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                mapLevel(i -> Text.literal(i * LHConfig.common().traits.ragnarokCount + "")
                    .formatted(Formatting.AQUA)),
                Text.literal(LHConfig.common().traits.ragnarokCooldown / 20 + "")
                    .formatted(Formatting.AQUA),
                mapLevel(i -> Text.literal("" + Math.round(LHConfig.common().traits.ragnarokTime * i / 20f))
                    .formatted(Formatting.AQUA)))
            .formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int cooldown = 0;
    }
}
