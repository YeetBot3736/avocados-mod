package potato.avocados;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import potato.avocados.entity.ShulkerBlockEntityRenderer;

public class AvocadosClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.TEAL_STAINED_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.FUCHSIA_STAINED_GLASS, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.TEAL_STAINED_GLASS_PANE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Avocados.FUCHSIA_STAINED_GLASS_PANE, RenderLayer.getTranslucent());
        BlockEntityRendererRegistry.register(Avocados.SHULKER_E, ShulkerBlockEntityRenderer::new);
    }
}
