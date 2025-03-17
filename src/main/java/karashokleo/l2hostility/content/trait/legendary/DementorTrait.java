package karashokleo.l2hostility.content.trait.legendary;

import dev.xkmc.l2serial.serialization.SerialClass;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import karashokleo.l2hostility.content.component.mob.CapStorageData;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DementorTrait extends LegendaryTrait
{
    // 传奇词条，使怪物免疫物理伤害并无视护甲。
    // 怪物将免疫物理攻击，攻击时造成无视护甲的魔法伤害。
    public DementorTrait()
    {
        super(Formatting.DARK_GRAY);
    }

    @Override
    public void onDamageSourceCreate(MobDifficulty difficulty, LivingEntity entity, int level, DamageSource damageSource, RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Vec3d position)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.bypassCooldown > 0) return;

        data.bypassCooldown = LHConfig.common().traits.dementorBypassCooldown;
        damageSource.setBypassArmor();
        damageSource.setBypassShield();
    }

    @Override
    public void onAttacked(MobDifficulty difficulty, LivingEntity entity, int level, LivingAttackEvent event)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.immuneCooldown > 0) return;

        DamageSource source = event.getSource();
        if (!source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY) &&
            !source.isIn(DamageTypeTags.BYPASSES_EFFECTS) &&
            !source.isIn(LHTags.MAGIC))
        {
            data.immuneCooldown = LHConfig.common().traits.dementorImmuneCooldown;
            event.setCanceled(true);
        }
    }

    @Override
    public void serverTick(MobDifficulty difficulty, LivingEntity mob, int level)
    {
        var data = difficulty.getOrCreateData(getId(), Data::new);
        if (data.immuneCooldown > 0)
            data.immuneCooldown--;
        if (data.bypassCooldown > 0)
            data.bypassCooldown--;
    }

    @Override
    public void addDetail(List<Text> list)
    {
        list.add(Text.translatable(getDescKey(),
                Text.literal(LHConfig.common().traits.dementorImmuneCooldown / 20 + "")
                        .formatted(Formatting.AQUA),
                Text.literal(LHConfig.common().traits.dementorBypassCooldown / 20 + "")
                        .formatted(Formatting.AQUA)
        ).formatted(Formatting.GRAY));
    }

    @SerialClass
    public static class Data extends CapStorageData
    {
        @SerialClass.SerialField
        public int immuneCooldown = 0;
        @SerialClass.SerialField
        public int bypassCooldown = 0;
    }
}
