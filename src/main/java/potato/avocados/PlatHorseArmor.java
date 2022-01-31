package potato.avocados;

import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class PlatHorseArmor extends HorseArmorItem {
    private final int bonus;
    private final String entityTexture;

    public PlatHorseArmor(int bonus, String name, Item.Settings settings) {
        super(bonus, name, settings);
        this.bonus = bonus;
        this.entityTexture = "textures/entity/horse/armor/horse_armor_" + name + ".png";
    }

    public Identifier getEntityTexture() {
        return new Identifier(this.entityTexture);
    }

    public int getBonus() {
        return this.bonus;
    }
}
