package danny8208.hungerrings.blocks.processor;

import danny8208.hungerrings.blocks.ModBlocks;
import danny8208.hungerrings.items.ModItems;
import danny8208.hungerrings.util.HungerNBT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HungerProcessorTile extends TileEntity implements ITickableTileEntity {
    private static int milk;
    private static int hunger;
    private static float saturation;
    public ItemStackHandler inventory = new ItemStackHandler(2) {
        final Item hungerring = ModItems.RING_HUNGER;
        final Item poisonring = ModItems.RING_POISON;

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return (slot == 0 && (stack.getItem() == hungerring && getStackInSlot(0) == ItemStack.EMPTY)
                    || (stack.getItem() == poisonring && getStackInSlot(0) == ItemStack.EMPTY))
                    || (slot == 1 && stack.getItem().isFood());
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> inventory);

    public HungerProcessorTile() {
        super(ModBlocks.HUNGER_PROCESSOR_TILE);
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            ItemStack stack0 = inventory.getStackInSlot(0);
            ItemStack stack1 = inventory.getStackInSlot(1);

            if (stack0.getItem() == ModItems.RING_HUNGER) {
                ItemStack hungerRing = inventory.getStackInSlot(0);
                HungerNBT.addHunger(hungerRing, hunger);
                HungerNBT.addSaturation(hungerRing, saturation);
                hunger = 0;
                saturation = 0f;
            }
            if (stack1.getItem().isFood()) {
                hunger = stack1.getItem().getFood().getHealing();
                saturation = stack1.getItem().getFood().getSaturation();
                stack1.shrink(1);
            }
        }
    }

    public void addMilk(int milk) {
        HungerProcessorTile.milk = HungerProcessorTile.milk + milk;
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
        inventory.deserializeNBT(tag);
        tag.getInt("StoredMilk");
        tag.getInt("StoredHunger");
        tag.getInt("StoredSaturation");
        milk = tag.getInt("StoredMilk");
        hunger = tag.getInt("StoredHunger");
        saturation = tag.getInt("StoredSaturation");
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.putInt("StoredMilk", milk);
        tag.putInt("StoredHunger", hunger);
        tag.putFloat("StoredSaturation", saturation);
        tag.merge(tag);
        tag.merge(inventory.serializeNBT());
        return tag;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}
