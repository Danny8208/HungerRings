package danny8208.hungerrings.blocks.processor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class HungerProcessor extends Block {
    public HungerProcessor() {
        super(Properties.create(Material.ROCK)
                .sound(SoundType.STONE)
                .hardnessAndResistance(2.5f)
                .harvestTool(ToolType.PICKAXE)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new HungerProcessorTile();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof HungerProcessorTile && worldIn.getTileEntity(pos) != null) {
            HungerProcessorTile tile = (HungerProcessorTile) worldIn.getTileEntity(pos);
            if (player.getHeldItemMainhand().getItem() == Items.MILK_BUCKET) {
                tile.hungerSaturationHandler.addMilk(1);
                player.getHeldItemMainhand().shrink(1);
                ItemEntity bucket = new ItemEntity(worldIn, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.BUCKET));
                bucket.setNoPickupDelay();
                worldIn.addEntity(bucket);
            }
            if (player.getHeldItemMainhand().getItem() == Items.BUCKET && tile.hungerSaturationHandler.getMilk() > 0) {
                ItemEntity bucket = new ItemEntity(worldIn, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.MILK_BUCKET));
                bucket.setNoPickupDelay();
                worldIn.addEntity(bucket);
                tile.hungerSaturationHandler.subtractMilk(1);
                player.getHeldItemMainhand().shrink(1);
            }
            if (player.isCrouching()) {
                player.sendStatusMessage(new StringTextComponent("StoredMilk: " + tile.hungerSaturationHandler.getMilk() + "    " +
                        "StoredHunger: " + tile.hungerSaturationHandler.getHunger() + "    " +
                        "StoredSaturation " + tile.hungerSaturationHandler.getSaturation()
                ), true);
            }
            if (player.getHeldItem(handIn).isEmpty() && !player.isCrouching()) {
                NetworkHooks.openGui((ServerPlayerEntity) player, tile, pos);
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, p_225533_6_);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (!worldIn.isRemote && worldIn.getTileEntity(pos) instanceof HungerProcessorTile) {
            HungerProcessorTile tile = (HungerProcessorTile) worldIn.getTileEntity(pos);
            worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.inventory.getStackInSlot(0)));
            worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.inventory.getStackInSlot(1)));
        }
    }
}
