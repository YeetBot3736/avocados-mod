package potato.avocados.item;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;

public class PlatHoe extends HoeItem{

    public PlatHoe(ToolMaterial material) {
        super(material, -2, 0.0f, new Settings().group(ItemGroup.TOOLS));
    }
    
}
