package danny8208.hungerrings;

import danny8208.hungerrings.blocks.processor.HungerProcessor;
import danny8208.hungerrings.blocks.processor.HungerProcessorContainer;
import danny8208.hungerrings.blocks.processor.HungerProcessorTile;
import danny8208.hungerrings.items.RingHunger;
import danny8208.hungerrings.items.RingPoison;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static danny8208.hungerrings.HungerRings.GROUP;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistries {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HungerRings.MODID);
    public static final RegistryObject<Item> RING_IRON = ITEMS.register("iron_ring", () -> new Item(new Item.Properties().group(GROUP).maxStackSize(1)));
    public static final RegistryObject<Item> RING_HUNGER = ITEMS.register("hunger_ring", RingHunger::new);
    public static final RegistryObject<Item> RING_POISON = ITEMS.register("poison_ring", RingPoison::new);
    public static final RegistryObject<Item> HUNGER_PROCESSOR_ITEM = ITEMS.register("hunger_processor", () -> new BlockItem(ModRegistries.HUNGER_PROCESSOR.get(), new Item.Properties().group(GROUP)));

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HungerRings.MODID);
    public static final RegistryObject<Block> HUNGER_PROCESSOR = BLOCKS.register("hunger_processor", HungerProcessor::new);

    private static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, HungerRings.MODID);
    public static final RegistryObject<TileEntityType<?>> HUNGER_PROCESSOR_TILE = TILE_ENTITIES.register("hunger_processor", () -> TileEntityType.Builder.create(HungerProcessorTile::new, HUNGER_PROCESSOR.get()).build(null));

    private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, HungerRings.MODID);
    public static final RegistryObject<ContainerType<HungerProcessorContainer>> HUNGER_PROCESSOR_CONTAINER = CONTAINERS.register("hunger_processor", () -> IForgeContainerType.create(((windowId, inv, data) -> new HungerProcessorContainer(windowId, inv, inv.player, inv.player.world, data.readBlockPos()))));

    protected static void init(IEventBus bus) {
        ITEMS.register(bus);
        BLOCKS.register(bus);
        TILE_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                RING_IRON.get(),
                RING_HUNGER.get(),
                RING_POISON.get(),
                HUNGER_PROCESSOR_ITEM.get()
        );
    }

    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                HUNGER_PROCESSOR.get()
        );
    }
    @SubscribeEvent
    public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(
                HUNGER_PROCESSOR_TILE.get()
        );
    }

    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                HUNGER_PROCESSOR_CONTAINER.get()
        );
    }
}
