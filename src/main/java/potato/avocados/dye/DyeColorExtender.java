package potato.avocados.dye;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.MapColor;
import potato.avocados.mixin.MapColorMixin;

public class DyeColorExtender implements Runnable{
    @Override
    public void run() {
        MappingResolver mp = FabricLoader.getInstance().getMappingResolver();
        String str = mp.mapClassName("intermediary","net.minecraft.class_1767");
        String mapc = 'L' + mp.mapClassName("intermediary","net.minecraft.class_3620") + ';';
        ClassTinkerers.enumBuilder(str, int.class, String.class, int.class, mapc ,int.class,int.class).addEnum("TEAL", () -> new Object[]{16, "teal", 0x68ABAB, MapColor.TEAL, 0x5F9EA0, 0x20B2AA}).build();
        ClassTinkerers.enumBuilder(str, int.class, String.class, int.class, mapc ,int.class,int.class).addEnum("FUCHSIA", () -> new Object[]{17, "fuchsia",0xFA7AFA, MapColorExtender.FUCHSIA,0xCC3399,0xB52783}).build();
    }
 
}

