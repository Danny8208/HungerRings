package danny8208.hungerrings.items;

import net.minecraft.item.Item;

import static danny8208.hungerrings.HungerRings.GROUP;

public class RingIron extends Item {
    public RingIron() {
        super(new Properties().group(GROUP).maxStackSize(1));
        setRegistryName("iron_ring");
    }
}
