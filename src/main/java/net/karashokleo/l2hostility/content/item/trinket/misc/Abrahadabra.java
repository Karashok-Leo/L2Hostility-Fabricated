package net.karashokleo.l2hostility.content.item.trinket.misc;

import net.karashokleo.l2hostility.config.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.karashokleo.l2hostility.init.data.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Abrahadabra extends CurseTrinketItem
{
    public Abrahadabra(Settings settings)
    {
        super(settings);
    }

    @Override
    public int getExtraLevel() {
        return LHConfig.common().items.abrahadabraExtraLevel;
    }

    @Override
    public boolean reflectTrait(MobTrait trait) {
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ABRAHADABRA.get().formatted(Formatting.GOLD));
    }
}
