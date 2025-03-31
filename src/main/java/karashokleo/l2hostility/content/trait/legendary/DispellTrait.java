package karashokleo.l2hostility.content.trait.legendary;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.api.event.ModifyDispellImmuneFactorCallback;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.traits.EnchantmentDisabler;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public void onDamageSourceCreate(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource damageSource, RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Vec3d position)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.bypassCooldown > 0) return;

        data.bypassCooldown = LHConfig.common().traits.dispellBypassCooldown;
        damageSource.setBypassMagic();
    }

    @Override
    public void onHurting(MobDifficulty difficulty, LivingEntity entity, int level, LivingHurtEvent event)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.disableCooldown > 0) return;

        List<ItemStack> list = new ArrayList<>();
        for (EquipmentSlot slot : EquipmentSlot.values())
        {
            ItemStack stack = event.getEntity().getEquippedStack(slot);
            if (stack.hasEnchantments() && !(stack.getOrCreateNbt().contains("l2hostility_enchantment")))
                list.add(stack);
        }
        if (list.isEmpty()) return;

        int count = Math.min(level * LHConfig.common().traits.dispellCount, list.size());
        if (count <= 0) return;
        count = entity.getRandom().nextInt(count) + 1;

        data.disableCooldown = LHConfig.common().traits.dispellDisableCooldown;
        int time = LHConfig.common().traits.dispellTime * level;
        for (int i = 0; i < count; i++)
        {
            int index = entity.getRandom().nextInt(list.size());
            EnchantmentDisabler.disableEnchantment(entity.getWorld(), list.remove(index), time);
        }
    }

    @Override
    public void onDamaged(MobDifficulty difficulty, LivingEntity entity, int level, LivingDamageEvent event)
    {
        DamageSource source = event.getSource();
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) ||
            source.isIn(DamageTypeTags.BYPASSES_EFFECTS) ||
            !source.isIn(LHTags.MAGIC))
            return;
        float amount = event.getAmount();
        double factor = LHConfig.common().traits.dispellImmuneFactor;
        factor = ModifyDispellImmuneFactorCallback.EVENT.invoker().modifyDispellImmuneFactor(difficulty, entity, level, source, amount, factor);
        event.setAmount((float) (amount * (1 - factor)));
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity mob, int level)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.bypassCooldown > 0)
            data.bypassCooldown--;
        if (data.disableCooldown > 0)
            data.disableCooldown--;
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                Text.literal((int) Math.round(LHConfig.common().traits.dispellImmuneFactor * 100) + "")
                        .formatted(Formatting.AQUA),
                Text.literal(LHConfig.common().traits.dispellBypassCooldown / 20 + "")
                        .formatted(Formatting.AQUA),
                mapLevel(i -> Text.literal(i * LHConfig.common().traits.dispellCount + "")
                        .formatted(Formatting.AQUA)),
                mapLevel(i -> Text.literal(LHConfig.common().traits.dispellTime * i / 20 + ""
                ).formatted(Formatting.AQUA)),
                Text.literal(LHConfig.common().traits.dispellDisableCooldown / 20 + "")
                        .formatted(Formatting.AQUA)
        ).formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int bypassCooldown = 0;
        @SerialClass.SerialField
        public int disableCooldown = 0;
    }
}
