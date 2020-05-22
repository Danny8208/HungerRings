package danny8208.hungerrings.items;

import danny8208.hungerrings.HungerRings;
import danny8208.hungerrings.util.EnabledUtil;
import danny8208.hungerrings.util.HungerNBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RingHunger extends Item {
    public RingHunger() {
        super(new Properties().group(HungerRings.GROUP).maxStackSize(1));
        setRegistryName("hunger_ring");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        HungerNBT.addHungerTag(stack);
        HungerNBT.addSaturationTag(stack);
        tooltip.add(new TranslationTextComponent("item.hungerrings.activate1"));
        tooltip.add(new TranslationTextComponent("item.hungerrings.food_slot"));
        tooltip.add(new StringTextComponent("Stored Hunger: " + HungerNBT.getHunger(stack)));
        tooltip.add(new StringTextComponent("Stored Saturation: " + HungerNBT.getSaturation(stack)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        PlayerInventory playerInventory = playerIn.inventory;

        if (!worldIn.isRemote) {
            if (!playerIn.isShiftKeyDown()) {
                EnabledUtil.changeEnabled(stack);
                if (EnabledUtil.isEnabled(stack)) {
                    playerIn.sendStatusMessage(new TranslationTextComponent("item.hungerrings.enabled1"), true);
                } else {
                    playerIn.sendStatusMessage(new TranslationTextComponent("item.hungerrings.disabled1"), true);
                }
            }
            if (playerIn.isShiftKeyDown()) {
                if (playerInventory.getStackInSlot(9).getItem().isFood()) {
                    HungerNBT.addHunger(stack, playerInventory.getStackInSlot(9).getItem().getFood().getHealing());
                    HungerNBT.addSaturation(stack, playerInventory.getStackInSlot(9).getItem().getFood().getSaturation());

                    playerInventory.decrStackSize(9, 1);
                } else {
                    playerIn.sendStatusMessage(new TranslationTextComponent("item.hungerrings.no_food"), true);
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote && entityIn instanceof PlayerEntity && EnabledUtil.isEnabled(stack)) {
            PlayerEntity player = (PlayerEntity) entityIn;

            if (player.getFoodStats().getFoodLevel() < 20 && HungerNBT.getHunger(stack) > 0) {
                player.getFoodStats().addStats(1, 0);
                HungerNBT.subtractHunger(stack, 1);
            }

            if (player.getFoodStats().getSaturationLevel() < 5 && HungerNBT.getSaturation(stack) > 0) {
                player.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + 1);
                HungerNBT.subtractSaturation(stack, 0.1f);
            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return EnabledUtil.isEnabled(stack);
    }
}
