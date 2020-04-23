package danny8208.hungerrings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ModKeybinding {
    public static final KeyBinding TOGGLE_POISON = new KeyBinding("key.hungerrings.toggle_poison", KeyConflictContext.IN_GAME, KeyModifier.NONE, InputMappings.getInputByCode(GLFW.GLFW_KEY_TAB, 0), "key.categories.hungerrings");

    public static void init() {
        ClientRegistry.registerKeyBinding(TOGGLE_POISON);
    }
}
