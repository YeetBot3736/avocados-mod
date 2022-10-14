package potato.avocados.ore;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class NetherPlatinumOre extends OreBlock{

    public NetherPlatinumOre(Settings settings) {
        super(settings, UniformIntProvider.create(0,2));
    }

}
