package potato.avocados.entity.banner;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static potato.avocados.block.BannerBlk.getForColor;

public class BannerBlkEntity extends BannerBlockEntity {
    public static final String PATTERNS_KEY = "Patterns";
    public static final String PATTERN_KEY = "Pattern";
    public static final String COLOR_KEY = "Color";
    @Nullable
    private Text customName;
    private DyeColor baseColor;
    @Nullable
    private NbtList patternListNbt;
    @Nullable
    private List<Pair<BannerPattern, DyeColor>> patterns;

    public BannerBlkEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.baseColor = ((AbstractBannerBlock) state.getBlock()).getColor();
    }

    @Nullable
    public static NbtList getPatternListNbt(ItemStack stack) {
        NbtList nbtList = null;
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null && nbtCompound.contains(PATTERNS_KEY, 9)) {
            nbtList = nbtCompound.getList(PATTERNS_KEY, 10).copy();
        }
        return nbtList;
    }

    public void readFrom(ItemStack stack, DyeColor baseColor) {
        this.baseColor = baseColor;
        this.readFrom(stack);
    }

    public void readFrom(ItemStack stack) {
        this.patternListNbt = getPatternListNbt(stack);
        this.patterns = null;
        this.customName = stack.hasCustomName() ? stack.getName() : null;
    }

    public void setCustomName(Text customName) {
        this.customName = customName;
    }


    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public List<Pair<BannerPattern, DyeColor>> getPatterns() {
        if (this.patterns == null) {
            this.patterns = getPatternsFromNbt(this.baseColor, this.patternListNbt);
        }
        return this.patterns;
    }

    public static List<Pair<BannerPattern, DyeColor>> getPatternsFromNbt(DyeColor baseColor, @Nullable NbtList patternListNbt) {
        ArrayList<Pair<BannerPattern, DyeColor>> list = Lists.newArrayList();
        list.add(Pair.of(BannerPattern.BASE, baseColor));
        if (patternListNbt != null) {
            for (int i = 0; i < patternListNbt.size(); ++i) {
                NbtCompound nbtCompound = patternListNbt.getCompound(i);
                BannerPattern bannerPattern = BannerPattern.byId(nbtCompound.getString(PATTERN_KEY));
                if (bannerPattern == null) continue;
                int j = nbtCompound.getInt(COLOR_KEY);
                list.add(Pair.of(bannerPattern, DyeColor.byId(j)));
            }
        }
        return list;
    }

    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(getForColor(this.baseColor));
        if (this.patternListNbt != null && !this.patternListNbt.isEmpty()) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.put(PATTERNS_KEY, this.patternListNbt.copy());
            BlockItem.setBlockEntityNbt(itemStack, this.getType(), nbtCompound);
        }
        if (this.customName != null) {
            itemStack.setCustomName(this.customName);
        }
        return itemStack;
    }

    public DyeColor getColorForState() {
        return this.baseColor;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}