package danny8208.hungerrings.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class HungerNBT {
    //hunger nbt
    public static void addHunger(ItemStack stack, int hunger) {
        stack.getTag().putInt("StoredHunger", getHunger(stack) + hunger);
    }

    public static void subtractHunger(ItemStack stack, int hunger) {
        stack.getTag().putInt("StoredHunger", getHunger(stack) - hunger);
    }

    public static void addHungerTag(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }
    }

    public static int getHunger(ItemStack stack) {
        return stack.getTag().getInt("StoredHunger");
    }

    //saturation nbt
    public static void addSaturation(ItemStack stack, float saturation) {
        stack.getTag().putFloat("StoredSaturation", getSaturation(stack) + saturation);
    }

    public static void subtractSaturation(ItemStack stack, float saturation) {
        stack.getTag().putFloat("StoredSaturation", getSaturation(stack) - saturation);
    }

    public static void addSaturationTag(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }
    }

    public static float getSaturation(ItemStack stack) {
        return stack.getTag().getFloat("StoredSaturation");
    }

    //milk nbt
    public static void addMilk(ItemStack stack, float milk) {
        stack.getTag().putFloat("StoredMilk", getMilk(stack) + milk);
    }

    public static void subtractMilk(ItemStack stack, float milk) {
        stack.getTag().putFloat("StoredMilk", getMilk(stack) - milk);
    }

    public static void addMilkTag(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }
    }

    public static float getMilk(ItemStack stack) {
        return stack.getTag().getFloat("StoredMilk");
    }
}
