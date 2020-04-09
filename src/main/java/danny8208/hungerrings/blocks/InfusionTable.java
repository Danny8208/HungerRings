package danny8208.hungerrings.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class InfusionTable extends Block {
    private static VoxelShape shape = Block.makeCuboidShape(0,0,0, 16, 12, 16);

    public InfusionTable() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(5, 1200.0f)
                .sound(SoundType.STONE)
                .harvestTool(ToolType.PICKAXE)
        );
        setRegistryName("infusion_table");
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
        return new InfusionTableTile();
    }
}
