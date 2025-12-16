package karashokleo.l2hostility.content.loot.modifier;

import io.github.fabricators_of_create.porting_lib.loot.LootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import karashokleo.l2hostility.compat.shared.ITraitLootRecipe;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.item.trinket.core.CurseTrinketItem;
import karashokleo.l2hostility.content.loot.condition.PlayerHasItemLootCondition;
import karashokleo.l2hostility.content.loot.condition.TraitLootCondition;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CurseLootModifier extends LootModifier implements ITraitLootRecipe
{
    protected CurseLootModifier(LootCondition... conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        var opt = MobDifficulty.get(context.get(LootContextParameters.THIS_ENTITY));
        if (opt.isEmpty())
        {
            return generatedLoot;
        }
        var diff = opt.get();
        double factor = diff.dropRate;
        PlayerEntity player = context.get(LootContextParameters.LAST_DAMAGE_PLAYER);
        if (player != null)
        {
            for (var stack : CurseTrinketItem.getFromPlayer(player))
            {
                factor *= stack.item().getLootFactor(stack.stack(), PlayerDifficulty.get(player), diff);
            }
        }
        modifyLoot(generatedLoot, context, diff, factor);
        return generatedLoot;
    }

    protected abstract void modifyLoot(ObjectArrayList<ItemStack> generatedLoot, LootContext context, MobDifficulty diff, double factor);

    public LootCondition[] getConditions()
    {
        return conditions;
    }

    @Override
    public List<ItemStack> getTraits()
    {
        List<ItemStack> ans = new ArrayList<>();
        for (var c : getConditions())
        {
            if (c instanceof TraitLootCondition cl)
            {
                ans.add(cl.trait.asItem().getDefaultStack());
            }
        }
        return ans;
    }

    @Override
    public List<ItemStack> getTrinketsRequired()
    {
        List<ItemStack> ans = new ArrayList<>();
        for (var c : getConditions())
        {
            if (c instanceof PlayerHasItemLootCondition condition)
            {
                ans.add(condition.item.getDefaultStack());
            }
        }
        return ans;
    }
}
