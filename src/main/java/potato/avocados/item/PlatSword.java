package potato.avocados.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class PlatSword extends SwordItem{

    public PlatSword(ToolMaterial toolMaterial) {
        super(toolMaterial, 4, -2.4f, new Settings().group(ItemGroup.COMBAT));
    }
}
