package danny8208.hungerrings.setup;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {
    @Override
    public World getClientWorld() {
        throw new IllegalStateException("this can only be ran on the client");
    }

    @Override
    public void init() {
    }
}