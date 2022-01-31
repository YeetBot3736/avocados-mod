package potato.avocados.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import potato.avocados.Avocados;

public class PlatinumArmorMaterial implements ArmorMaterial{

    private static final int[] BASE_DURABILITY = new int[] {13,15,16,11};
    private static final int[] PROTECTION_AMOUNTS = new int[] {2,5,7,3};

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()]*29;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_AMOUNTS[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 12;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Avocados.PLATINUM_INGOT);
    }

    @Override
    public String getName() {
        return "platinum";
    }

    @Override
    public float getToughness() {
        return 1.63F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.55F;
    }
    
}
