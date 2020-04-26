package danny8208.hungerrings.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class HungerSaturationHandler implements INBTSerializable<CompoundNBT> {
    private float milk;
    private int hunger;
    private float saturation;

    public HungerSaturationHandler() {
    }

    public float getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public void addMilk(int milkAdd) {
        milk = milk + milkAdd;
    }

    public void addHunger(int hungerAdd) {
        hunger = hunger + hungerAdd;
    }

    public void addSaturation(float saturationAdd) {
        saturation = saturation + saturationAdd;
    }

    public void subtractMilk(int milkSubtract) {
        milk = getMilk() - milkSubtract;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("StoredMilk", milk);
        nbt.putInt("StoredHunger", hunger);
        nbt.putFloat("StoredSaturation", saturation);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        milk = nbt.getFloat("StoredMilk");
        hunger = nbt.getInt("StoredHunger");
        saturation = nbt.getFloat("StoredSaturation");
    }
}
