package potato.avocados.entity.banner;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import potato.avocados.AvocadosClient;
import potato.avocados.block.BannerBlk;
import potato.avocados.block.WallBannerBlk;

import java.util.List;

@Environment(value=EnvType.CLIENT)
public class BannerBlkEntityRenderer implements BlockEntityRenderer<BannerBlkEntity> {
    public static final String BANNER = "flag";
    private static final String PILLAR = "pole";
    private static final String CROSSBAR = "bar";
    private final ModelPart banner;
    private final ModelPart pillar;
    private final ModelPart crossbar;

    public BannerBlkEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(AvocadosClient.BANNER_MODEL);
        this.banner = modelPart.getChild(BANNER);
        this.pillar = modelPart.getChild(PILLAR);
        this.crossbar = modelPart.getChild(CROSSBAR);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(BANNER, ModelPartBuilder.create().uv(0, 0).cuboid(-10.0f, 0.0f, -2.0f, 20.0f, 40.0f, 1.0f), ModelTransform.NONE);
        modelPartData.addChild(PILLAR, ModelPartBuilder.create().uv(44, 0).cuboid(-1.0f, -30.0f, -1.0f, 2.0f, 42.0f, 2.0f), ModelTransform.NONE);
        modelPartData.addChild(CROSSBAR, ModelPartBuilder.create().uv(0, 42).cuboid(-10.0f, -32.0f, -1.0f, 20.0f, 2.0f, 2.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(BannerBlkEntity bannerBlkEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        long l;
        float h;
        List<Pair<RegistryEntry<BannerPattern>, DyeColor>> list = bannerBlkEntity.getPatterns();
        boolean bl = bannerBlkEntity.getWorld() == null;
        matrixStack.push();
        if (bl) {
            l = 0L;
            matrixStack.translate(0.5, 0.5, 0.5);
            this.pillar.visible = true;
        } else {
            l = bannerBlkEntity.getWorld().getTime();
            BlockState blockState = bannerBlkEntity.getCachedState();
            if (blockState.getBlock() instanceof BannerBlk) {
                matrixStack.translate(0.5, 0.5, 0.5);
                h = (float)(-blockState.get(BannerBlk.ROTATION) * 360) / 16.0f;
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
                this.pillar.visible = true;
            } else {
                matrixStack.translate(0.5, -0.1666666716337204, 0.5);
                h = -blockState.get(WallBannerBlk.FACING).asRotation();
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
                matrixStack.translate(0.0, -0.3125, -0.4375);
                this.pillar.visible = false;
            }
        }
        matrixStack.push();
        matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
        VertexConsumer vertexConsumer = ModelLoader.BANNER_BASE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntitySolid);
        this.pillar.render(matrixStack, vertexConsumer, i, j);
        this.crossbar.render(matrixStack, vertexConsumer, i, j);
        BlockPos blockPos = bannerBlkEntity.getPos();
        float k = ((float)Math.floorMod(blockPos.getX() * 7L + blockPos.getY() * 9L + blockPos.getZ() * 13L + l, 100L) + f) / 100.0f;
        this.banner.pitch = (-0.0125f + 0.01f * MathHelper.cos((float)Math.PI * 2 * k)) * (float)Math.PI;
        this.banner.pivotY = -32.0f;
        renderCanvas(matrixStack, vertexConsumerProvider, i, j, this.banner, ModelLoader.BANNER_BASE, true, list);
        matrixStack.pop();
        matrixStack.pop();
    }
    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns) {
        renderCanvas(matrices, vertexConsumers, light, overlay, canvas, baseSprite, isBanner, patterns, false);
    }

    public static void renderCanvas(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, ModelPart canvas, SpriteIdentifier baseSprite, boolean isBanner, List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns, boolean glint) {
        canvas.render(matrices, baseSprite.getVertexConsumer(vertexConsumers, RenderLayer::getEntitySolid, glint), light, overlay);
        for (Pair<RegistryEntry<BannerPattern>, DyeColor> pair : patterns) {
            float[] fs = pair.getSecond().getColorComponents();
            ((RegistryEntry)pair.getFirst()).getKey().map((key) -> {
                RegistryKey<BannerPattern> bp = (RegistryKey<BannerPattern>) key;
                if(isBanner){
                    return TexturedRenderLayers.getBannerPatternTextureId(bp);
                }else{
                    return TexturedRenderLayers.getShieldPatternTextureId(bp);
                }
            }).ifPresent((sprite) -> canvas.render(matrices, ((SpriteIdentifier)sprite).getVertexConsumer(vertexConsumers, RenderLayer::getEntityNoOutline), light, overlay, fs[0], fs[1], fs[2], 1.0F));
        }
    }
}