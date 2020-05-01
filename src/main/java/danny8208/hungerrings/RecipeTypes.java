package danny8208.hungerrings;

import danny8208.hungerrings.blocks.table.InfusionRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public class RecipeTypes {
    public static final IRecipeType<InfusionRecipe> INFUSION = new IRecipeType<InfusionRecipe>() {
        @Override
        public <C extends IInventory> Optional<InfusionRecipe> matches(IRecipe<C> recipe, World worldIn, C inv) {
            return recipe.matches(inv, worldIn) ? Optional.of((InfusionRecipe) recipe) : Optional.empty();
        }
    };

    static {
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(HungerRings.MODID, "infusion"), INFUSION);
    }
}
