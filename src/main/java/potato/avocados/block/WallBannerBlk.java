package potato.avocados.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

public class WallBannerBlk extends WallBannerBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public WallBannerBlk(DyeColor dyeColor, AbstractBlock.Settings settings) {
        super(dyeColor, settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }
}


