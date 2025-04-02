package karashokleo.l2hostility.content.item.trinket.misc;

import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ImagineBreaker extends CurseTrinketItem
{
    public ImagineBreaker()
    {
        super();
    }

    @Override
    public void onDamageSourceCreate(ItemStack stack, LivingEntity entity, DamageSource damageSource, RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Vec3d position)
    {
        damageSource.setBypassMagic();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_IMAGINE_BREAKER.get().formatted(Formatting.GOLD));
        tooltip.add(LHTexts.ITEM_CHARM_NO_DROP.get().formatted(Formatting.RED));
    }

    @Override
    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 0;
    }
}
