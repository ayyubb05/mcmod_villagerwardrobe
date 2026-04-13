package com.pak.villagerwardrobe.screen.custom;

import com.pak.villagerwardrobe.util.WardrobeOutfitLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
public class WardrobeScreen extends AbstractContainerScreen<WardrobeMenu> {

  private static final ResourceLocation ENCHANTING_TABLE_GUI =
      ResourceLocation.withDefaultNamespace("textures/gui/container/enchanting_table.png");

  private static final int[] OPTION_Y_POSITIONS = {19, 39, 59};
  private static final int OPTION_X = 82;          // text starts after arrow width + small gap
  private static final int OPTION_WIDTH = 85;      // keep as is or adjust
  private static final int OPTION_HEIGHT = 16;

  // Arrow buttons — positioned just to the right of the option rows
  private static final int ARROW_X = 60;           // left edge, where arrows sit
  private static final int ARROW_UP_Y = 14;        // aligns with first row
  private static final int ARROW_DOWN_Y = 54;      // aligns with third row
  private static final int ARROW_SIZE = 16;

  private int hoveredOption = -1;
  private boolean hoveringUp = false;
  private boolean hoveringDown = false;
  private int scrollOffset = 0;

  public WardrobeScreen(WardrobeMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
    this.imageWidth = 176;
    this.imageHeight = 166;
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    guiGraphics.blit(ENCHANTING_TABLE_GUI, leftPos, topPos, 0, 0, imageWidth, imageHeight);

    boolean hasItem = !menu.blockEntity.inventory.getStackInSlot(0).isEmpty();
    List<String> outfits = WardrobeOutfitLoader.INSTANCE.getDisplayNames();
    boolean canScroll = outfits.size() > 3;

    // Draw option highlight
    if (hoveredOption >= 0 && hasItem) {
      int highlightY = topPos + OPTION_Y_POSITIONS[hoveredOption] - 2;
      guiGraphics.fill(
          leftPos + OPTION_X, highlightY,
          leftPos + OPTION_X + OPTION_WIDTH, highlightY + OPTION_HEIGHT,
          0x30FFFFFF
      );
    }

    // Draw scroll arrows as simple filled boxes with text
    if (hasItem && canScroll) {
      // Up arrow
      int upColor = (hoveringUp && scrollOffset > 0) ? 0xFFFFFFFF : 0x80FFFFFF;
      guiGraphics.fill(
          leftPos + ARROW_X, topPos + ARROW_UP_Y,
          leftPos + ARROW_X + ARROW_SIZE, topPos + ARROW_UP_Y + ARROW_SIZE,
          0x50000000  // dark background
      );
      guiGraphics.drawString(font, "▲", leftPos + ARROW_X + 6, topPos + ARROW_UP_Y + 6, upColor, false);

      // Down arrow
      int downColor = (hoveringDown && scrollOffset < outfits.size() - 3) ? 0xFFFFFFFF : 0x80FFFFFF;
      guiGraphics.fill(
          leftPos + ARROW_X, topPos + ARROW_DOWN_Y,
          leftPos + ARROW_X + ARROW_SIZE, topPos + ARROW_DOWN_Y + ARROW_SIZE,
          0x50000000
      );
      guiGraphics.drawString(font, "▼", leftPos + ARROW_X + 6, topPos + ARROW_DOWN_Y + 6, downColor, false);
    }
  }
  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    if (menu.blockEntity.inventory.getStackInSlot(0).isEmpty()) return;

    List<String> outfits = WardrobeOutfitLoader.INSTANCE.getDisplayNames();

    // Define the clipping rectangle in screen coordinates
    int clipX = leftPos + OPTION_X;
    int clipRight = leftPos + OPTION_X + OPTION_WIDTH;

    guiGraphics.enableScissor(clipX, 0, clipRight, this.height);

    for (int i = 0; i < 3; i++) {
      int dataIndex = scrollOffset + i;
      if (dataIndex >= outfits.size()) break;

      int color = (i == hoveredOption) ? 0xFFFFAA : 0x80FF20;
      guiGraphics.drawString(font, outfits.get(dataIndex), OPTION_X, OPTION_Y_POSITIONS[i], color, false);
    }

    guiGraphics.disableScissor();
  }
  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
    boolean hasItem = !menu.blockEntity.inventory.getStackInSlot(0).isEmpty();
    List<String> outfits = WardrobeOutfitLoader.INSTANCE.getDisplayNames();

    // Update hovered option
    hoveredOption = -1;
    if (hasItem) {
      for (int i = 0; i < 3; i++) {
        int dataIndex = scrollOffset + i;
        if (dataIndex >= outfits.size()) break;
        int optionY = topPos + OPTION_Y_POSITIONS[i] - 2;
        if (mouseX >= leftPos + OPTION_X
            && mouseX <= leftPos + OPTION_X + OPTION_WIDTH
            && mouseY >= optionY
            && mouseY <= optionY + OPTION_HEIGHT) {
          hoveredOption = i;
          break;
        }
      }
    }

    // Update hovered arrows
    hoveringUp = hasItem && outfits.size() > 3
        && mouseX >= leftPos + ARROW_X
        && mouseX <= leftPos + ARROW_X + ARROW_SIZE
        && mouseY >= topPos + ARROW_UP_Y
        && mouseY <= topPos + ARROW_UP_Y + ARROW_SIZE;

    hoveringDown = hasItem && outfits.size() > 3
        && mouseX >= leftPos + ARROW_X
        && mouseX <= leftPos + ARROW_X + ARROW_SIZE
        && mouseY >= topPos + ARROW_DOWN_Y
        && mouseY <= topPos + ARROW_DOWN_Y + ARROW_SIZE;

    super.render(guiGraphics, mouseX, mouseY, partialTick);
    renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (button == 0) {
      List<String> outfits = WardrobeOutfitLoader.INSTANCE.getDisplayNames();

      // Up arrow clicked
      if (hoveringUp && scrollOffset > 0) {
        scrollOffset--;
        return true;
      }

      // Down arrow clicked
      if (hoveringDown && scrollOffset < outfits.size() - 3) {
        scrollOffset++;
        return true;
      }

      // Option row clicked
      if (hoveredOption >= 0) {
        minecraft.gameMode.handleInventoryButtonClick(menu.containerId, scrollOffset + hoveredOption);
        return true;
      }
    }
    return super.mouseClicked(mouseX, mouseY, button);
  }
}