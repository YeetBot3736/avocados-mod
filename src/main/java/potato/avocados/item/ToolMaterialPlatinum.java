package potato.avocados.item;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import potato.avocados.Avocados;

public class ToolMaterialPlatinum implements ToolMaterial {

    @Override
    public int getDurability() {
       return 873;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 7.0f;
    }

    @Override
    public float getAttackDamage() {
        return 2.0f;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 13;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Avocados.PLATINUM_INGOT);
    }
    
}
