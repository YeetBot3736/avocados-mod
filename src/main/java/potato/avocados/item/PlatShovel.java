package potato.avocados.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

public class PlatShovel extends ShovelItem{

    public PlatShovel(ToolMaterial material) {
        super(material, 2.0f , -3.0f, new Settings().group(ItemGroup.TOOLS));
    }
    
}
