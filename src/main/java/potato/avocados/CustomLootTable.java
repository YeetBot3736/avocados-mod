package potato.avocados;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import static potato.avocados.Avocados.AVOCADO;

public class CustomLootTable {
    public static final Identifier SHEEP = EntityType.SHEEP.getLootTableId();
    public static final Identifier SPRUCE = Blocks.SPRUCE_LEAVES.getLootTableId();
    public static void register(){
        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
                    if(SPRUCE.equals(id)){
                        FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(AVOCADO))
                                .withCondition(TableBonusLootCondition.builder(Enchantments.FORTUNE,0.04f,0.1f,0.2f,0.35f).build());
                        supplier.pool(poolBuilder);
                    }
                })
        );
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, table, setter) -> {
            if (SHEEP.equals(Avocados.TEAL_SHEEP)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Avocados.TEAL_WOOL));
                table.pool(poolBuilder);
            }
            else if (SHEEP.equals(Avocados.FUCHSIA_SHEEP)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Avocados.FUCHSIA_WOOL));
                table.pool(poolBuilder);
            }
        });
    }

}
