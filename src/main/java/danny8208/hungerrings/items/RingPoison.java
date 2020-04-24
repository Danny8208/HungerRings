package danny8208.hungerrings.items;

import danny8208.hungerrings.ModKeybinding;
import danny8208.hungerrings.util.EnabledUtil;
import danny8208.hungerrings.util.HungerNBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static danny8208.hungerrings.HungerRings.GROUP;

public class RingPoison extends Item {
    public RingPoison() {
        super(new Properties().group(GROUP).maxStackSize(1));
        setRegistryName("poison_ring");
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.hungerrings.activate2"));
        tooltip.add(new TranslationTextComponent("item.hungerrings.milk_slot"));
        HungerNBT.addHungerTag(stack);
        tooltip.add(new StringTextComponent("Stored Milk: " + HungerNBT.getMilk(stack)));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        PlayerInventory playerInventory = playerIn.inventory;

        if (!worldIn.isRemote) {
            if (!playerIn.isCrouching()) {
                EnabledUtil.changeEnabled(stack);
                if (EnabledUtil.isEnabled(stack)) {
                    playerIn.sendMessage(new TranslationTextComponent("item.hungerrings.enabled2"));
                } else {
                    playerIn.sendMessage(new TranslationTextComponent("item.hungerrings.disabled2"));
                }
            }
            if (playerIn.isCrouching()) {
                if (playerInventory.getStackInSlot(9).getItem() == Items.MILK_BUCKET) {
                    playerInventory.decrStackSize(9, 1);
                    ItemEntity bucket = new ItemEntity(worldIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), new ItemStack(Items.BUCKET));
                    bucket.setNoPickupDelay();
                    worldIn.addEntity(bucket);
                    HungerNBT.addMilk(stack, 0.1f);
                } else {
                    playerIn.sendMessage(new TranslationTextComponent("item.hungerrings.no_milk"));
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote && entityIn instanceof PlayerEntity && EnabledUtil.isEnabled(stack) && ModKeybinding.TOGGLE_POISON.isPressed() && HungerNBT.getMilk(stack) > 0) {
            PlayerEntity player = (PlayerEntity) entityIn;
            player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
            HungerNBT.subtractMilk(stack, 0.1f);
            super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
        }
    }
}
