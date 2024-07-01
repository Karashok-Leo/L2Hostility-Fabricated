package karashokleo.l2hostility.content.loot.condition;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.data.generate.TraitGLMProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

@SerialClass
public class PlayerHasItemLootCondition implements LootCondition
{
    @SerialClass.SerialField
    public Item item;

    @Deprecated
    public PlayerHasItemLootCondition()
    {
    }

    public PlayerHasItemLootCondition(Item item)
    {
        this.item = item;
    }

    @Override
    public LootConditionType getType()
    {
        return TraitGLMProvider.HAS_ITEM;
    }

    @Override
    public boolean test(LootContext context)
    {
        if (!context.hasParameter(LootContextParameters.LAST_DAMAGE_PLAYER)) return false;
        PlayerEntity player = context.get(LootContextParameters.LAST_DAMAGE_PLAYER);
        return TrinketCompat.hasItemInTrinket(player, item);
    }
}
