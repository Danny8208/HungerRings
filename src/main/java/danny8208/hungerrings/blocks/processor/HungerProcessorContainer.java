package danny8208.hungerrings.blocks.processor;

import danny8208.hungerrings.blocks.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class HungerProcessorContainer extends Container {
    protected final HungerProcessorTile tileEntity;
    private final PlayerEntity playerEntity;

    public HungerProcessorContainer(int id, PlayerInventory inventory, PlayerEntity player, World world, BlockPos pos) {
        super(ModBlocks.HUNGER_PROCESSOR_CONTAINER, id);
        tileEntity = (HungerProcessorTile) world.getTileEntity(pos);
        playerEntity = player;
        IItemHandler playerInventory = new InvWrapper(inventory);
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(new SlotItemHandler(h, 0, 44, 20));
            addSlot(new SlotItemHandler(h, 1, 116, 20));
        });

        //inventory
        for (int l = 0; l < 3; ++l) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new SlotItemHandler(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 79));
            }
        }

        //hotbar
        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new SlotItemHandler(playerInventory, i1, 8 + i1 * 18, 137));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.HUNGER_PROCESSOR);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < tileEntity.inventory.getSlots()) {
                if (!this.mergeItemStack(itemstack1, tileEntity.inventory.getSlots(), this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, tileEntity.inventory.getSlots(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
