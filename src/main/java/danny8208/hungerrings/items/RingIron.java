package danny8208.hungerrings.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static danny8208.hungerrings.HungerRings.GROUP;

public class RingIron extends Item {
    public RingIron() {
        super(new Properties().group(GROUP).maxStackSize(1));
        setRegistryName("iron_ring");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.hungerrings.crafting"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
