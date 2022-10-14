package potato.avocados;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class AvocadoComponents {
    public static final FoodComponent AVOCADO = (new FoodComponent.Builder()).hunger(8).saturationModifier((float) 0.7).statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,9600,0),0.87F).build();
    public static final FoodComponent CHEESE = (new FoodComponent.Builder()).hunger(6).saturationModifier((float) 0.5).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,600,0),0.15F).build();
    public static final FoodComponent CHEESECAKE = (new FoodComponent.Builder()).hunger(4).saturationModifier((float) 1.2).build();
    public static final FoodComponent BEESECHURGER = (new FoodComponent.Builder()).hunger(7).saturationModifier((float) 0.9).meat().build();
    public static final FoodComponent COOKED_BEET = (new FoodComponent.Builder()).hunger(5).saturationModifier((float)1.4).build();
    public static final FoodComponent PLAT_BEET = (new FoodComponent.Builder()).hunger(9).saturationModifier((float)1.6).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,600,2),0.65F).build();
    public static final FoodComponent PLAT_POTATO = (new FoodComponent.Builder()).hunger(10).saturationModifier((float)1.8).statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,18000,0),1.00F).statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,18000,4),0.75F).statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,18000,2),0.65F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,18000,3),0.3F).build();
    public static final FoodComponent PLAT_STEAK = (new FoodComponent.Builder()).hunger(7).statusEffect(new StatusEffectInstance(StatusEffects.SATURATION,4,10), 1.00F).saturationModifier((float)1.7).meat().build();
}
