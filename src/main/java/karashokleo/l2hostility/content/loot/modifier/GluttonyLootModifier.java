package karashokleo.l2hostility.content.loot.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class GluttonyLootModifier extends CurseLootModifier
{
    public static final Codec<GluttonyLootModifier> CODEC = RecordCodecBuilder.create(i -> codecStart(i).apply(i, GluttonyLootModifier::new));

    public GluttonyLootModifier(LootCondition... conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected void modifyLoot(ObjectArrayList<ItemStack> generatedLoot, LootContext context, MobDifficulty diff, double factor)
    {
        double chance = factor * diff.getLevel() * LHConfig.common().items.curse.gluttonyBottleDropRate;
        int base = (int) chance;
        if (context.getRandom().nextDouble() < chance - base) base++;
        if (base > 0) generatedLoot.add(new ItemStack(ConsumableItems.BOTTLE_CURSE, base));
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }

    @Override
    public ItemStack getLoot()
    {
        return ConsumableItems.BOTTLE_CURSE.getDefaultStack();
    }

    @Override
    public void addTooltip(List<Text> tooltip)
    {
        tooltip.add(LHTexts.TOOLTIP_LOOT_GLUTTONY.get().formatted(Formatting.YELLOW));
    }
}
