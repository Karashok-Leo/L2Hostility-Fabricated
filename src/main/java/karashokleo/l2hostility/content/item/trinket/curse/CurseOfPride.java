package karashokleo.l2hostility.content.item.trinket.curse;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.logic.DifficultyLevel;
import karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class CurseOfPride extends CurseTrinketItem
{
    private static final String NAME = "l2hostility:pride";

    public CurseOfPride()
    {
        super();
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        int level = DifficultyLevel.ofAny(entity);
        double rate = LHConfig.common().items.curse.prideDamageBonus;
        event.setAmount(event.getAmount() * (float) (1 + level * rate));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity)
    {
        stack.getOrCreateNbt().putInt(NAME, DifficultyLevel.ofAny(entity));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        var ans = super.getModifiers(stack, slot, entity, uuid);
        int level = DifficultyLevel.ofAny(entity);
        if (level > 0)
        {
            double rate = LHConfig.common().items.curse.prideHealthBonus * level;
            ans.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, NAME, rate, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
        return ans;
    }

    @Override
    public void modifyDifficulty(MobDifficultyCollector instance)
    {
        instance.traitCostFactor(LHConfig.common().items.curse.prideTraitFactor);
        instance.setFullChance();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        int protect = (int) Math.round(100 * LHConfig.common().items.curse.prideHealthBonus);
        int damage = (int) Math.round(100 * LHConfig.common().items.curse.prideDamageBonus);
        int trait = (int) Math.round(100 * (1 / LHConfig.common().items.curse.prideTraitFactor - 1));
        tooltip.add(LHTexts.ITEM_CHARM_PRIDE.get(protect, damage).formatted(Formatting.GOLD));
        tooltip.add(LHTexts.ITEM_CHARM_TRAIT_CHEAP.get(trait).formatted(Formatting.RED));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
