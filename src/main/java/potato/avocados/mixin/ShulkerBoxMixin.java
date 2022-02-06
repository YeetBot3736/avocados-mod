package potato.avocados.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potato.avocados.Avocados;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxMixin {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private static void get(DyeColor dyeColor, CallbackInfoReturnable<Block> cir){
        if(dyeColor == Avocados.TEAL_COLOR) cir.setReturnValue(Avocados.TEAL_SHULKER_BOX);
        if(dyeColor == Avocados.FUCHSIA_COLOR) cir.setReturnValue(Avocados.FUCHSIA_SHULKER_BOX);
    }
}
