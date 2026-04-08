package com.pak.villagerwardrobe.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pak.villagerwardrobe.VillagerWardrobe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class WardrobeScreen extends AbstractContainerScreen<WardrobeMenu> {
  private static final ResourceLocation GUI_TEXTURE =
      ResourceLocation.fromNamespaceAndPath(VillagerWardrobe.MOD_ID, "textures/gui/wardrobe/wardrobe_gui.png");

  public WardrobeScreen(WardrobeMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);

    int x = (width - imageWidth) / 2;
    int y = (height - imageHeight) / 2;

    guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
  }

  @Override
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
  }
}