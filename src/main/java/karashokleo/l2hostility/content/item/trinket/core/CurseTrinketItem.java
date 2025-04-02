package karashokleo.l2hostility.content.item.trinket.core;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.content.component.mob.MobDifficulty;
import karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import karashokleo.l2hostility.content.logic.MobDifficultyCollector;
import karashokleo.l2hostility.init.LHMiscs;
import karashokleo.l2hostility.util.GenericItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CurseTrinketItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public CurseTrinketItem()
    {
        super();
    }

    public CurseTrinketItem(Settings settings)
    {
        super(settings);
    }

    public static List<GenericItemStack<CurseTrinketItem>> getFromPlayer(LivingEntity player)
    {
        var list = TrinketCompat.getItems(player, e -> e.getItem() instanceof CurseTrinketItem);
        List<GenericItemStack<CurseTrinketItem>> ans = new ArrayList<>();
        for (var e : list)
            if (e.getItem() instanceof CurseTrinketItem item)
                ans.add(new GenericItemStack<>(item, e));
        return ans;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid)
    {
        Multimap<EntityAttribute, EntityAttributeModifier> map = LinkedHashMultimap.create();
        int lv = getExtraLevel();
        if (lv > 0)
            map.put(LHMiscs.ADD_LEVEL, new EntityAttributeModifier(uuid, Registries.ITEM.getId(this).getPath(), lv, EntityAttributeModifier.Operation.ADDITION));
        return map;
    }

    public int getExtraLevel()
    {
        return 0;
    }

    public double getLootFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 1;
    }

    public double getGrowFactor(ItemStack stack, PlayerDifficulty player, MobDifficulty mob)
    {
        return 1;
    }

    public void modifyDifficulty(MobDifficultyCollector instance)
    {
    }
}
