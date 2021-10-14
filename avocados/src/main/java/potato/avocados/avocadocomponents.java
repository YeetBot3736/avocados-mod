package potato.avocados;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class avocadocomponents {
    public static final FoodComponent AVOCADO = (new FoodComponent.Builder()).hunger(8).saturationModifier((float) 0.7).statusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,9600,4),0.87F).meat().build();
    public static final FoodComponent CHEESE = (new FoodComponent.Builder()).hunger(6).saturationModifier((float) 0.5).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,9600,4),0.15F).build();
    public static final FoodComponent CHEESECAKE = (new FoodComponent.Builder()).hunger(12).saturationModifier((float) 1.5).build();
    public static final FoodComponent BEESECHURGER = (new FoodComponent.Builder()).hunger(7).saturationModifier((float) 0.9).meat().build();
}
