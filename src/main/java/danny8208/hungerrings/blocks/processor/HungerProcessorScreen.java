package danny8208.hungerrings.blocks.processor;

import com.mojang.blaze3d.platform.GlStateManager;
import danny8208.hungerrings.HungerRings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class HungerProcessorScreen extends ContainerScreen<HungerProcessorContainer> {
    private static final ResourceLocation GUI = new ResourceLocation(HungerRings.MODID, "textures/gui/hunger_processor.png");
    private final HungerProcessorTile tile;

    public HungerProcessorScreen(HungerProcessorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        tile = screenContainer.tileEntity;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        drawString(Minecraft.getInstance().fontRenderer, "Stored Milk: " + tile.getMilk(), 5, 40, 0xffffff);
        drawString(Minecraft.getInstance().fontRenderer, "Stored Hunger: ", 5, 50, 0x996633);
        drawString(Minecraft.getInstance().fontRenderer, "Stored Saturation: ", 5, 60, 0xff9966);
    }
}
