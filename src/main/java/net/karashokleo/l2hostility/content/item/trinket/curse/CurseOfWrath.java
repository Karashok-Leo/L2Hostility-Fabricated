package net.karashokleo.l2hostility.content.item.trinket.curse;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.init.LHConfig;
import net.karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import net.karashokleo.l2hostility.content.logic.DifficultyLevel;
import net.karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class CurseOfWrath extends CurseTrinketItem
{
    private static final Set<StatusEffect> IMMUNE = Set.of(StatusEffects.BLINDNESS, StatusEffects.DARKNESS, StatusEffects.NAUSEA, StatusEffects.SLOWNESS, StatusEffects.MINING_FATIGUE, StatusEffects.WEAKNESS);

    public CurseOfWrath(Settings settings)
    {
        super(settings);
    }

    @Override
    public int getExtraLevel()
    {
        return LHConfig.common().items.curse.wrathExtraLevel;
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        int level = DifficultyLevel.ofAny(event.getEntity()) - DifficultyLevel.ofAny(entity);
        if (level > 0)
        {
            double rate = LHConfig.common().items.curse.wrathDamageBonus;
            event.setAmount(event.getAmount() * (float) (1 + level * rate));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int rate = (int) Math.round(100 * LHConfig.common().items.curse.wrathDamageBonus);
        tooltip.add(LHTexts.ITEM_CHARM_WRATH.get(rate).formatted(Formatting.GOLD));
        addTooltip(tooltip, IMMUNE);
    }

    private void addTooltip(List<Text> list, Set<StatusEffect> set)
    {
        TreeMap<Identifier, StatusEffect> map = new TreeMap<>();
        for (StatusEffect e : set)
            map.put(Registries.STATUS_EFFECT.getId(e), e);
        MutableText comp = LHTexts.ARMOR_IMMUNE.get();
        boolean comma = false;
        for (StatusEffect e : map.values())
        {
            if (comma) comp = comp.append(", ");
            comp = comp.append(Text.translatable(e.getTranslationKey())
                    .formatted(e.getCategory().getFormatting()));
            comma = true;
        }
        list.add(comp.formatted(Formatting.LIGHT_PURPLE));
    }
}
