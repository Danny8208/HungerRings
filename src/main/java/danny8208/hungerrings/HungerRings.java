package danny8208.hungerrings;

import danny8208.hungerrings.items.RingHunger;
import danny8208.hungerrings.items.RingIron;
import danny8208.hungerrings.setup.ClientProxy;
import danny8208.hungerrings.setup.IProxy;
import danny8208.hungerrings.setup.ServerProxy;
import danny8208.hungerrings.util.SupportingMods;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;

@Mod("hungerrings")
public class HungerRings
{
    public static final String MODID = "hungerrings";
    public static final CreativeGroup GROUP = new CreativeGroup();
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    private static final Logger logger = LogManager.getLogger();

    public HungerRings() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.init();
        logger.info("HungerRings common setup");
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        if (SupportingMods.CURIOS.isLoaded()) {
            InterModComms.sendTo("curios", CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("ring").setSize(1));
            logger.info("CuriosAPI has been detected. Registering rings to be able to be worn.");
        }
        logger.info("HungerRings IMC setup");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    new RingHunger(),
                    new RingIron()
            );
            logger.info("All HungerRings items has been registered");
        }
    }
}
