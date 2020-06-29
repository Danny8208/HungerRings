package danny8208.hungerrings;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeGroup extends ItemGroup {
    public CreativeGroup() {
        super(HungerRings.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModRegistries.RING_HUNGER.get());
    }
}
