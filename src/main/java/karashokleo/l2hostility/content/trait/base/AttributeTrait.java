package karashokleo.l2hostility.content.trait.base;

import karashokleo.l2hostility.content.logic.TraitManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class AttributeTrait extends MobTrait
{
    public record AttributeEntry(
            String name,
            Supplier<EntityAttribute> attribute,
            DoubleSupplier factor,
            EntityAttributeModifier.Operation op
    )
    {
    }

    protected final AttributeEntry[] entries;

    public AttributeTrait(IntSupplier color, AttributeEntry... entries)
    {
        super(color);
        this.entries = entries;
    }

    public AttributeTrait(Formatting style, AttributeEntry... entries)
    {
        super(style);
        this.entries = entries;
    }

    @Override
    public void initialize(LivingEntity le, int level)
    {
        for (var e : entries)
            TraitManager.addAttribute(le, e.attribute.get(), e.name(), e.factor.getAsDouble() * level, e.op());
    }

    @Override
    public void addDetail(List<Text> list)
    {
        for (var e : entries)
            list.add(mapLevel(i ->
                    (e.op == EntityAttributeModifier.Operation.ADDITION ?
                            Text.literal("+" + Math.round(e.factor.getAsDouble() * i)) :
                            Text.literal("+" + Math.round(e.factor.getAsDouble() * i * 100) + "%"))
                            .formatted(Formatting.AQUA))
                    .append(ScreenTexts.SPACE)
                    .append(
                            Text.translatable(e.attribute.get().getTranslationKey())
                                    .formatted(Formatting.BLUE)
                    ));
    }
}
