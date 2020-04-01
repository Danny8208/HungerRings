package danny8208.hungerrings.setup;

import net.minecraft.world.World;

public interface IProxy {
    World getClientWorld();

    void init();
}
