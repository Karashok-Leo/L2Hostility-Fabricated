package karashokleo.l2hostility.compat.rei;

import karashokleo.l2hostility.compat.shared.LivingEntityWrapper;
import karashokleo.l2hostility.init.LHTraits;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class REITraitDisplay implements Display
{
    protected final Identifier id;
    protected final LivingEntityWrapper entityWrapper;
    protected final EntryIngredient traits;

    public REITraitDisplay(LivingEntityWrapper entityWrapper)
    {
        this.id = Registries.ENTITY_TYPE.getId(entityWrapper.entity().getType());
        this.entityWrapper = entityWrapper;
        var traits = LHTraits.TRAIT
                .stream()
                .filter(trait -> trait.allow(entityWrapper.entity()))
                .map(e -> e.asItem().getDefaultStack())
                .toList();
        this.traits = EntryIngredients.ofItemStacks(traits);
    }

    public LivingEntityWrapper getEntityWrapper()
    {
        return entityWrapper;
    }

    public EntryIngredient getTraits()
    {
        return traits;
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return List.of();
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return List.of(traits);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return REICompat.TRAIT;
    }
}
