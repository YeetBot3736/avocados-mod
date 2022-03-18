package potato.avocados.entity.bed;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import potato.avocados.block.BedBlk;
import potato.avocados.*;
public class BedEntity extends BlockEntity {
    private DyeColor color;

    public BedEntity(BlockPos pos, BlockState state) {
        super(Avocados.BED_E, pos, state);
        this.color = ((BedBlk)state.getBlock()).getColor();
    }

    public BedEntity(BlockPos pos, BlockState state, DyeColor color) {
        super(Avocados.BED_E, pos, state);
        this.color = color;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void setColor(DyeColor color) {
        this.color = color;
    }
}

