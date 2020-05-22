package danny8208.hungerrings;

import danny8208.hungerrings.blocks.ModBlocks;
import danny8208.hungerrings.blocks.pedestal.InfusionPedestal;
import danny8208.hungerrings.blocks.pedestal.InfusionPedestalRenderer;
import danny8208.hungerrings.blocks.pedestal.InfusionPedestalTile;
import danny8208.hungerrings.blocks.processor.HungerProcessor;
import danny8208.hungerrings.blocks.processor.HungerProcessorContainer;
import danny8208.hungerrings.blocks.processor.HungerProcessorScreen;
import danny8208.hungerrings.blocks.processor.HungerProcessorTile;
import danny8208.hungerrings.blocks.table.InfusionTable;
import danny8208.hungerrings.blocks.table.InfusionTableRenderer;
import danny8208.hungerrings.blocks.table.InfusionTableTile;
import danny8208.hungerrings.items.RingHunger;
import danny8208.hungerrings.items.RingIron;
import danny8208.hungerrings.items.RingPoison;
import danny8208.hungerrings.setup.ClientProxy;
import danny8208.hungerrings.setup.IProxy;
import danny8208.hungerrings.setup.ServerProxy;
import danny8208.hungerrings.util.SupportingMods;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

@Mod("hungerrings")
public class HungerRings {
    public static final String MODID = "hungerrings";
    public static final CreativeGroup GROUP = new CreativeGroup();
    public static final Logger logger = LogManager.getLogger();
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public HungerRings() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
        ModKeybinding.init();
        ScreenManager.registerFactory(ModBlocks.HUNGER_PROCESSOR_CONTAINER, HungerProcessorScreen::new);
        logger.info("HungerRings common setup");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ModBlocks.INFUSION_TABLE_TILE, InfusionTableRenderer::new);
        ClientRegistry.bindTileEntityRenderer(ModBlocks.INFUSION_PEDESTAL_TILE, InfusionPedestalRenderer::new);
        logger.info("HungerRings client setup");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        if (SupportingMods.CURIOS.isLoaded()) {
            InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("ring").setSize(3));
            logger.info("CuriosAPI has been detected. Registering rings to be able to be worn.");
        }
        logger.info("HungerRings IMC setup");
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    new RingHunger(),
                    new RingIron(),
                    new RingPoison(),

                    new BlockItem(ModBlocks.INFUSION_TABLE, new Item.Properties().group(GROUP)).setRegistryName("infusion_table"),
                    new BlockItem(ModBlocks.INFUSION_PEDESTAL, new Item.Properties().group(GROUP)).setRegistryName("infusion_pedestal"),

                    new BlockItem(ModBlocks.HUNGER_PROCESSOR, new Item.Properties().group(GROUP)).setRegistryName("hunger_processor")
            );
            logger.info("All HungerRings items has been registered");
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    new InfusionTable(),
                    new InfusionPedestal(),
                    new HungerProcessor()
            );
            logger.info("All HungerRings blocks has been registered");
        }

        @SubscribeEvent
        public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
            event.getRegistry().registerAll(
                    TileEntityType.Builder.create(InfusionTableTile::new, ModBlocks.INFUSION_TABLE).build(null).setRegistryName("infusion_table"),
                    TileEntityType.Builder.create(InfusionPedestalTile::new, ModBlocks.INFUSION_PEDESTAL).build(null).setRegistryName("infusion_pedestal"),
                    TileEntityType.Builder.create(HungerProcessorTile::new, ModBlocks.HUNGER_PROCESSOR).build(null).setRegistryName("hunger_processor")
            );
        }

        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
            event.getRegistry().registerAll(
                    IForgeContainerType.create((windowId, inv, data) -> {
                        BlockPos pos = data.readBlockPos();
                        return new HungerProcessorContainer(windowId, inv, HungerRings.proxy.getClientPlayer(), HungerRings.proxy.getClientWorld(), pos);
                    }).setRegistryName("hunger_processor")
            );
        }
    }
}
