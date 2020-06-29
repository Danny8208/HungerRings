package danny8208.hungerrings.blocks.processor;

import danny8208.hungerrings.ModRegistries;
import danny8208.hungerrings.util.HungerNBT;
import danny8208.hungerrings.util.HungerSaturationHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HungerProcessorTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public ItemStackHandler inventory = new ItemStackHandler(2) {
        final Item hungerring = ModRegistries.RING_HUNGER.get();
        final Item poisonring = ModRegistries.RING_POISON.get();

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return (slot == 0 && (stack.getItem() == hungerring && getStackInSlot(0) == ItemStack.EMPTY)
                    || (stack.getItem() == poisonring && getStackInSlot(0) == ItemStack.EMPTY))
                    || (slot == 1 && (stack.getItem().isFood())
            );
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> inventory);
    public HungerSaturationHandler hungerSaturationHandler = new HungerSaturationHandler();

    public HungerProcessorTile() {
        super(ModRegistries.HUNGER_PROCESSOR_TILE.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            ItemStack stack0 = inventory.getStackInSlot(0);
            ItemStack stack1 = inventory.getStackInSlot(1);

            if (stack0.getItem() == ModRegistries.RING_HUNGER.get()) {
                ItemStack hungerRing = inventory.getStackInSlot(0);
                HungerNBT.addHunger(hungerRing, hungerSaturationHandler.getHunger());
                HungerNBT.addSaturation(hungerRing, hungerSaturationHandler.getSaturation());
                hungerSaturationHandler.setHunger(0);
                hungerSaturationHandler.setSaturation(0f);
            }
            if (stack0.getItem() == ModRegistries.RING_POISON.get()) {
                ItemStack poisonRing = inventory.getStackInSlot(0);
                HungerNBT.addMilk(poisonRing, hungerSaturationHandler.getMilk());
                hungerSaturationHandler.setMilk(0);
            }
            if (stack1.getItem().isFood()) {
                hungerSaturationHandler.addHunger(stack1.getItem().getFood().getHealing());
                hungerSaturationHandler.addSaturation(stack1.getItem().getFood().getSaturation());
                stack1.shrink(1);
            }
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        hungerSaturationHandler.deserializeNBT(nbt);
        inventory.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.merge(hungerSaturationHandler.serializeNBT());
        tag.merge(inventory.serializeNBT());
        return tag;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new HungerProcessorContainer(p_createMenu_1_, p_createMenu_2_, p_createMenu_3_, world, pos);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.hungerrings.hunger_processor");
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
