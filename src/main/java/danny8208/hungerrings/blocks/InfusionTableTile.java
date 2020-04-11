package danny8208.hungerrings.blocks;

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

public class InfusionTableTile extends TileEntity implements ITickableTileEntity {
    public ItemStackHandler inventory = new ItemStackHandler(1) {
            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }
        };
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> inventory);

    public InfusionTableTile() {
        super(ModBlocks.INFUSION_TABLE_TILE);
    }

    @Override
    public void tick() {
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
