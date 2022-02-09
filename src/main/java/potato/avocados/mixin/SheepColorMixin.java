package potato.avocados.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potato.avocados.Avocados;

@Mixin(SheepEntity.class)
public abstract class SheepColorMixin extends Entity {
    @Shadow @Final private static TrackedData<Byte> COLOR;
    public SheepColorMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getColor", cancellable = true, at = @At("HEAD"))
    public void getColor(CallbackInfoReturnable<DyeColor> cir){
        cir.setReturnValue(DyeColor.byId(dataTracker.get(COLOR) & 31));
    }

    @Inject(method = "setColor", at=@At("HEAD"), cancellable = true)
    public void setColor(DyeColor color, CallbackInfo ci){
        byte avocados = this.dataTracker.get(COLOR);
        if(color == Avocados.FUCHSIA_COLOR || color == Avocados.TEAL_COLOR){
            dataTracker.set(COLOR, (byte)(avocados & 0xF0 | color.getId() & 31));
            ci.cancel();
        }
    }
}
