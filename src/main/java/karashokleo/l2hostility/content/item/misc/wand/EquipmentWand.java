package karashokleo.l2hostility.content.item.misc.wand;

import karashokleo.l2hostility.content.screen.equipment.EquipmentScreenHandlerFactory;
import karashokleo.l2hostility.init.LHTexts;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EquipmentWand extends BaseWand
{
    public EquipmentWand(Settings settings)
    {
        super(settings);
    }

    @Override
    public void clickTarget(ItemStack stack, PlayerEntity player, LivingEntity entity)
    {
//        if (entity instanceof MobEntity mob) {
//            if (player.isSneaking())
//                new EntityCuriosMenuPvd(mob, 0).open((ServerPlayerEntity) player);
//            else
//                new EquipmentsMenuPvd(mob).open((ServerPlayerEntity) player);
//        }

        if (entity instanceof MobEntity mob)
            new EquipmentScreenHandlerFactory(mob).open((ServerPlayerEntity) player);
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(LHTexts.ITEM_WAND_EQUIPMENT.get().formatted(Formatting.GRAY));
    }
}
