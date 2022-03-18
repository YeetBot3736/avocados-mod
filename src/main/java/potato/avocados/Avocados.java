package potato.avocados;

import com.chocohead.mm.api.ClassTinkerers;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.Settings;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potato.avocados.armor.BaseArmor;
import potato.avocados.armor.PlatinumArmorMaterial;
import potato.avocados.block.BedBlk;
import potato.avocados.block.CakeCandle;
import potato.avocados.block.ShulkerBlock;
import potato.avocados.entity.bed.BedEntity;
import potato.avocados.entity.shulker.ShulkerBlockEntity;
import potato.avocados.item.*;
import potato.avocados.mixin.RendererMixin;
import potato.avocados.mixin.SheepDropMixin;
import potato.avocados.ore.NetherPlatinumOre;
import potato.avocados.ore.PlatinumOre;
import potato.avocados.redstone.ButtonBlk;
import potato.avocados.redstone.DoorBlk;
import potato.avocados.redstone.PlatTrapDoor;
import potato.avocados.redstone.PressPlateBlk;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static net.minecraft.client.render.TexturedRenderLayers.SHULKER_BOXES_ATLAS_TEXTURE;

public class Avocados implements ModInitializer{
	public static final Logger LOGGER = LoggerFactory.getLogger("avocados");
	public static final Identifier TEAL_SHEEP = new Identifier("entities/sheep/teal");
	public static final Identifier FUCHSIA_SHEEP = new Identifier("entities/sheep/fuchsia");
	public static final Item AVOCADO = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.AVOCADO));
	public static final Item CHEESE = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.CHEESE));
	public static final Item CHEESECAKE = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.CHEESECAKE));
	public static final Item BEESECHURGER = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.BEESECHURGER));
	public static final Item RAW_PLATINUM = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item PLATINUM_INGOT = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item PLATINUM_NUGGET = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item COOKED_BEET = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.COOKED_BEET));
	public static final Item PLAT_BEET = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.PLAT_BEET));
	public static final Block PLAT_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(55.0f,65.0f).sounds(BlockSoundGroup.METAL));
	public static final Item PLAT_POTATO = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.PLAT_POTATO));
	public static final Item NETHERITE_NUGGET = new Item(new Settings().fireproof().group(ItemGroup.MISC));
	public static final Block RAW_PLAT_BLOCK =  new Block(FabricBlockSettings.of(Material.METAL).strength(55.0f,65.0f).sounds(BlockSoundGroup.METAL));
	public static final Item PLAT_STEAK = new Item(new Settings().group(ItemGroup.FOOD).food(AvocadoComponents.PLAT_STEAK));
	public static final Block PLAT_ORE = new PlatinumOre(FabricBlockSettings.of(Material.METAL).strength(35.0f,90.0f).sounds(BlockSoundGroup.STONE));
	public static final ArmorMaterial PLAT_ARMOUR = new PlatinumArmorMaterial();
	public static final Block PLAT_PRESSURE_PLATE = new PressPlateBlk(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.METAL).strength(51.5f,61.5f).sounds(BlockSoundGroup.METAL));
	public static final Block PLAT_DOOR = new DoorBlk(FabricBlockSettings.of(Material.METAL).strength(55.0f,65.0f).sounds(BlockSoundGroup.METAL));
	public static final Block PLAT_BUTTON = new ButtonBlk(FabricBlockSettings.of(Material.METAL).strength(6.0f,6.0f).sounds(BlockSoundGroup.METAL));
	public static final Item PLAT_HORSE_ARMOR = new PlatHorseArmor(7, "platinum", new Item.Settings().maxCount(1).group(ItemGroup.MISC));
	public static final Block PLAT_TRAPDOOR = new PlatTrapDoor(FabricBlockSettings.of(Material.METAL).strength(65.0f,65.0f).sounds(BlockSoundGroup.METAL));
	public static final Block NETHER_PLAT_ORE = new NetherPlatinumOre(FabricBlockSettings.of(Material.METAL).strength(35.0f,35.0f).sounds(BlockSoundGroup.NETHERRACK));
	public static final DyeColor TEAL_COLOR = ClassTinkerers.getEnum(DyeColor.class, "TEAL");
	public static final DyeColor FUCHSIA_COLOR= ClassTinkerers.getEnum(DyeColor.class, "FUCHSIA");
	public static final Item TEAL_DYE= new DyeItem(TEAL_COLOR, new Item.Settings().group(ItemGroup.MISC));
	public static final Block TEAL_TERRACOTTA = new Block(FabricBlockSettings.of(Material.STONE).strength(12.5f,42.0f).sounds(BlockSoundGroup.STONE));
	public static final Block TEAL_WOOL = new Block(FabricBlockSettings.of(Material.WOOL).strength(8.0f,8.0f).sounds(BlockSoundGroup.WOOL));
	public static final Block TEAL_GLAZED_TERRACOTTA = new GlazedTerracottaBlock(FabricBlockSettings.of(Material.STONE).strength(14.0f,14.0f).sounds(BlockSoundGroup.STONE));
	public static final Block TEAL_CARPET = new CarpetBlock(FabricBlockSettings.of(Material.CARPET).strength(1.0f,1.0f).sounds(BlockSoundGroup.WOOL));
	public static final Block TEAL_STAINED_GLASS = new StainedGlassBlock(TEAL_COLOR, FabricBlockSettings.of(Material.GLASS).strength(3.0f,3.0f).sounds(BlockSoundGroup.GLASS).nonOpaque());
	public static final Block TEAL_STAINED_GLASS_PANE = new StainedGlassPaneBlock(TEAL_COLOR, FabricBlockSettings.of(Material.GLASS).strength(3.0f,3.0f).sounds(BlockSoundGroup.GLASS).nonOpaque());
	public static final Block TEAL_SHULKER_BOX = createShulkerBoxBlock(TEAL_COLOR, FabricBlockSettings.of(Material.SHULKER_BOX).strength(20.0f,20.0f).sounds(BlockSoundGroup.STONE));
	public static final Block TEAL_CONCRETE = new Block(FabricBlockSettings.of(Material.STONE).strength(18.0f,18.0f).sounds(BlockSoundGroup.STONE));
	public static final Block TEAL_CONCRETE_POWDER = new ConcretePowderBlock(TEAL_CONCRETE, FabricBlockSettings.of(Material.SOIL).strength(5.0f,5.0f).sounds(BlockSoundGroup.SAND));
	public static final Block TEAL_BED = createBedBlock(TEAL_COLOR);
	public static final Block TEAL_CANDLE = new CandleBlock(FabricBlockSettings.of(Material.DECORATION).strength(1.0f,1.0f).sounds(BlockSoundGroup.CANDLE));
	public static final Block TEAL_CANDLE_CAKE = new CakeCandle(TEAL_CANDLE, FabricBlockSettings.of(Material.DECORATION).strength(1.0f,1.0f).sounds(BlockSoundGroup.CANDLE));
	public static final Block TEAL_BANNER = new BannerBlock(TEAL_COLOR, FabricBlockSettings.of(Material.WOOD).strength(10.0f,10.0f).sounds(BlockSoundGroup.WOOD));
	public static final Block TEAL_WALL_BANNER = new WallBannerBlock(TEAL_COLOR, FabricBlockSettings.of(Material.WOOD).strength(10.0f,10.0f).sounds(BlockSoundGroup.WOOD));
	public static final Item FUCHSIA_DYE= new DyeItem(FUCHSIA_COLOR, new Item.Settings().group(ItemGroup.MISC));
	public static final Block FUCHSIA_TERRACOTTA = new Block(FabricBlockSettings.of(Material.STONE).strength(12.5f,42.0f).sounds(BlockSoundGroup.STONE));
	public static final Block FUCHSIA_WOOL = new Block(FabricBlockSettings.of(Material.WOOL).strength(8.0f,8.0f).sounds(BlockSoundGroup.WOOL));
	public static final Block FUCHSIA_GLAZED_TERRACOTTA = new GlazedTerracottaBlock(FabricBlockSettings.of(Material.STONE).strength(14.0f,14.0f).sounds(BlockSoundGroup.STONE));
	public static final Block FUCHSIA_CARPET = new CarpetBlock(FabricBlockSettings.of(Material.CARPET).strength(1.0f,1.0f).sounds(BlockSoundGroup.WOOL));
	public static final Block FUCHSIA_STAINED_GLASS = new StainedGlassBlock(FUCHSIA_COLOR, FabricBlockSettings.of(Material.GLASS).strength(3.0f,3.0f).sounds(BlockSoundGroup.GLASS).nonOpaque());
	public static final Block FUCHSIA_STAINED_GLASS_PANE = new StainedGlassPaneBlock(FUCHSIA_COLOR, FabricBlockSettings.of(Material.GLASS).strength(3.0f,3.0f).sounds(BlockSoundGroup.GLASS).nonOpaque());
	public static final Block FUCHSIA_SHULKER_BOX = createShulkerBoxBlock(FUCHSIA_COLOR, FabricBlockSettings.of(Material.SHULKER_BOX).strength(20.0f,20.0f).sounds(BlockSoundGroup.STONE));
	public static final Block FUCHSIA_CONCRETE = new Block(FabricBlockSettings.of(Material.STONE).strength(18.0f,18.0f).sounds(BlockSoundGroup.STONE));
	public static final Block FUCHSIA_CONCRETE_POWDER = new ConcretePowderBlock(FUCHSIA_CONCRETE, FabricBlockSettings.of(Material.SOIL).strength(5.0f,5.0f).sounds(BlockSoundGroup.SAND));
	public static final Block FUCHSIA_BED = createBedBlock(FUCHSIA_COLOR);
	public static final Block FUCHSIA_CANDLE = new CandleBlock(FabricBlockSettings.of(Material.DECORATION).strength(1.0f,1.0f).sounds(BlockSoundGroup.CANDLE));
	public static final Block FUCHSIA_CANDLE_CAKE = new CakeCandle(FUCHSIA_CANDLE, FabricBlockSettings.of(Material.DECORATION).strength(1.0f,1.0f).sounds(BlockSoundGroup.CANDLE));
	public static final Block FUCHSIA_BANNER = new BannerBlock(FUCHSIA_COLOR, FabricBlockSettings.of(Material.WOOD).strength(10.0f,10.0f).sounds(BlockSoundGroup.WOOD));
	public static final Block FUCHSIA_WALL_BANNER = new WallBannerBlock(FUCHSIA_COLOR, FabricBlockSettings.of(Material.WOOD).strength(10.0f,10.0f).sounds(BlockSoundGroup.WOOD));
	public static final List<SpriteIdentifier> HALLO = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black", "teal", "fuchsia").map(string -> new SpriteIdentifier(SHULKER_BOXES_ATLAS_TEXTURE, new Identifier("entity/shulker/shulker_" + string))).collect(ImmutableList.toImmutableList());public static final EntityModelLayer SH = new EntityModelLayer(new Identifier("avocados","shulker"), "main");
	public static BlockEntityType<ShulkerBlockEntity> SHULKER_E;
	public static BlockEntityType<BedEntity> BED_E;
	public static BlockEntityType<BannerBlockEntity> TEAL_BANNER_E, FUCHSIA_BANNER_E;

	public void regItem(String ItemName, Item item){
		Registry.register(Registry.ITEM, new Identifier("avocados", ItemName), item);
	}
	public void regBlock(String BlockName, Block block, ItemGroup group){
		Registry.register(Registry.BLOCK, new Identifier("avocados",BlockName), block);
		if(block instanceof BannerBlock){
			Registry.register(Registry.ITEM, new Identifier("avocados", BlockName), new BlockItem(block, new Item.Settings().group(group).maxCount(16)));
		}
		else if(block instanceof ShulkerBlock || block instanceof BedBlock){
			Registry.register(Registry.ITEM, new Identifier("avocados", BlockName), new BlockItem(block, new Item.Settings().group(group).maxCount(1)));
		}
		else Registry.register(Registry.ITEM, new Identifier("avocados",BlockName),new BlockItem(block, new Item.Settings().group(group)));
	}
	private static ShulkerBlock createShulkerBoxBlock(DyeColor color, AbstractBlock.Settings settings) {
		AbstractBlock.ContextPredicate contextPredicate = (state, world, pos) -> {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (!(blockEntity instanceof ShulkerBoxBlockEntity)) {
				return true;
			}
			ShulkerBlockEntity shulkerBlockEntity = (ShulkerBlockEntity)blockEntity;
			return shulkerBlockEntity.suffocates();
		};
		return new ShulkerBlock(color, settings.strength(2.0f).dynamicBounds().nonOpaque().suffocates(contextPredicate).blockVision(contextPredicate));
	}
	private static final ConfiguredFeature<?, ?> ORE_PLATINUM = Feature.ORE
			.configure(new OreFeatureConfig(
					new BlockMatchRuleTest(Blocks.END_STONE),
					PLAT_ORE.getDefaultState(), 12
			));
	private static final PlacedFeature ORE_PLATINUM_THE_END = ORE_PLATINUM.withPlacement(CountPlacementModifier.of(2), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(),YOffset.fixed(80)));
	private static final ConfiguredFeature<?, ?> NETHER_PLATI_ORE = Feature.ORE
			.configure(new OreFeatureConfig(OreConfiguredFeatures.BASE_STONE_NETHER,
					NETHER_PLAT_ORE.getDefaultState(), 7
			));
	private static final PlacedFeature NETHER_PLATINUM_ORE = NETHER_PLATI_ORE.withPlacement(CountPlacementModifier.of(5),SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(),YOffset.fixed(85)));
	private static BedBlk createBedBlock(DyeColor color) {
		return new BedBlk(color, AbstractBlock.Settings.of(Material.WOOL, state -> state.get(BedBlock.PART) == BedPart.FOOT ? color.getMapColor() : MapColor.WHITE_GRAY).sounds(BlockSoundGroup.WOOD).strength(0.2f).nonOpaque());
	}
	public static Map<DyeColor, ItemConvertible> MOD_DROP = SheepDropMixin.getDROPS();
	@Override
  public void onInitialize() {
		RendererMixin.setTexture(HALLO);	
		CustomLootTable.register();
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(Avocados.AVOCADO.asItem(), 0.65f);
		MOD_DROP.put(TEAL_COLOR, TEAL_WOOL);
		MOD_DROP.put(FUCHSIA_COLOR, FUCHSIA_WOOL);
		SheepDropMixin.setDrops(MOD_DROP);
		SHULKER_E = Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier("avocados","shulker_box"), FabricBlockEntityTypeBuilder.create(ShulkerBlockEntity::new, TEAL_SHULKER_BOX, FUCHSIA_SHULKER_BOX).build());
		BED_E = Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier("avocados","bed"), FabricBlockEntityTypeBuilder.create(BedEntity::new,TEAL_BED, FUCHSIA_BED).build());
		TEAL_BANNER_E = Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier("avocados","teal_banner"), FabricBlockEntityTypeBuilder.create(BannerBlockEntity::new).build());
		FUCHSIA_BANNER_E = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("avocados","fuchsia_banner"), FabricBlockEntityTypeBuilder.create(BannerBlockEntity::new).build());
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("avocados", "platinum_ore"), ORE_PLATINUM);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("avocados", "platinum_ore"), ORE_PLATINUM_THE_END);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("avocados", "platinum_ore")));
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("avocados", "nether_platinum_ore"), NETHER_PLATI_ORE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier("avocados", "nether_platinum_ore"), NETHER_PLATINUM_ORE);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier("avocados", "nether_platinum_ore")));
		Registry.register(Registry.ITEM, new Identifier("avocados", "avocado"), AVOCADO);
		Registry.register(Registry.ITEM, new Identifier("avocados", "cheese"), CHEESE);
		Registry.register(Registry.ITEM, new Identifier("avocados", "cheesecake"), CHEESECAKE);
		Registry.register(Registry.ITEM, new Identifier("avocados", "beesechurger"), BEESECHURGER);
		Registry.register(Registry.ITEM, new Identifier("avocados", "raw_platinum"), RAW_PLATINUM);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_ingot"), PLATINUM_INGOT);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_nugget"), PLATINUM_NUGGET);
		Registry.register(Registry.ITEM, new Identifier("avocados", "cooked_beetroot"), COOKED_BEET);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_beetroot"), PLAT_BEET);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "platinum_block"), PLAT_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_block"), new BlockItem(PLAT_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_potato"), PLAT_POTATO);
		Registry.register(Registry.ITEM, new Identifier("avocados", "netherite_nugget"), NETHERITE_NUGGET);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "raw_platinum_block"), RAW_PLAT_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("avocados", "raw_platinum_block"), new BlockItem(RAW_PLAT_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_steak"), PLAT_STEAK);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "platinum_ore"), PLAT_ORE);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_ore"), new BlockItem(PLAT_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_helmet"), new BaseArmor(PLAT_ARMOUR, EquipmentSlot.HEAD));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_chestplate"), new BaseArmor(PLAT_ARMOUR, EquipmentSlot.CHEST));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_leggings"), new BaseArmor(PLAT_ARMOUR, EquipmentSlot.LEGS));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_boots"), new BaseArmor(PLAT_ARMOUR, EquipmentSlot.FEET));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_pickaxe"), new PlatPick(new ToolMaterialPlatinum()));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_axe"), new PlatAxe(new ToolMaterialPlatinum()));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_shovel"), new PlatShovel(new ToolMaterialPlatinum()));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_sword"), new PlatSword(new ToolMaterialPlatinum()));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_hoe"), new PlatHoe(new ToolMaterialPlatinum()));
		Registry.register(Registry.BLOCK, new Identifier("avocados", "platinum_pressure_plate"), PLAT_PRESSURE_PLATE);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_pressure_plate"), new BlockItem(PLAT_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.BLOCK, new Identifier("avocados", "platinum_door"), PLAT_DOOR);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_door"), new BlockItem(PLAT_DOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.BLOCK, new Identifier("avocados", "platinum_button"), PLAT_BUTTON);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_button"), new BlockItem(PLAT_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_horse_armor"), PLAT_HORSE_ARMOR);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "platinum_trapdoor"), PLAT_TRAPDOOR);
		Registry.register(Registry.ITEM, new Identifier("avocados", "platinum_trapdoor"), new BlockItem(PLAT_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
		Registry.register(Registry.BLOCK, new Identifier("avocados", "nether_platinum_ore"), NETHER_PLAT_ORE);
		Registry.register(Registry.ITEM, new Identifier("avocados", "nether_platinum_ore"), new BlockItem(NETHER_PLAT_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.ITEM, new Identifier("avocados", "teal_dye"), TEAL_DYE);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "teal_terracotta"), TEAL_TERRACOTTA);
		Registry.register(Registry.ITEM, new Identifier("avocados", "teal_terracotta"), new BlockItem(TEAL_TERRACOTTA, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.BLOCK, new Identifier("avocados", "teal_wool"), TEAL_WOOL);
		Registry.register(Registry.ITEM, new Identifier("avocados", "teal_wool"), new BlockItem(TEAL_WOOL, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		Registry.register(Registry.BLOCK, new Identifier("avocados", "teal_glazed_terracotta"), TEAL_GLAZED_TERRACOTTA);
		Registry.register(Registry.ITEM, new Identifier("avocados", "teal_glazed_terracotta"), new BlockItem(TEAL_GLAZED_TERRACOTTA, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		regBlock("teal_carpet", TEAL_CARPET, ItemGroup.DECORATIONS);
		regBlock("teal_stained_glass", TEAL_STAINED_GLASS, ItemGroup.BUILDING_BLOCKS);
		regBlock("teal_stained_glass_pane", TEAL_STAINED_GLASS_PANE, ItemGroup.DECORATIONS);
		regBlock("teal_shulker_box", TEAL_SHULKER_BOX, ItemGroup.DECORATIONS);
		regBlock("teal_concrete", TEAL_CONCRETE, ItemGroup.BUILDING_BLOCKS);
		regBlock("teal_concrete_powder", TEAL_CONCRETE_POWDER, ItemGroup.BUILDING_BLOCKS);
		regBlock("teal_bed", TEAL_BED, ItemGroup.DECORATIONS);
		regBlock("teal_candle", TEAL_CANDLE, ItemGroup.DECORATIONS);
		regBlock("teal_banner", TEAL_BANNER, ItemGroup.DECORATIONS);
		regItem("fuchsia_dye", FUCHSIA_DYE);
		regBlock("fuchsia_terracotta", FUCHSIA_TERRACOTTA, ItemGroup.BUILDING_BLOCKS);
		regBlock("fuchsia_wool", FUCHSIA_WOOL, ItemGroup.BUILDING_BLOCKS);
		regBlock("fuchsia_glazed_terracotta", FUCHSIA_GLAZED_TERRACOTTA, ItemGroup.BUILDING_BLOCKS);
		regBlock("fuchsia_carpet", FUCHSIA_CARPET, ItemGroup.DECORATIONS);
		regBlock("fuchsia_stained_glass", FUCHSIA_STAINED_GLASS, ItemGroup.BUILDING_BLOCKS);
		regBlock("fuchsia_stained_glass_pane", FUCHSIA_STAINED_GLASS_PANE, ItemGroup.DECORATIONS);
		regBlock("fuchsia_shulker_box", FUCHSIA_SHULKER_BOX, ItemGroup.DECORATIONS);
		regBlock("fuchsia_concrete", FUCHSIA_CONCRETE, ItemGroup.BUILDING_BLOCKS);
		regBlock("fuchsia_concrete_powder", FUCHSIA_CONCRETE_POWDER, ItemGroup.BUILDING_BLOCKS);
		regBlock("fuchsia_bed", FUCHSIA_BED, ItemGroup.DECORATIONS);
		regBlock("fuchsia_candle", FUCHSIA_CANDLE, ItemGroup.DECORATIONS);
		regBlock("fuchsia_banner", FUCHSIA_BANNER, ItemGroup.DECORATIONS);
		//No need any BlockItem for WallBanners or CandleCakeBlocks
		Registry.register(Registry.BLOCK, new Identifier("avocados", "teal_wall_banner"), TEAL_WALL_BANNER);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "fuchsia_wall_banner"), FUCHSIA_WALL_BANNER);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "teal_candle_cake"), TEAL_CANDLE_CAKE);
		Registry.register(Registry.BLOCK, new Identifier("avocados", "fuchsia_candle_cake"), FUCHSIA_CANDLE_CAKE);
	}	
}


//Thanks to mycf#6231, JoeMama#7129, Yeyito#1834, KaloyanKYS#5185 and Velimoss#0330, together with the whole of Suited Llama's Community and Fabriccord for helping with this mod :D