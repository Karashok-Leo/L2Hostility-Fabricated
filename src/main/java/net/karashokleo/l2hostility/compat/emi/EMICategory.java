package net.karashokleo.l2hostility.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class EMICategory extends EmiRecipeCategory
{
    private final Supplier<Text> title;

    public EMICategory(Identifier id, EmiRenderable icon, Supplier<Text> title)
    {
        super(id, icon);
        this.title = title;
    }

    @Override
    public Text getName()
    {
        return title.get();
    }
}
