package potato.avocados.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import potato.avocados.Avocados;
import potato.avocados.entity.banner.BannerBlkEntity;

public class BannerBlk extends BannerBlock {
    public static final IntProperty ROTATION = Properties.ROTATION;

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BannerBlkEntity(pos,state,this.getColor());
    }

    public BannerBlk(DyeColor dyeColor, AbstractBlock.Settings settings) {
        super(dyeColor, settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ROTATION, 0));
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


