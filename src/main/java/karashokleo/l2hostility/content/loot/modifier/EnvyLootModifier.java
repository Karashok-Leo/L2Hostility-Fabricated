package karashokleo.l2hostility.content.loot.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.init.LHConfig;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.text.Text;

import java.util.List;

public class EnvyLootModifier extends CurseLootModifier
{
    public static final Codec<EnvyLootModifier> CODEC = RecordCodecBuilder.create(i -> codecStart(i).apply(i, EnvyLootModifier::new));

    public EnvyLootModifier(LootCondition... conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected void modifyLoot(ObjectArrayList<ItemStack> generatedLoot, LootContext context, MobDifficulty diff, double factor)
    {
        for (var entry : diff.traits.entrySet())
        {
            if (diff.fullDrop || context.getRandom().nextDouble() < factor * entry.getValue() * LHConfig.common().items.curse.envyDropRate)
            {
                generatedLoot.add(entry.getKey().asItem().getDefaultStack());
            }
        }
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC;
    }

    @Override
    public ItemStack getLoot()
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void addTooltip(List<Text> tooltip)
    {
        tooltip.add(LHTexts.TOOLTIP_LOOT_ENVY.get());
    }
}
