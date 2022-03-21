package potato.avocados;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import potato.avocados.entity.banner.BannerBlkEntityRenderer;
import potato.avocados.entity.bed.BedEntityRenderer;
import potato.avocados.entity.shulker.ShulkerBlockEntityRenderer;
import potato.avocados.entity.shulker.ShulkerBlockModel;

public class AvocadosClient implements ClientModInitializer {
    public static final EntityModelLayer ENTITY_MODEL_LAYER = new EntityModelLayer(new Identifier("avocados","shulker"),"main");
    public static final EntityModelLayer BED_MODEL_H = new EntityModelLayer(new Identifier("avocados","bed"),"head");
    public static final EntityModelLayer BED_MODEL_F = new EntityModelLayer(new Identifier("avocados","bed"),"foot");
    public static final EntityModelLayer BANNER_MODEL = new EntityModelLayer(new Identifier("avocados","banner"),"main");
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.TEAL_STAINED_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.FUCHSIA_STAINED_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.TEAL_STAINED_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.FUCHSIA_STAINED_GLASS_PANE, RenderLayer.getTranslucent());
        BlockEntityRendererRegistry.register(Avocados.SHULKER_E, ShulkerBlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ENTITY_MODEL_LAYER, ShulkerBlockModel::getTexturedModelData);
        BlockEntityRendererRegistry.register(Avocados.BED_E, BedEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BED_MODEL_H, BedEntityRenderer::getHeadTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(BED_MODEL_F, BedEntityRenderer::getFootTexturedModelData);
        BlockEntityRendererRegistry.register(Avocados.BANNER_E, BannerBlkEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BANNER_MODEL, BannerBlkEntityRenderer::getTexturedModelData);


    }
}
