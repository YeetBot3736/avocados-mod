package potato.avocados.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import potato.avocados.Avocados;
import potato.avocados.entity.banner.BannerBlkEntity;

public class WallBannerBlk extends WallBannerBlock {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BannerBlkEntity(pos,state,this.getColor());
    }

    public WallBannerBlk(DyeColor dyeColor, AbstractBlock.Settings settings) {
        super(dyeColor, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BannerBlkEntity) {
            return ((BannerBlkEntity)blockEntity).getPickStack();
        }
        return super.getPickStack(world, pos, state);
    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) {
            world.getBlockEntity(pos, Avocados.BANNER_E).ifPresent(blockEntity -> blockEntity.readFrom(itemStack));
        } else if (itemStack.hasCustomName()) {
            world.getBlockEntity(pos, Avocados.BANNER_E).ifPresent(blockEntity -> blockEntity.setCustomName(itemStack.getName()));
        }
    }
}


