package potato.avocados;

import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BannerItm extends BannerItem {
    private static final String TRANSLATION_KEY_PREFIX = "block.avocados.banner.";

    public BannerItm(Block block, Block block2, Item.Settings settings) {
        super(block, block2, settings);
        Validate.isInstanceOf(AbstractBannerBlock.class, block);
        Validate.isInstanceOf(AbstractBannerBlock.class, block2);
    }

    public DyeColor getColor() {
        return ((AbstractBannerBlock)this.getBlock()).getColor();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
    }
}