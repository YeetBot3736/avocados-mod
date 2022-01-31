package potato.avocados.mixin;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potato.avocados.Avocados;

@Mixin(SheepEntity.class)
public class SheepEntityMixin {
    private SheepEntity that = (SheepEntity)(Object)this;
    @Inject(method = "getLootTableId", at = @At("HEAD"))
    public void getLootTableId(CallbackInfoReturnable<Identifier> cir){
        if(that.isSheared()) {
            if (that.getColor() == Avocados.TEAL_COLOR) {
                cir.setReturnValue(Avocados.TEAL_SHEEP);
            }
            else if(that.getColor() == Avocados.FUCHSIA_COLOR){
                cir.setReturnValue(Avocados.FUCHSIA_SHEEP);
            }
        }
    }
}
