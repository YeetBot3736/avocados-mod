package potato.avocados.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

public class PlatPick extends PickaxeItem{

    public PlatPick(ToolMaterial material) {
        super(material, 2, -2.8f, new Settings().group(ItemGroup.TOOLS));
    }
    
}
