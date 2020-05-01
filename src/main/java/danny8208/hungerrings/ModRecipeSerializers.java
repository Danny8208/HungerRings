package danny8208.hungerrings;

import danny8208.hungerrings.blocks.table.InfusionRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModRecipeSerializers {
    public static final IRecipeSerializer<InfusionRecipe> INFUSION = new InfusionRecipe.Serializer();

    @SubscribeEvent
    public static void onRegisterSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        IForgeRegistry<IRecipeSerializer<?>> registry = event.getRegistry();
        registry.registerAll(
                INFUSION.setRegistryName(new ResourceLocation(HungerRings.MODID, "infusion"))
        );
    }

}
