package karashokleo.l2hostility.compat.shared;

import karashokleo.l2hostility.client.L2HostilityClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.stream.Stream;

public record LivingEntityWrapper(
    LivingEntity entity,
    int scale,
    Text text
)
{
    public static Stream<LivingEntityWrapper> streamRegistry(Predicate<Entity> predicate, int scale)
    {
        return Registries.ENTITY_TYPE.stream()
            .map(entityType -> entityType.create(L2HostilityClient.getClientWorld()))
            .filter(entity -> entity instanceof LivingEntity)
            .filter(predicate)
            .map(entity -> LivingEntityWrapper.of((LivingEntity) entity, scale, entity.getName()));
    }

    @Nullable
    public static LivingEntityWrapper of(EntityType<?> entityType, int scale)
    {
        return of(entityType, scale, entityType.getName());
    }

    @Nullable
    public static LivingEntityWrapper of(EntityType<?> entityType, int scale, Text text)
    {
        Entity entity = entityType.create(L2HostilityClient.getClientWorld());
        if (!(entity instanceof LivingEntity living))
        {
            return null;
        }
        return of(living, scale, text);
    }

    public static LivingEntityWrapper of(LivingEntity entity, int scale, Text text)
    {
        Box box = entity.getBoundingBox();
        double len = box.getAverageSideLength();
        if (len > 1.05)
        {
            len = (len + Math.sqrt(len)) / 2.0;
        }

        if (entity instanceof SlimeEntity)
        {
            ((SlimeEntity) entity).setSize(5, false);
        }
        scale *= (int) (1.05 / len * 8.0);

        return new LivingEntityWrapper(entity, scale, text);
    }

    public void render(DrawContext ctx, int x, int y, int mouseX, int mouseY)
    {
        float yOffset = this.entity.getStandingEyeHeight() / this.entity.getHeight() * 40;

        InventoryScreen.drawEntity(ctx, x, y, scale, x - mouseX, -yOffset + y - mouseY, this.entity);
//        drawEntity(ctx, x, y, scale, x - mouseX, -yOffset + y - mouseY, this.entity);

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int width = textRenderer.getWidth(text);
        ctx.drawText(textRenderer, text, x - width / 2, y + 8, -1, true);
    }

/*
    public static void drawEntity(DrawContext context, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity)
    {
        float f = (float) Math.atan(mouseX / 40.0F);
        float g = (float) Math.atan(mouseY / 40.0F);
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float) Math.PI);
        Quaternionf quaternionf2 = (new Quaternionf()).rotateX(g * 20.0F * ((float) Math.PI / 180F));
        quaternionf.mul(quaternionf2);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.bodyYaw = 180.0F + f * 20.0F;
        entity.setYaw(180.0F + f * 40.0F);
        entity.setPitch(-g * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();
        drawEntity(context, x, y, size, quaternionf, quaternionf2, entity);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
    }

    public static void drawEntity(DrawContext context, int x, int y, int size, Quaternionf quaternionf, @Nullable Quaternionf quaternionf2, LivingEntity entity)
    {
        context.getMatrices().push();
        context.getMatrices().translate(x, y, (double) 50.0F);
        context.getMatrices().multiplyPositionMatrix((new Matrix4f()).scaling((float) size, (float) size, (float) (-size)));
        context.getMatrices().multiply(quaternionf);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        if (quaternionf2 != null)
        {
            quaternionf2.conjugate();
            entityRenderDispatcher.setRotation(quaternionf2);
        }
        entityRenderDispatcher.setRenderShadows(false);
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0, 0, 0, 0, 1, context.getMatrices(), context.getVertexConsumers(), 15728880));
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }
 */
}
