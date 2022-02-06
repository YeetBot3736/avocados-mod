package potato.avocados.mixin;

import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(TexturedRenderLayers.class)
public interface RendererMixin {
    @Accessor("COLORED_SHULKER_BOXES_TEXTURES")
    @Mutable
    static void setTexture(List<SpriteIdentifier> COLORED_SHULKER_BOXES_TEXTURES) {
        throw new AssertionError();
    }
}
