package net.karashokleo.l2hostility.content.item.trinket.core;

import io.github.fabricators_of_create.porting_lib.entity.events.LivingAttackEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.karashokleo.l2hostility.content.component.mob.MobDifficulty;
import net.karashokleo.l2hostility.content.component.player.PlayerDifficulty;
import net.karashokleo.l2hostility.content.trait.base.MobTrait;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class CurseTrinketItem extends BaseTrinketItem
{
    public CurseTrinketItem(Settings settings)
    {
        super(settings);
    }


//    @Override
//    public final Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack)
//    {
//        return getAttributeModifiers(slotContext.entity(), uuid);
//    }
//
//    protected Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nullable LivingEntity wearer, UUID uuid)
//    {
//        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create();
//        int lv = getExtraLevel();
//        if (lv > 0)
//        {
//            ResourceLocation id = ForgeRegistries.ITEMS.getKey(this);
//            assert id != null;
//            map.put(LHMiscs.ADD_LEVEL.get(), new AttributeModifier(uuid, id.getPath(), lv, AttributeModifier.Operation.ADDITION));
//        }
//        return map;
//    }

//    @Override
//    public List<Text> getAttributesTooltip(List<Text> tooltips, ItemStack stack)
//    {
//        return AttrTooltip.modifyTooltip(super.getAttributesTooltip(tooltips, stack),
//                getAttributeModifiers(Proxy.getPlayer(), Util.NIL_UUID), false);
//    }

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

    public boolean reflectTrait(MobTrait trait)
    {
        return false;
    }
//
//    public void onHurtTarget(ItemStack stack, LivingEntity user, AttackCache cache) {
//    }
//
//    public void onDamage(ItemStack stack, LivingEntity user, LivingDamageEvent event) {
//    }

    public void onAttacked(ItemStack stack, LivingEntity entity, LivingAttackEvent event)
    {
    }

    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onHurt(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
    }

    public void onDamaged(ItemStack stack, LivingEntity entity, LivingDamageEvent event)
    {
    }
}
