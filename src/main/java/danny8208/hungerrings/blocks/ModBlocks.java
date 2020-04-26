package danny8208.hungerrings.blocks;

import danny8208.hungerrings.blocks.pedestal.InfusionPedestal;
import danny8208.hungerrings.blocks.pedestal.InfusionPedestalTile;
import danny8208.hungerrings.blocks.processor.HungerProcessor;
import danny8208.hungerrings.blocks.processor.HungerProcessorContainer;
import danny8208.hungerrings.blocks.processor.HungerProcessorTile;
import danny8208.hungerrings.blocks.table.InfusionTable;
import danny8208.hungerrings.blocks.table.InfusionTableTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
    @ObjectHolder("hungerrings:infusion_table")
    public static InfusionTable INFUSION_TABLE;
    @ObjectHolder("hungerrings:infusion_pedestal")
    public static InfusionPedestal INFUSION_PEDESTAL;
    @ObjectHolder("hungerrings:hunger_processor")
    public static HungerProcessor HUNGER_PROCESSOR;

    @ObjectHolder("hungerrings:infusion_table")
    public static TileEntityType<InfusionTableTile> INFUSION_TABLE_TILE;
    @ObjectHolder("hungerrings:infusion_pedestal")
    public static TileEntityType<InfusionPedestalTile> INFUSION_PEDESTAL_TILE;
    @ObjectHolder("hungerrings:hunger_processor")
    public static TileEntityType<HungerProcessorTile> HUNGER_PROCESSOR_TILE;

    @ObjectHolder("hungerrings:hunger_processor")
    public static ContainerType<HungerProcessorContainer> HUNGER_PROCESSOR_CONTAINER;
}
