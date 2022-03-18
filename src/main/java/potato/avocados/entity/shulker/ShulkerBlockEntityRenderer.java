package potato.avocados.entity.shulker;

import net.minecraft.block.AbstractBlock;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.State;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import potato.avocados.*;
import potato.avocados.block.ShulkerBlock;

import java.util.Objects;

public class ShulkerBlockEntityRenderer
        implements BlockEntityRenderer<ShulkerBlockEntity> {
    private final ShulkerEntityModel<?> model;

    public ShulkerBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new ShulkerEntityModel<>(ctx.getLayerModelPart(Avocados.SH));
    }

    @Override
    public void render(ShulkerBlockEntity ShulkerBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        DyeColor dyeColor;
        Object blockState;
        Direction direction = Direction.UP;
        if (ShulkerBlockEntity.hasWorld() && ((AbstractBlock.AbstractBlockState)(blockState = Objects.requireNonNull(ShulkerBlockEntity.getWorld()).getBlockState(ShulkerBlockEntity.getPos()))).getBlock() instanceof ShulkerBlock) {
            direction = (Direction) ((State)blockState).get(ShulkerBlock.FACING);
        }
        blockState = (dyeColor = ShulkerBlockEntity.getColor()) == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(dyeColor.getId());
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        matrixStack.scale(0.9995f, 0.9995f, 0.9995f);
        matrixStack.multiply(direction.getRotationQuaternion());
        matrixStack.scale(1.0f, -1.0f, -1.0f);
        matrixStack.translate(0.0, -1.0, 0.0);
        ModelPart modelPart = this.model.getLid();
        modelPart.setPivot(0.0f, 24.0f - ShulkerBlockEntity.getAnimationProgress(f) * 0.5f * 16.0f, 0.0f);
        modelPart.yaw = 270.0f * ShulkerBlockEntity.getAnimationProgress(f) * ((float)Math.PI / 180);
        VertexConsumer vertexConsumer = ((SpriteIdentifier)blockState).getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull);
        this.model.render(matrixStack, vertexConsumer, i, j, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }
}

