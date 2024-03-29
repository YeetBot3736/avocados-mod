package potato.avocados.entity.banner;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import potato.avocados.Avocados;
import potato.avocados.block.BannerBlk;

import java.util.ArrayList;
import java.util.List;

public class BannerBlkEntity extends BlockEntity implements Nameable {
    public static final String PATTERNS_KEY = "Patterns";
    public static final String PATTERN_KEY = "Pattern";
    public static final String COLOR_KEY = "Color";
    @Nullable
    private Text customName;
    private DyeColor baseColor;
    @Nullable
    private NbtList patternListNbt;
    @Nullable
    private List<Pair<RegistryEntry<BannerPattern>, DyeColor>> patterns;

    public BannerBlkEntity(BlockPos pos, BlockState state) {
        super(Avocados.BANNER_E, pos, state);
        this.baseColor = ((AbstractBannerBlock)state.getBlock()).getColor();
    }

    public BannerBlkEntity(BlockPos pos, BlockState state, DyeColor baseColor) {
        this(pos, state);
        this.baseColor = baseColor;
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

//    public void readFrom(ItemStack stack, DyeColor baseColor) {
//        this.baseColor = baseColor;
//        this.readFrom(stack);
//    }

    public void readFrom(ItemStack stack) {
        this.patternListNbt = BannerBlkEntity.getPatternListNbt(stack);
        this.patterns = null;
        this.customName = stack.hasCustomName() ? stack.getName() : null;
    }

    @Override
    public Text getName() {
        if (this.customName != null) {
            return this.customName;
        }
        return Text.translatable("block.avocados.banner");
    }

    @Override
    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    public void setCustomName(@Nullable Text    customName) {
        this.customName = customName;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.patternListNbt != null) {
            nbt.put(PATTERNS_KEY, this.patternListNbt);
        }
        if (this.customName != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if (nbt.contains("CustomName", 8)) {
            this.customName = Text.Serializer.fromJson(nbt.getString("CustomName"));
        }
        this.patternListNbt = nbt.getList(PATTERNS_KEY, 10);
        this.patterns = null;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public List<Pair<RegistryEntry<BannerPattern>, DyeColor>> getPatterns() {
        if (this.patterns == null) {
            this.patterns = getPatternsFromNbt(this.baseColor, this.patternListNbt);
        }
        return this.patterns;
    }

    public static List<Pair<RegistryEntry<BannerPattern>, DyeColor>> getPatternsFromNbt(DyeColor baseColor, NbtList patternListNbt) {
        ArrayList<Pair<RegistryEntry<BannerPattern>, DyeColor>> list = Lists.newArrayList();
        list.add(Pair.of(Registry.BANNER_PATTERN.entryOf(BannerPatterns.BASE), baseColor));
        if (patternListNbt != null) {
            for (int i = 0; i < patternListNbt.size(); ++i) {
                NbtCompound nbtCompound = patternListNbt.getCompound(i);
                RegistryEntry<BannerPattern> registryEntry = BannerPattern.byId(nbtCompound.getString("Pattern"));
                if (registryEntry == null) continue;
                int j = nbtCompound.getInt(COLOR_KEY);
                list.add(Pair.of(registryEntry, DyeColor.byId(j)));
            }
        }
        return list;
    }

    public ItemStack getPickStack() {
        ItemStack itemStack = new ItemStack(BannerBlk.getForColor(this.baseColor));
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

}