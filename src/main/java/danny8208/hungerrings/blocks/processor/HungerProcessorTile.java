package danny8208.hungerrings.blocks.processor;

import danny8208.hungerrings.blocks.ModBlocks;
import danny8208.hungerrings.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HungerProcessorTile extends TileEntity {
    public ItemStackHandler inventory = new ItemStackHandler(2) {
        final Item hungerring = ModItems.RING_HUNGER;
        final Item poisonring = ModItems.RING_POISON;
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return (stack.getItem() == hungerring && getStackInSlot(0) == ItemStack.EMPTY) || (stack.getItem() == poisonring &&  getStackInSlot(0) == ItemStack.EMPTY)  || stack.getItem().isFood();
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
    public void read(CompoundNBT tag) {
        super.read(tag);
        inventory.deserializeNBT(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
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
