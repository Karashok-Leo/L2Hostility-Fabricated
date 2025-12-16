package karashokleo.l2hostility.content.trait.legendary;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.network.S2CUndying;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.init.LHNetworking;
import karashokleo.l2hostility.init.LHTraits;
import karashokleo.leobrary.effect.api.event.LivingHealCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class UndyingTrait extends LegendaryTrait
{
    // 传奇词条，使怪物拥有无限续杯的不死图腾。
    // 怪物死亡时会试图使用不死图腾来使自己满血复活。
    public UndyingTrait()
    {
        super(Formatting.DARK_BLUE);
    }

    @Override
    public boolean allowDeath(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource source, float amount)
    {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
        {
            return true;
        }
        if (difficulty.hasTrait(LHTraits.SPLIT))
        {
            return true;
        }
        if (level > 0 && validTarget(entity))
        {
            var data = difficulty.getOrCreateData(LHTraits.UNDYING.getId(), Data::new);
            if (data.times >= level * LHConfig.common().traits.undyingTimes)
            {
                return true;
            }

            float healAmount = entity.getMaxHealth() - entity.getHealth();
            LivingHealCallback.LivingHeal event = new LivingHealCallback.LivingHeal(entity, healAmount);
            boolean allow = LivingHealCallback.EVENT.invoker().onLivingHeal(event);
            if (!allow)
            {
                return true;
            }

            data.times++;
            entity.setHealth(entity.getHealth() + event.getAmount());
            LHNetworking.toTracking(entity, new S2CUndying(entity));
            entity.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0F, 1.0F);
            return false;
        } else
        {
            return true;
        }
    }

    public static boolean validTarget(LivingEntity le)
    {
        return !(le instanceof EnderDragonEntity) &&
            le.canHaveStatusEffect(new StatusEffectInstance(LHEffects.CURSE, 100));
    }

    @Override
    public boolean allow(LivingEntity le, int difficulty, int maxModLv)
    {
        return validTarget(le) && super.allow(le, difficulty, maxModLv);
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
            mapLevel(i -> Text.literal(i * LHConfig.common().traits.undyingTimes + "")
                .formatted(Formatting.AQUA))
        ).formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int times = 0;
    }
}
