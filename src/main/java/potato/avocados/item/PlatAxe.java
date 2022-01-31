package potato.avocados.item;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;

public class PlatAxe extends AxeItem{

    public PlatAxe(ToolMaterial material) {
        super(material, 6,-3.0f , new Settings().group(ItemGroup.TOOLS)); 
    }
    
}
