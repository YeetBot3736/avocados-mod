package potato.avocados.block;

import com.google.common.collect.Maps;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;

import java.util.Map;

public class BannerBlk extends BannerBlock {
    public static final IntProperty ROTATION = Properties.ROTATION;
    private static final Map<DyeColor, Block> COLORED_BANNERS = Maps.newHashMap();

    public BannerBlk(DyeColor dyeColor, AbstractBlock.Settings settings) {
        super(dyeColor, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0));
        COLORED_BANNERS.put(dyeColor, this);
    }
    public static Block getForColor(DyeColor color) {
        return COLORED_BANNERS.getOrDefault(color, Blocks.WHITE_BANNER);
    }
}


