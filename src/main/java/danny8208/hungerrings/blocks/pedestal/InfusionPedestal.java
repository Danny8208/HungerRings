package danny8208.hungerrings.blocks.pedestal;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class InfusionPedestal  extends Block {
    private static VoxelShape shape = Block.makeCuboidShape(4,0,4, 12, 16, 12);

    public InfusionPedestal() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(5, 1200.0f)
                .sound(SoundType.STONE)
                .harvestTool(ToolType.PICKAXE)
        );
        setRegistryName("infusion_pedestal");
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new InfusionPedestalTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile instanceof InfusionPedestalTile) {
            InfusionPedestalTile pedestal = (InfusionPedestalTile) tile;
            ItemStackHandler inventory = pedestal.inventory;
            ItemStack input = inventory.getStackInSlot(0);
            ItemStack held = player.getHeldItem(handIn);

            if (input.isEmpty() && !held.isEmpty()) {
                ItemStack stack1 = held.copy();
                stack1.setCount(1);
                inventory.setStackInSlot(0, stack1);
                if (held.getCount() >= 1) {
                    held.shrink(1);
                    player.setHeldItem(handIn, held);
                } else {
                    player.setHeldItem(handIn, ItemStack.EMPTY);
                }
                worldIn.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
            } else if (!input.isEmpty()) {
                BlockPos playerPos = player.getPosition();
                ItemEntity item = new ItemEntity(worldIn, playerPos.getX(), playerPos.getY(), playerPos.getZ(), input);
                item.setNoPickupDelay();
                worldIn.addEntity(item);
                inventory.setStackInSlot(0, ItemStack.EMPTY);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof InfusionPedestalTile) {
            InfusionPedestalTile tile = (InfusionPedestalTile) worldIn.getTileEntity(pos);
            worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.inventory.getStackInSlot(0)));
        }
    }
}
