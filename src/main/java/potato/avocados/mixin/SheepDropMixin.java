package potato.avocados.mixin;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SheepEntity.class)
public interface SheepDropMixin {
    @Accessor
    static Map<DyeColor, ItemConvertible> getDROPS() {
        throw new AssertionError();
    }

    @Accessor("DROPS")
    @Mutable
    static void setDrops(Map<DyeColor, ItemConvertible> x){
        throw new AssertionError();
    }
}
