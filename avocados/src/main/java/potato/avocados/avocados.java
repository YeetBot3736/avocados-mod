package potato.avocados;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class avocados implements ModInitializer {

	public static final Item AVOCADO = new Item(new Item.Settings().group(ItemGroup.FOOD).food(avocadocomponents.AVOCADO));
	public static final Item CHEESE = new Item(new Settings().group(ItemGroup.FOOD).food(avocadocomponents.CHEESE));
	public static final Item CHEESECAKE = new Item(new Settings().group(ItemGroup.FOOD).food(avocadocomponents.CHEESECAKE));
	public static final Item BEESECHURGER = new Item(new Settings().group(ItemGroup.FOOD).food(avocadocomponents.BEESECHURGER));
	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("avocados","avocado"), AVOCADO);
		Registry.register(Registry.ITEM, new Identifier("avocados","cheese"), CHEESE);
		Registry.register(Registry.ITEM, new Identifier("avocados","cheesecake"), CHEESECAKE);
		Registry.register(Registry.ITEM, new Identifier("avocados","beesechurger"), BEESECHURGER);
	}
}
