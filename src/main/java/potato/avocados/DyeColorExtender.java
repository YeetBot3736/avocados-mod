package potato.avocados;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.block.MapColor;

public class DyeColorExtender implements Runnable{
    @Override
    public void run() {
        MappingResolver mp = FabricLoader.getInstance().getMappingResolver();
        String str = mp.mapClassName("intermediary","net.minecraft.class_1767");
        String mapc = 'L' + mp.mapClassName("intermediary","net.minecraft.class_3620") + ';';
        ClassTinkerers.enumBuilder(str, int.class, String.class, int.class, mapc ,int.class,int.class).addEnum("TEAL", () -> new Object[]{17, "teal", 0x8080, MapColor.TEAL, 0x5F9EA0, 0x20B2AA}).build();
        ClassTinkerers.enumBuilder(str, int.class, String.class, int.class, mapc ,int.class,int.class).addEnum("FUCHSIA", () -> new Object[]{18, "fuchsia",0xFF00FF, Avocados.FUCHSIA,0xCC3399,0xB52783}).build();
    }
 
}

