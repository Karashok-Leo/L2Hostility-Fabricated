package net.karashokleo.l2hostility.content.trait.legendary;

import net.karashokleo.l2hostility.init.LHConfig;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class PullingTrait extends PushPullTrait
{
    // 传奇词条，使怪物能够吸引玩家。
    // 怪物会吸引周围 10 格内所有对自己有敌意的生物，只能在近战敌人身上发现。
    public PullingTrait() {
        super(Formatting.DARK_BLUE);
    }

    @Override
    protected int getRange() {
        return LHConfig.common().traits.pullingRange;
    }

    @Override
    protected double getStrength(double dist) {
        return (1 - dist) * dist * LHConfig.common().traits.pullingStrength * -4;
    }

    @Override
    public void addDetail(List<Text> list) {
        list.add(Text.translatable(getDescKey(),
                        Text.literal(LHConfig.common().traits.pullingRange + "")
                                .formatted(Formatting.AQUA))
                .formatted(Formatting.GRAY));
    }
}
