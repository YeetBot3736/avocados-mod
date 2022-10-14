package potato.avocados.ore;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class PlatinumOre extends OreBlock {

    public PlatinumOre(Settings settings) {
        super(settings, UniformIntProvider.create(1,4));
    }
}
