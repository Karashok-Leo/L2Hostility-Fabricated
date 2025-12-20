package karashokleo.l2hostility.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(DamageSource.class)
public abstract class DamageSourceMixin
{
    @WrapOperation(
        method = "getDeathMessage",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/Entity;getDisplayName()Lnet/minecraft/text/Text;",
            ordinal = 1
        )
    )
    private Text inject_getDeathMessage(Entity instance, Operation<Text> original)
    {
        Text name = original.call(instance);
        if (!LHConfig.common().misc.deathMessageShowKillerTitle)
        {
            return name;
        }
        Optional<MobDifficulty> optional = MobDifficulty.get(instance);
        if (optional.isEmpty())
        {
            return name;
        }
        Text title = optional.get().getTitleLine(true, true);
        if (title == null)
        {
            return name;
        }
        return Text.empty()
            .append(name)
            .append(title);
    }
}
