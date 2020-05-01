package danny8208.hungerrings.blocks.table;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import danny8208.hungerrings.ModRecipeSerializers;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class InfusionRecipe implements IRecipe<IInventory> {
    protected final ResourceLocation id;
    protected final NonNullList<Ingredient> inputs;
    protected final ItemStack result;

    public InfusionRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack result) {
        this.id = id;
        this.inputs = inputs;
        this.result = result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.INFUSION;
    }

    @Override
    public IRecipeType<?> getType() {
        return null;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        ItemStack tableStack = inv.getStackInSlot(0);
        return this.inputs.get(0).test(tableStack);
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfusionRecipe> {
        @Override
        public InfusionRecipe read(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> inputs = NonNullList.create();
            JsonObject input = JSONUtils.getJsonObject(json, "input");
            inputs.add(Ingredient.deserialize(input));

            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            for (int i = 0; i < ingredients.size(); i++) {
                inputs.add(Ingredient.deserialize(ingredients.get(i)));
            }

            ItemStack output = ShapedRecipe.deserializeItem(json.getAsJsonObject("result"));

            return new InfusionRecipe(recipeId, inputs, output);
        }

        @Override
        public InfusionRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            int size = buffer.readVarInt();

            NonNullList<Ingredient> inputs = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.read(buffer));
            }

            ItemStack output = buffer.readItemStack();

            return new InfusionRecipe(recipeId, inputs, output);
        }

        @Override
        public void write(PacketBuffer buffer, InfusionRecipe recipe) {
            buffer.writeVarInt(recipe.inputs.size());

            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.result);
        }
    }
}
