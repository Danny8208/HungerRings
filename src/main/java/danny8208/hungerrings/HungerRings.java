package danny8208.hungerrings;

import danny8208.hungerrings.blocks.processor.HungerProcessorScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod("hungerrings")
public class HungerRings {
    public static final String MODID = "hungerrings";
    public static final CreativeGroup GROUP = new CreativeGroup();
    public static final Logger logger = LogManager.getLogger();

    public HungerRings() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        ModRegistries.init(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModKeybinding.init();
        ScreenManager.registerFactory(ModRegistries.HUNGER_PROCESSOR_CONTAINER.get(), HungerProcessorScreen::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("curios")) {
            InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("ring").size(2).build());
            logger.info("CuriosAPI has been detected. Registering rings to be able to be worn.");
        }
    }
}